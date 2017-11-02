package com.schools.blackjack.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CardTable {
    private int id;
    private Shoe shoe;
    private List<Player> players;
    private Dealer dealer;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void dealerHasBlackJack() {                   //this method may need to be refactored to endHand method
        for (Player player : this.players) {
            if (player.getHands().get(0).blackJack()) {
                player.getHands().get(0).setWin(0);
            } else {
                player.getHands().get(0).setWin(-1);
            }
        }
    }

    public boolean endHand() {
        // method should adjust bankrolls based on bets and win value of each hand
        for (Player player : this.players) {
            for(Hand hand : player.getHands()) {
                List<Integer> newBank = new ArrayList<>();
                for (int i = 0; i < player.getBets().size(); i++) {
                    int currentBank = player.getBankroll().get(i);
                    int currentBet = player.getBankroll().get(i);
                    currentBank += currentBet * hand.getWin();
                    newBank.add(currentBank);
                }
                player.setBankroll(newBank);
            }
        }
        return true;
    }
}
