package com.schools.blackjack.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shoe {
    private int numDecks;
    private int index;
    private int yellow;
    private List<Card> shoeCards;
    List<Integer> initBankrolls;
    List<Integer> endBankrolls;

    public Shoe() {

    }

    private int getNumDecks() {
        return numDecks;
    }

    private void setNumDecks(int numDecks) {
        this.numDecks = numDecks;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public List<Card> getShoeCards() {
        return shoeCards;
    }

    private void setShoeCards(List<Card> shoeCards) {
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

    public Shoe loadShoe() {
        this.setNumDecks(8);
        List<Card> cards = new ArrayList<>();
        this.setShoeCards(cards);
        List<String> suits = new ArrayList<>();
        suits.add("Hearts");
        suits.add("Clubs");
        suits.add("Diamonds");
        suits.add("Spades");
        for (int i = 0; i < this.getNumDecks(); i++) {
            for(int j = 0; j < suits.size(); j++) {
                for (int k = 2; k < 11; k++) {
                    Card temp = new Card();
                    temp.setValue(k);
                    temp.setName(Integer.toString(k));
                    temp.setSuit(suits.get(j));
                    if (j % 2 == 0) {
                        temp.setColor("red");
                    } else {
                        temp.setColor("black");
                    }
                    temp.setAbName(temp.getName() + temp.getSuit().substring(0,1));
                    this.getShoeCards().add(temp);
                }
                Card jack = new Card();
                jack.setValue(10);
                jack.setName("J");
                jack.setSuit(suits.get(j));
                jack.setAbName(jack.getName() + jack.getSuit().substring(0,1));
                Card queen = new Card();
                queen.setValue(10);
                queen.setName("Q");
                queen.setSuit(suits.get(j));
                queen.setAbName(queen.getName() + queen.getSuit().substring(0,1));
                Card king = new Card();
                king.setValue(10);
                king.setName("K");
                king.setSuit(suits.get(j));
                king.setAbName(king.getName() + king.getSuit().substring(0,1));
                Card ace = new Card();
                ace.setValue(1);
                ace.setName("A");
                ace.setSuit(suits.get(j));
                ace.setAbName(ace.getName() + ace.getSuit().substring(0,1));
                this.getShoeCards().add(jack);
                this.getShoeCards().add(queen);
                this.getShoeCards().add(king);
                this.getShoeCards().add(ace);
            }
        }
        return this;
    }

    public Shoe shuffleShoe() {
        Random r = new Random();
        int pent = r.nextInt(52) + 312;
        this.setYellow(pent);
        for (int i = this.getShoeCards().size() - 1; i > 1; i--) {
            int num = r.nextInt(i);
            Card temp = this.getShoeCards().get(num);
            Card last = this.getShoeCards().get(i);
            this.getShoeCards().remove(num);
            this.getShoeCards().add(num, last);
            this.getShoeCards().remove(i);
            this.getShoeCards().add(i, temp);
        }
        this.setIndex(1);
        return this;
    }

}
