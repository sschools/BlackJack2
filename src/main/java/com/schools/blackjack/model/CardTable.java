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
    private boolean endRound;

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

    public boolean isEndRound() {
        return endRound;
    }

    public void setEndRound(boolean endRound) {
        this.endRound = endRound;
    }

    public void dealerHasBlackJack() {
        String name = this.getDealer().getHand().getCards().get(1).getName();
        String suit = this.getDealer().getHand().getCards().get(1).getSuit();
        this.getDealer().getHand().getCards().get(1).setAbName(name + suit.substring(0,1));
        for (Player player : this.players) {
            if (player.getHands().get(0).blackJack()) {
                player.getHands().get(0).setWin(0);
            } else {
                player.getHands().get(0).setWin(-1);
            }
        }
        this.setMessage("Dealer Has Black Jack");
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
        this.setEndRound(true);
    }

    public void setWins() {
        for (Player player : this.players) {
            for (Hand hand : player.getHands()) {
                if (hand.isBust()) {
                    hand.setWin(-1);
                    hand.setMessage(hand.getMessage() + " Lose");
                } else if (hand.getTotal() == 21 && hand.getCards().size() == 2) {
                    hand.setWin(1.5);
                    hand.setMessage("BlackJack!!");
                } else if (this.getDealer().getHand().isBust()) {
                    hand.setWin(1);
                    hand.setMessage(hand.getMessage() + " Winner!");
                } else if (hand.getTotal() > this.getDealer().getHand().getTotal()) {
                    hand.setWin(1);
                    hand.setMessage(hand.getMessage() + " Winner!");
                } else if (hand.getTotal() == this.getDealer().getHand().getTotal()) {
                    hand.setWin(0);
                    hand.setMessage(hand.getMessage() + " Push");
                } else {
                    hand.setWin(-1);
                    hand.setMessage(hand.getMessage() + " Lose");
                }
                if (hand.isDoubleDown()) {
                    hand.setWin(hand.getWin() * 2);
                }
            }
        }
        this.endHand();
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
        this.setWins();
    }

    public Hand hit(Hand hand) {
        Card next = this.getShoe().getShoeCards().get(this.getShoe().getIndex());
        // this gets first hand, will need to adjust for multiple hands after a split

        Hand current = hand;
        if (next.getValue() == 1) {
            current.setAce(true);
        }
        current.getCards().add(next);
        current.setTotal();
        if (current.getTotal() > 21) {
            current.setBust(true);
        }
        this.getShoe().setIndex(this.getShoe().getIndex() + 1);
        return current;
    }

    public void stand() {
        int currentP = this.getCurrentPlayer();
        int currentH = this.getPlayers().get(currentP).getCurrentHand();
        this.getPlayers().get(currentP).clearButtons();
        if (currentH < this.getPlayers().get(currentP).getHands().size() - 1) {
            this.getPlayers().get(currentP).getHands().get(currentH).setActive(false); //this is for the thymeleaf to move the buttons
            this.getPlayers().get(currentP).setCurrentHand(currentH + 1);              //after a stand on a split hand
            this.getPlayers().get(currentP).getHands().get(currentH +1).setActive(true);
            this.getPlayers().get(this.getCurrentPlayer()).setButtons();
        } else if (this.getCurrentPlayer() == this.getPlayers().size() - 1) {
            this.playDealer();
        } else {
            this.setCurrentPlayer(this.getCurrentPlayer() + 1);
            this.getPlayers().get(this.getCurrentPlayer()).setCurrentHand(0);
            this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).setActive(true);
            this.getPlayers().get(this.getCurrentPlayer()).setButtons();
        }
    }
}
