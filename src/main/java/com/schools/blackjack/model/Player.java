package com.schools.blackjack.model;

import java.util.List;

public class Player {
    private String name;
    private List<Hand> hands;
    private List<Integer> bets;
    private List<Integer> bankroll;
    private boolean canHit;
    private boolean canStand;
    private boolean canDouble;
    private boolean canSplit;

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Hand> getHands() {
        return hands;
    }

    public void setHands(List<Hand> hands) {
        this.hands = hands;
    }

    public List<Integer> getBets() {
        return bets;
    }

    public void setBets(List<Integer> bets) {
        this.bets = bets;
    }

    public List<Integer> getBankroll() {
        return bankroll;
    }

    public void setBankroll(List<Integer> bankroll) {
        this.bankroll = bankroll;
    }

    public boolean isCanHit() {
        return canHit;
    }

    public void setCanHit(boolean canHit) {
        this.canHit = canHit;
    }

    public boolean isCanStand() {
        return canStand;
    }

    public void setCanStand(boolean canStand) {
        this.canStand = canStand;
    }

    public boolean isCanDouble() {
        return canDouble;
    }

    public void setCanDouble(boolean canDouble) {
        this.canDouble = canDouble;
    }

    public boolean isCanSplit() {
        return canSplit;
    }

    public void setCanSplit(boolean canSplit) {
        this.canSplit = canSplit;
    }

    public void setButtons() {
        this.setCanHit(true);
        this.setCanStand(true);
        if (this.getHands().get(0).getCards().size() == 2) {
            this.setCanDouble(true);
        } else {
            this.setCanDouble(false);
        }
        if (this.getHands().get(0).getCards().get(0).getValue() == this.getHands().get(0).getCards().get(1).getValue()) {
            this.setCanSplit(true);
        } else {
            this.setCanSplit(false);
        }
    }

    public void clearButtons() {
        this.setCanHit(false);
        this.setCanStand(false);
        this.setCanDouble(false);
        this.setCanSplit(false);
    }
}

