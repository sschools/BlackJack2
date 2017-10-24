package com.schools.blackjack.model;

import java.util.List;

public class Hand {
    private int total;
    private List<Card> cards;
    private boolean ace;
    private boolean blackJack;

    public Hand() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
            this.total = total;
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

    public void setAce(boolean ace) {
        this.ace = ace;
    }

    public boolean isBlackJack() {
        return blackJack;
    }

    public void setBlackJack(boolean blackJack) {
        this.blackJack = blackJack;
    }
}
