package ru.kriseev.netdevlab.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.kriseev.netdevlab.common.model.GameState;
import ru.kriseev.netdevlab.common.model.LeaderboardEntry;
import ru.kriseev.netdevlab.common.model.Room;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameClient {
    Socket socket;
    InputStream socketInputStream;
    OutputStream socketOutputStream;
    BufferedReader socketReader;
    PrintWriter socketWriter;

    public Boolean getConnected() {
        return connected;
    }

    public String getNickname() {
        return nickname;
    }

    public Boolean getGameStarted() {
        return gameStarted;
    }

    private Boolean connected = false;
    private String nickname;

    private GameState state;
    private Boolean gameStarted = false;

    private Room room;
    public GameClient() {

    }

    public void connect() {
        try {
            socket = new Socket("127.0.0.1", 4000);
            socketInputStream = socket.getInputStream();
            socketOutputStream = socket.getOutputStream();
            socketReader = new BufferedReader(new InputStreamReader(socketInputStream));
            socketWriter = new PrintWriter(socketOutputStream, true);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean join(String nickname) {
        this.nickname = nickname;
        System.out.println("joining");
        socketWriter.println("join");
        socketWriter.println(nickname);
        System.out.println("join sent");
        String connectResult;
        try {
            connectResult = socketReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Received status " + connectResult);
        switch (connectResult) {
            case "ok":
                connected = true;
                return true;
            case "full":
            case "playerexists":
            default:
                return false;
        }
    }
    public void disconnect() {
        if(connected) {
            try {
                socketWriter.println("disconnect");
                socketReader.readLine();
                socketReader = null;
                socketWriter = null;
                socket.close();
                connected = false;
                gameStarted = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateState() {
        Gson gson = new Gson();
        socketWriter.println("getGameState");
        try {
            String response = socketReader.readLine();
            if("nogame".equals(response)) {
                state = null;
                return;
            }
            state = gson.fromJson(response, GameState.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearState() {
        state = null;
    }
    public void updateRoom() {
        Gson gson = new Gson();
        socketWriter.println("getRoomState");
        try {
            room = gson.fromJson(socketReader.readLine(), Room.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public GameState getState() {
        return state;
    }
    public Room getRoom() {
        return room;
    }
    public void sendReady() {
        socketWriter.println("ready");
        try {
            socketReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendNotReady() {
        socketWriter.println("unready");
        try {
            socketReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateGameStartedStatus() {
        socketWriter.println("gameStarted");
        String message;
        try {
            message = socketReader.readLine();
            gameStarted = message.equals("true");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void shoot() {
        if(!gameStarted) {
            return;
        }
        socketWriter.println("shoot");
        String message;
        try {
            socketReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<LeaderboardEntry> getLeaderboard() {
        socketWriter.println("leaderboard");
        try {
            String message = socketReader.readLine();
            Type leaderboardType = new TypeToken<ArrayList<LeaderboardEntry>>() {}.getType();
            return new Gson().fromJson(message, leaderboardType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
