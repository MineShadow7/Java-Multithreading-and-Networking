package ru.kriseev.netdevlab.server.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Players")
public class PlayerPersistentData {
    @Id
    @Column(name = "playerId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "playerName")
    private String nickname;

    @Column(name = "playerRank")
    private int rank;

    public PlayerPersistentData(String nickname, int rank) {
        this.nickname = nickname;
        this.rank = rank;
    }

    public PlayerPersistentData() {

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
