package com.schools.blackjack.model;

import java.util.List;

public class Hand {
    private int total;
    private List<Card> cards;
    private boolean ace;

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
