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
    private int currentPlayer;

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

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void dealerHasBlackJack() {
        for (Player player : this.players) {
            if (player.getHands().get(0).blackJack()) {
                player.getHands().get(0).setWin(0);
            } else {
                player.getHands().get(0).setWin(-1);
            }
        }
        this.endHand();
    }

    public void endHand() {
        // method should adjust bankrolls based on bets and win value of each hand
        for (Player player : this.players) {
            for (Hand hand : player.getHands()) {
                List<Integer> newBank = new ArrayList<>();
                for (int i = 0; i < player.getBets().size(); i++) {
                    int currentBank = player.getBankroll().get(i);
                    int currentBet = player.getBets().get(i);
                    currentBank += currentBet * hand.getWin();
                    newBank.add(currentBank);
                }
                player.setBankroll(newBank);
            }
        }
    }

    public void playDealer() {
        String name = this.getDealer().getHand().getCards().get(1).getName();
        String suit = this.getDealer().getHand().getCards().get(1).getSuit();
        this.getDealer().getHand().getCards().get(1).setAbName(name + suit.substring(0,1));
        Hand hand = this.getDealer().getHand();
        hand.setTotal();
        while (hand.getTotal() < 17) {
            Hand hitHand = this.hit(hand);
            hand = hitHand;
            hand.setTotal();
        }
        this.getDealer().setHand(hand);
    }

    public Hand hit(Hand hand) {
        Card next = this.getShoe().getShoeCards().get(this.getShoe().getIndex());
        // this gets first hand, will need to adjust for multiple hands after a split

        Hand current = hand;
        current.getCards().add(next);
        current.setTotal();
        if (current.getTotal() > 21) {
            current.setBust(true);
        }
        this.getShoe().setIndex(this.getShoe().getIndex() + 1);
        return current;
    }

    public void stand() {
        this.getPlayers().get(this.getCurrentPlayer()).clearButtons();
        if (this.getCurrentPlayer() == this.getPlayers().size() - 1) {
            this.playDealer();
        } else {
            this.setCurrentPlayer(this.getCurrentPlayer() + 1);
            this.getPlayers().get(this.getCurrentPlayer()).setButtons();
        }
    }
}
