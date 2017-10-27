package com.schools.blackjack.model;

import java.util.List;

public class Shoe {
    private int numDecks;
    int index;
    private int yellow;
    List<Card> shoeCards;

    public Shoe() {

    }

    public int getNumDecks() {
        return numDecks;
    }

    public void setNumDecks(int numDecks) {
        this.numDecks = numDecks;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public List<Card> getShoeCards() {
        return shoeCards;
    }

    public void setShoeCards(List<Card> shoeCards) {
        this.shoeCards = shoeCards;
    }

    @Override
    public String toString() {
        return "Shoe{" +
                "numDecks=" + numDecks +
                ", index=" + index +
                ", yellow=" + yellow +
                ", shoeCards=" + shoeCards +
                '}';
    }
}
