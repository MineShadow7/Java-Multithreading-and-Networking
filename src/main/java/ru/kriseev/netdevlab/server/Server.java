package ru.kriseev.netdevlab.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.kriseev.netdevlab.common.model.Player;
import ru.kriseev.netdevlab.common.model.Room;
import ru.kriseev.netdevlab.server.model.PlayerRepository;
import ru.kriseev.netdevlab.server.util.HibernateSessionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

class ServerSocketIOHandler extends Thread {
    private final Socket clientSocket;
    private final Room room;

    public ServerSocketIOHandler(Socket clientSocket, Room room) {
        this.clientSocket = clientSocket;
        this.room = room;
    }

    @Override
    public void run() {
        BufferedReader br;
        PrintWriter writer;
        Player associatedPlayer = null;
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        PlayerRepository playerRepository = new PlayerRepository();
        System.out.println("Accepted new connection");
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = "";
            IOLoop:
            while (true) {
                message = br.readLine();
                MSGSW:
                switch (message) {
                    case "leaderboard":
                        writer.println(gson.toJson(playerRepository.getTopPlayers()));
                        break;
                    case "gameStarted":
                        synchronized (Game.gameLock) {
                            writer.println(!(Game.currentGame == null || Game.currentGame.getState().getIsFinished()));
                        }
                        break;
                    case "join":
                        synchronized (room) {
                            String nickname = br.readLine();
                            synchronized (Game.gameLock) {
                                if (Game.currentGame != null && !Game.currentGame.getState().getIsFinished()) {
                                    writer.println("gamestarted");
                                    break;
                                }
                            }
                            if (associatedPlayer != null) {
                                writer.println("alreadyloggedin");
                                break;
                            }
                            if (room.getPlayers().size() >= 4) {
                                writer.println("full");
                                break;
                            }
                            for (Player p : room.getPlayers()) {
                                if (p.getNickname().equals(nickname)) {
                                    writer.println("playerexists");
                                    break MSGSW;
                                }
                            }
                            System.out.println(nickname + " logged in");
                            associatedPlayer = playerRepository.getPlayer(nickname);
                            double y = 105.0;
                            room.addPlayer(associatedPlayer);
                            for (Player p : room.getPlayers()) {
                                p.setY(y);
                                y += 90.0;
                            }
                        }
                        writer.println("ok");
                        break;
                    case "disconnect":
                        disconnectPlayer(associatedPlayer);
                        break IOLoop;
                    case "getGameState":
                        synchronized (Game.gameLock) {
                            if (Game.currentGame == null) {
                                writer.println("nogame");
                                break;
                            }
                            writer.println(gson.toJson(Game.currentGame.getState()));
                        }
                        break;
                    case "getRoomState":
                        synchronized (room) {
                            writer.println(gson.toJson(room));
                        }
                        break;
                    case "ready":
                        if (associatedPlayer == null) {
                            writer.println("noplayer");
                            break;
                        }
                        synchronized (room) {
                            associatedPlayer.setReady(true);
                            System.out.println(associatedPlayer.getNickname() + "is ready");
                            room.notify();
                        }
                        writer.println("ok");
                        break;
                    case "unready":
                        if (associatedPlayer == null) {
                            writer.println("noplayer");
                            break;
                        }
                        synchronized (room) {
                            associatedPlayer.setReady(false);
                            writer.println("ok");
                            System.out.println(associatedPlayer.getNickname() + " is not ready");
                        }
                        break;
                    case "shoot":
                        if (associatedPlayer == null) {
                            writer.println("noplayer");
                            break;
                        }
                        synchronized (Game.gameLock) {
                            if (Game.currentGame == null || Game.currentGame.getState().getIsFinished()) {
                                writer.println("nogame");
                                break;
                            }
                            associatedPlayer.shoot();
                            writer.println("ok");
                        }
                        break;
                }
            }
            clientSocket.close();
        } catch (IOException | NullPointerException e) {
            disconnectPlayer(associatedPlayer);
        }
    }

    private void disconnectPlayer(Player associatedPlayer) {
        if (associatedPlayer != null) {
            synchronized (Game.gameLock) {
                Game.currentGame = null;
            }
            synchronized (room) {
                room.removePlayer(associatedPlayer);
                room.notify();
            }
            System.out.println(associatedPlayer.getNickname() + " disconnected");

        }
    }
}

public class Server {
    private final AtomicBoolean running = new AtomicBoolean(false);

    public void startServer(int port) {
        if (running.getAndSet(true)) {
            System.out.println("Can't start second server in same instance");
            return;
        }
        HibernateSessionFactory.getSessionFactory(); // Init for future use
        Room room = new Room();
        Thread gameloopThread = new Thread(() -> {
            System.out.println("Started gameloop thread");
            try {
                while (true) {
                    synchronized (room) {
                        while (!room.isReady()) {
                            room.wait();
                        }
                    }
                    System.out.println("Starting new game");
                    synchronized (room) {
                        synchronized (Game.gameLock) {
                            for (Player p : room.getPlayers()) {
                                p.reset();
                            }
                        }
                        Game.currentGame = new Game(640, 480, room.getPlayers().toArray(new Player[0]));
                    }
                    while (true) {
                        synchronized (Game.gameLock) {
                            if (Game.currentGame == null) {
                                System.out.println("Game was destroyed");
                                break;
                            }

                            Game.currentGame.step();
                            if (Game.currentGame.getState().getIsFinished()) {
                                break;
                            }
                        }
                        synchronized (room) {
                            while (!room.isReady()) {
                                synchronized (Game.gameLock) {
                                    Game.currentGame.getState().setPaused(true);
                                }
                                room.wait();
                                synchronized (Game.gameLock) {
                                    if (Game.currentGame == null) {
                                        break;
                                    }
                                }
                            }
                            synchronized (Game.gameLock) {
                                if (Game.currentGame != null) {
                                    Game.currentGame.getState().setPaused(false);
                                }
                            }
                        }
                        Thread.sleep(10);
                    }
                    synchronized (room) {
                        for (Player p : room.getPlayers()) {
                            p.setReady(false);
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        gameloopThread.start();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Started server on port " + port);
            while (running.get()) {
                new ServerSocketIOHandler(serverSocket.accept(), room).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gameloopThread.interrupt();
    }
}
