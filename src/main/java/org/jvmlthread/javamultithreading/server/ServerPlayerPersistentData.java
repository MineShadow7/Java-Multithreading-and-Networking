package org.jvmlthread.javamultithreading.server;

import jakarta.persistence.*;

@Entity
@Table(name = "Players")
public class ServerPlayerPersistentData {
    @Id
    @Column(name = "playerId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "playerName")
    private String nickname;

    @Column(name = "playerRank")
    private int rank;

    public ServerPlayerPersistentData(String nickname, int rank) {
        this.nickname = nickname;
        this.rank = rank;
    }

    public ServerPlayerPersistentData() {

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
