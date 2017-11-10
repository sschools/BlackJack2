package com.schools.blackjack.model;

import javax.persistence.*;

@Entity
@Table(name = "shoe")
public class ShoeStats {
    private int id;
    private int numPlayers;
    private int numHands;
    private int winHands;
    private int betStrat;
    private double winPercent;
    private double avBrDelta; //bankroll delta
    private double maxBrDelta;
    private double minBrDelta;

    public ShoeStats() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "numplayers")
    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Column(name = "numhands")
    public int getNumHands() {
        return numHands;
    }

    public void setNumHands(int numHands) {
        this.numHands = numHands;
    }

    @Column(name = "winhands")
    public int getWinHands() {
        return winHands;
    }

    public void setWinHands(int winHands) {
        this.winHands = winHands;
    }

    @Column(name = "betstrat")
    public int getBetStrat() {
        return betStrat;
    }

    public void setBetStrat(int betStrat) {
        this.betStrat = betStrat;
    }

    @Column(name = "winpercent")
    public double getWinPercent() {
        return winPercent;
    }

    public void setWinPercent(double winPercent) {
        this.winPercent = winPercent;
    }

    @Column(name = "avbrdelta")
    public double getAvBrDelta() {
        return avBrDelta;
    }

    public void setAvBrDelta(double avBrDelta) {
        this.avBrDelta = avBrDelta;
    }

    @Column(name = "maxbrdelta")
    public double getMaxBrDelta() {
        return maxBrDelta;
    }

    public void setMaxBrDelta(double maxBrDelta) {
        this.maxBrDelta = maxBrDelta;
    }

    @Column(name = "minbrdelta")
    public double getMinBrDelta() {
        return minBrDelta;
    }

    public void setMinBrDelta(double minBrDelta) {
        this.minBrDelta = minBrDelta;
    }
}
