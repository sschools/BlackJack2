package com.schools.blackjack.model;

import java.util.List;

public class Hand {
    private int total;
    private List<Card> cards;
    private boolean ace;
    private boolean bust;
    private double win;
    private String message;
    private boolean doubleDown;
    private boolean active;

    public Hand() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = 0;
        for(Card card: this.getCards()) {
            this.total += card.getValue();
        }
        if (this.ace && this.total < 12) {
            this.total += 10;
        }
    }

    public List<Card> getCards() {
            return cards;
        }

    public void setCards(List<Card> cards) {
            this.cards = cards;
        }

    public boolean isAce() {
        return ace;
    }

    public double getWin() {
        return win;
    }

    public void setWin(double win) {
        this.win = win;
    }

    public void setAce(boolean ace) {
        this.ace = ace;
    }

    public boolean isBust() {
        return bust;
    }

    public void setBust(boolean bust) {
        this.bust = bust;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDoubleDown() {
        return doubleDown;
    }

    public void setDoubleDown(boolean doubleDown) {
        this.doubleDown = doubleDown;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean blackJack() {
        boolean temp;
        int first = 0;
        int second = 0;
        first = this.cards.get(0).getValue();
        second = this.cards.get(1).getValue();
        if ((first == 1 && second == 10) || (second == 1 && first == 10)) {
            temp = true;
        } else {
            temp = false;
        }
        return temp;
    }
}
