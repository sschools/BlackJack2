package com.schools.blackjack.model;

import javax.persistence.*;

@Entity
@Table(name = "shoe")
public class ShoeStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "numplayers")
    private int numPlayers;
    @Column(name = "numhands")
    private int numHands;
    @Column(name = "winhands")
    private int winHands;
    @Column(name = "betstrat")
    private int betStrat;
    @Column(name = "winpercent")
    private double winPercent;
    @Column(name = "avbrdelta")
    private double avBrDelta; //bankroll delta
    @Column(name = "maxbrdelta")
    private double maxBrDelta;
    @Column(name = "minbrdelta")
    private double minBrDelta;

    ShoeStat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getNumHands() {
        return numHands;
    }

    public void setNumHands(int numHands) {
        this.numHands = numHands;
    }

    public int getWinHands() {
        return winHands;
    }

    public void setWinHands(int winHands) {
        this.winHands = winHands;
    }

    public int getBetStrat() {
        return betStrat;
    }

    public void setBetStrat(int betStrat) {
        this.betStrat = betStrat;
    }

    public double getWinPercent() {
        return winPercent;
    }

    public void setWinPercent(double winPercent) {
        this.winPercent = winPercent;
    }

    public double getAvBrDelta() {
        return avBrDelta;
    }

    public void setAvBrDelta(double avBrDelta) {
        this.avBrDelta = avBrDelta;
    }

    public double getMaxBrDelta() {
        return maxBrDelta;
    }

    public void setMaxBrDelta(double maxBrDelta) {
        this.maxBrDelta = maxBrDelta;
    }

    public double getMinBrDelta() {
        return minBrDelta;
    }

    public void setMinBrDelta(double minBrDelta) {
        this.minBrDelta = minBrDelta;
    }
}
