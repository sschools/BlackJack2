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
    private boolean endShoe;
    private ShoeStat shoeStat;

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

    private void setDealer(Dealer dealer) {
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

    private void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isEndRound() {
        return endRound;
    }

    private void setEndRound(boolean endRound) {
        this.endRound = endRound;
    }

    public boolean isEndShoe() {
        return endShoe;
    }

    public void setEndShoe(boolean endShoe) {
        this.endShoe = endShoe;
    }

    public ShoeStat getShoeStat() {
        return shoeStat;
    }

    private void setShoeStat(ShoeStat shoeStat) {
        this.shoeStat = shoeStat;
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

    private void endHand() {
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
        //end of shoe settings
        if (this.getShoe().getIndex() > this.getShoe().getYellow()) {
            this.setEndShoe(true); //thymeleaf to show stats button
            this.setMessage("End of Shoe.");
            this.getShoe().endBankrolls = new ArrayList<>();
            for (Player player : this.getPlayers()) {
                this.getShoe().endBankrolls.add(player.getBankroll().get(0));

            }
            int totalDiff = 0;
            int max = 0;
            int min = 0;
            for (int i = 0; i < this.getPlayers().size(); i++) {
                int tempDiff = this.getShoe().endBankrolls.get(i) - this.getShoe().initBankrolls.get(i);
                if (i == 0) {
                    min = tempDiff;
                    max = tempDiff;
                } else {
                    if (tempDiff > max) {
                        max = tempDiff;
                    }
                    if (tempDiff < min) {
                        min = tempDiff;
                    }
                }
                totalDiff += tempDiff;
            }
            this.getShoeStat().setMinBrDelta(min);
            this.getShoeStat().setMaxBrDelta(max);
            this.getShoeStat().setAvBrDelta( (double) totalDiff / this.getShoeStat().getNumPlayers());
            this.getShoeStat().setWinPercent( (double) this.getShoeStat().getWinHands() / this.getShoeStat().getNumHands() * 100);
        } else {
            this.setEndRound(true); //this is for thymeleaf to show deal button
        }

    }

    private void setWins() {
        for (Player player : this.players) {
            for (Hand hand : player.getHands()) {
                if (hand.isBust()) {
                    hand.setWin(-1);
                    hand.setMessage(hand.getMessage() + " Lose");
                } else if (hand.getTotal() == 21 && hand.getCards().size() == 2 && !player.isSplitHands()) {
                    hand.setWin(1.5);
                    this.getShoeStat().setWinHands(this.getShoeStat().getWinHands() + 1);
                    hand.setMessage("BlackJack!!");
                } else if (this.getDealer().getHand().isBust()) {
                    hand.setWin(1);
                    this.getShoeStat().setWinHands(this.getShoeStat().getWinHands() + 1);
                    hand.setMessage(hand.getMessage() + " Winner!");
                } else if (hand.getTotal() > this.getDealer().getHand().getTotal()) {
                    hand.setWin(1);
                    this.getShoeStat().setWinHands(this.getShoeStat().getWinHands() + 1);
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

    private void playDealer() {
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
            currentP += 1;
            this.setCurrentPlayer(this.getCurrentPlayer() + 1);
            this.getPlayers().get(this.getCurrentPlayer()).setCurrentHand(0);
            this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).setActive(true);
            this.getPlayers().get(this.getCurrentPlayer()).setButtons();
            this.getPlayers().get(this.getCurrentPlayer()).setSplitHands(false);
            this.getPlayers().get(currentP).getHands().get(0).setTotal();
            if (this.getPlayers().get(currentP).getHands().get(0).getTotal() == 21) {
                this.getPlayers().get(currentP).getHands().get(0).setMessage("BlackJack!!!");
                this.stand();
            }
        }
    }

    public void resetShoe(Shoe shoe) {
        shoe.shuffleShoe();
        this.setStats();
        this.dealCards();
        this.setMessage("");
        this.setEndShoe(false);
    }

    public void setStats() {
        ShoeStat tempStat = new ShoeStat();
        tempStat.setNumPlayers(this.getPlayers().size());
        System.out.println("NumPlayers from set stats method: " + this.getPlayers().size());
        tempStat.setNumHands(0);
        tempStat.setWinHands(0);
        tempStat.setWinHands(0);
        tempStat.setBetStrat(1);
        tempStat.setAvBrDelta(0);
        tempStat.setMaxBrDelta(0);
        tempStat.setMinBrDelta(0);
        this.setShoeStat(tempStat);
        this.getShoe().initBankrolls = new ArrayList<>();
        for (Player player : this.getPlayers()) {
            this.getShoe().initBankrolls.add(player.getBankroll().get(0));
        }
    }

    public void initializeTable(int numPlayers, CardTable cardTable) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            Player newPlayer = new Player();
            newPlayer.setBets(new ArrayList<>());
            newPlayer.setBankroll(new ArrayList<>());
            int pNum = i + 1;
            newPlayer.setName("Player #" + Integer.toString(pNum));
            newPlayer.getBets().add(2);
            newPlayer.getBankroll().add(1000);
            newPlayer.setCanHit(false);
            newPlayer.setCanStand(false);
            newPlayer.setCanDouble(false);
            newPlayer.setCanSplit(false);
            players.add(newPlayer);
        }
        cardTable.setPlayers(players);
        Shoe shoe = new Shoe();
        cardTable.setShoe(shoe.loadShoe());
        cardTable.setShoe(shoe.shuffleShoe());

        Dealer dealer = new Dealer();
        cardTable.setDealer(dealer);
        cardTable.setStats();
        cardTable.dealCards();
    }

    public void dealCards() {
        this.setEndRound(false);
        this.setMessage("");
        Shoe shoe = this.getShoe();
        Dealer dealer = this.getDealer();
        List<Player> players = this.getPlayers();
        for (Player player : players) {
            List<Hand> hands = new ArrayList<>();
            Hand hand = new Hand();
            hands.add(hand);
            player.setHands(hands);
            player.setCurrentHand(0);
        }
        // deals cards to players at table
        int i = this.getPlayers().size();
        for (int j = 0; j < i; j++) {
            Hand currentHand = players.get(j).getHands().get(0);
            List<Card> cards = new ArrayList<>();
            currentHand.setCards(cards);
            currentHand.setAce(false);
            currentHand.setMessage("");
            currentHand.setActive(false);
            Card first = shoe.getShoeCards().get(shoe.getIndex() + j);
            Card second = shoe.getShoeCards().get(shoe.getIndex() + j + i + 1);
            currentHand.getCards().add(first);
            currentHand.getCards().add(second);
            if (first.getName().equals("A") || second.getName().equals("A")) {
                currentHand.setAce(true);
            }
            currentHand.setTotal();
            List<Hand> hands = new ArrayList<>();
            hands.add(currentHand);
            players.get(j).setHands(hands);
            this.getShoeStat().setNumHands(this.getShoeStat().getNumHands() + 1);
        }

        //deals dealers cards
        Hand hand = new Hand();
        dealer.setHand(hand);
        Hand dealerHand = dealer.getHand();
        List<Card> cards1 = new ArrayList<>();
        dealerHand.setCards(cards1);
        dealerHand.setAce(false);
        dealerHand.getCards().add(shoe.getShoeCards().get(shoe.getIndex() + i));
        dealerHand.getCards().add(shoe.getShoeCards().get(shoe.getIndex() + i + i + 1));
        dealerHand.getCards().get(1).setAbName("**");
        if (dealerHand.getCards().get(0).getName().equals("A") || dealerHand.getCards().get(1).getName().equals("A")) {
            dealerHand.setAce(true);
        }
        dealer.setHand(dealerHand);

        //moves location in shoe
        int currentLoc = shoe.getIndex();
        currentLoc += 2*(i+1);
        shoe.setIndex(currentLoc);

        this.setDealer(dealer);
        this.setPlayers(players);
        this.setShoe(shoe);

        //handles dealer having blackjack
        if (dealerHand.blackJack()) {
            this.dealerHasBlackJack();
        } else {
            this.getPlayers().get(0).setButtons();
            this.setCurrentPlayer(0);
            this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).setActive(true);
            this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).setTotal();
            if (this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).getTotal() == 21) {
                this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).setMessage("BlackJack!!!");
                this.stand();
            }
        }
    }
}
