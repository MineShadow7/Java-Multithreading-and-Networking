package org.jvmlthread.javamultithreading.server;

import jakarta.persistence.*;

@Entity
@Table(name = "Players")
public class PlayerPersistentDataServer {
    @Id
    @Column(name = "playerId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "playerName")
    private String nickname;

    @Column(name = "playerRank")
    private int rank;

    public PlayerPersistentDataServer(String nickname, int rank) {
        this.nickname = nickname;
        this.rank = rank;
    }

    public PlayerPersistentDataServer() {

    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
