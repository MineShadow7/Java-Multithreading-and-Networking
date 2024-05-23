package org.jvmlthread.javamultithreading.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jvmlthread.javamultithreading.game.MainLobbyClass;
import org.jvmlthread.javamultithreading.game.GamePlayerClass;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

class ServerSocketIOHandler extends Thread {
    private final Socket clientSocket;
    private final MainLobbyClass lobby;

    public ServerSocketIOHandler(Socket clientSocket, MainLobbyClass lobby) {
        this.clientSocket = clientSocket;
        this.lobby = lobby;
    }

    @Override
    public void run() {
        BufferedReader br;
        PrintWriter writer;
        GamePlayerClass associatedPlayer = null;
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        ServerPlayerRepository playerRepository = new ServerPlayerRepository();
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
                        synchronized (GameServerClass.gameLock) {
                            writer.println(!(GameServerClass.currentGame == null || GameServerClass.currentGame.getState().getIsFinished()));
                        }
                        break;
                    case "join":
                        synchronized (lobby) {
                            String nickname = br.readLine();
                            synchronized (GameServerClass.gameLock) {
                                if (GameServerClass.currentGame != null && !GameServerClass.currentGame.getState().getIsFinished()) {
                                    writer.println("gamestarted");
                                    break;
                                }
                            }
                            if (associatedPlayer != null) {
                                writer.println("alreadyloggedin");
                                break;
                            }
                            if (lobby.getPlayers().size() >= 4) {
                                writer.println("full");
                                break;
                            }
                            for (GamePlayerClass p : lobby.getPlayers()) {
                                if (p.getNickname().equals(nickname)) {
                                    writer.println("playerexists");
                                    break MSGSW;
                                }
                            }
                            System.out.println(nickname + " logged in");
                            associatedPlayer = playerRepository.getPlayer(nickname);
                            double y = 105.0;
                            lobby.addPlayer(associatedPlayer);
                            for (GamePlayerClass p : lobby.getPlayers()) {
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
                        synchronized (GameServerClass.gameLock) {
                            if (GameServerClass.currentGame == null) {
                                writer.println("nogame");
                                break;
                            }
                            writer.println(gson.toJson(GameServerClass.currentGame.getState()));
                        }
                        break;
                    case "getRoomState":
                        synchronized (lobby) {
                            writer.println(gson.toJson(lobby));
                        }
                        break;
                    case "ready":
                        if (associatedPlayer == null) {
                            writer.println("noplayer");
                            break;
                        }
                        synchronized (lobby) {
                            associatedPlayer.setReady(true);
                            System.out.println(associatedPlayer.getNickname() + "is ready");
                            lobby.notify();
                        }
                        writer.println("ok");
                        break;
                    case "unready":
                        if (associatedPlayer == null) {
                            writer.println("noplayer");
                            break;
                        }
                        synchronized (lobby) {
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
                        synchronized (GameServerClass.gameLock) {
                            if (GameServerClass.currentGame == null || GameServerClass.currentGame.getState().getIsFinished()) {
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

    private void disconnectPlayer(GamePlayerClass associatedPlayer) {
        if (associatedPlayer != null) {
            synchronized (GameServerClass.gameLock) {
                GameServerClass.currentGame = null;
            }
            synchronized (lobby) {
                lobby.removePlayer(associatedPlayer);
                lobby.notify();
            }
            System.out.println(associatedPlayer.getNickname() + " disconnected");

        }
    }
}

public class ServerClass {
    private final AtomicBoolean running = new AtomicBoolean(false);

    public void startServer(int port) {
        if (running.getAndSet(true)) {
            System.out.println("Can't start second server in same instance");
            return;
        }
        HibernateSessionFactory.getSessionFactory(); // Init for future use
        MainLobbyClass lobby = new MainLobbyClass();
        Thread gameloopThread = new Thread(() -> {
            System.out.println("Started gameloop thread");
            try {
                while (true) {
                    synchronized (lobby) {
                        while (!lobby.isReady()) {
                            lobby.wait();
                        }
                    }
                    System.out.println("Starting new game");
                    synchronized (lobby) {
                        synchronized (GameServerClass.gameLock) {
                            for (GamePlayerClass p : lobby.getPlayers()) {
                                p.reset();
                            }
                        }
                        GameServerClass.currentGame = new GameServerClass(640, 480, lobby.getPlayers().toArray(new GamePlayerClass[0]));
                    }
                    while (true) {
                        synchronized (GameServerClass.gameLock) {
                            if (GameServerClass.currentGame == null) {
                                System.out.println("Game was destroyed");
                                break;
                            }

                            GameServerClass.currentGame.step();
                            if (GameServerClass.currentGame.getState().getIsFinished()) {
                                break;
                            }
                        }
                        synchronized (lobby) {
                            while (!lobby.isReady()) {
                                synchronized (GameServerClass.gameLock) {
                                    GameServerClass.currentGame.getState().setPaused(true);
                                }
                                lobby.wait();
                                synchronized (GameServerClass.gameLock) {
                                    if (GameServerClass.currentGame == null) {
                                        break;
                                    }
                                }
                            }
                            synchronized (GameServerClass.gameLock) {
                                if (GameServerClass.currentGame != null) {
                                    GameServerClass.currentGame.getState().setPaused(false);
                                }
                            }
                        }
                        Thread.sleep(10);
                    }
                    synchronized (lobby) {
                        for (GamePlayerClass p : lobby.getPlayers()) {
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
                new ServerSocketIOHandler(serverSocket.accept(), lobby).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gameloopThread.interrupt();
    }
}

