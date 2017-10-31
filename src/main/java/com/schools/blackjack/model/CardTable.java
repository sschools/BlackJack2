package com.schools.blackjack.model;

import javax.persistence.*;
import java.util.List;

public class CardTable {
    private int id;
    private Shoe shoe;
    private List<Player> players;
    private Dealer dealer;
    private boolean dealerBlackJack;
    private String message;

    public CardTable() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public boolean isDealerBlackJack() {
        return dealerBlackJack;
    }

    public void setDealerBlackJack(Hand dealHand) {
        int valOne = dealHand.getCards().get(0).getValue();
        int valTwo = dealHand.getCards().get(1).getValue();
        if ((valOne == 1 && valTwo == 10) || (valOne == 10 && valTwo == 1)) {
            this.dealerBlackJack = true;
        } else {
            this.dealerBlackJack = false;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
