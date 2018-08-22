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
    private String gameType;
    private String pace;
    private int numShoes;

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

    private void setShoe(Shoe shoe) {
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

    private void setMessage(String message) {
        this.message = message;
    }

    private int getCurrentPlayer() {
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

    private void setEndShoe(boolean endShoe) {
        this.endShoe = endShoe;
    }

    public ShoeStat getShoeStat() {
        return shoeStat;
    }

    private void setShoeStat(ShoeStat shoeStat) {
        this.shoeStat = shoeStat;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getPace() {
        return pace;
    }

    public void setPace(String pace) {
        this.pace = pace;
    }

    public int getNumShoes() {
        return numShoes;
    }

    public void setNumShoes(int numShoes) {
        this.numShoes = numShoes;
    }

    public void dealerHasBlackJack() {
        this.getDealer().setDealerShortName(this.getDealer().getHand().getCards().get(1));
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

        this.adjustBankrolls();

        if (this.getShoe().getIndex() > this.getShoe().getYellow()) {
            this.endShoeInfo();
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
        this.getDealer().setDealerShortName(this.getDealer().getHand().getCards().get(1));
        Hand hand = this.getDealer().getHand();
        hand.setTotal();
        boolean allBusted = true;
        for (Player player : this.getPlayers()) {
            for (Hand playerHand : player.getHands()) {
                if (!playerHand.isBust()) {
                    allBusted = false;
                }
            }
        }
        if (!allBusted) {
            while (hand.getTotal() < 17) {
                hand.hit(this.getShoe());
                hand.setTotal();
            }
        }

        this.getDealer().setHand(hand);
        this.setWins();
    }

    private void stand() {
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

    private void setStats() {
        ShoeStat tempStat = new ShoeStat();
        tempStat.setNumPlayers(this.getPlayers().size());
        tempStat.setNumHands(0);
        tempStat.setWinHands(0);
        tempStat.setWinHands(0);
        tempStat.setBetStrat(1);
        tempStat.setAvBrDelta(0);
        tempStat.setMaxBrDelta(0);
        tempStat.setMinBrDelta(0);
        this.setShoeStat(tempStat);
        this.getShoe().setInitBankrolls(new ArrayList<>());
        for (Player player : this.getPlayers()) {
            this.getShoe().getInitBankrolls().add(player.getBankroll().get(0));
        }
    }

    public void initializeTable(int numPlayers, String gameType) {
        this.setGameType(gameType);
        this.setPlayers(this.initializePlayers(numPlayers));

        Shoe shoe = new Shoe();
        this.setShoe(shoe.loadShoe());
        this.setShoe(shoe.shuffleShoe());

        this.setDealer(new Dealer());
        this.setStats();
        this.dealCards();
    }

    public void dealCards() {
        this.resetRound();
        // deals cards to players at table
        int i = this.getPlayers().size();
        for (int j = 0; j < this.getPlayers().size(); j++) {
            Hand currentHand = this.getPlayers().get(j).getHands().get(0);
            List<Card> cards = new ArrayList<>();
            currentHand.setCards(cards);
            currentHand.setAce(false);
            currentHand.setMessage("");
            currentHand.setActive(false);
            Card first = this.getShoe().getShoeCards().get(this.getShoe().getIndex() + j);
            Card second = this.getShoe().getShoeCards().get(this.getShoe().getIndex() + j + i + 1);
            currentHand.getCards().add(first);
            currentHand.getCards().add(second);
            if (first.getName().equals("A") || second.getName().equals("A")) {
                currentHand.setAce(true);
            }
            currentHand.setTotal();
            List<Hand> hands = new ArrayList<>();
            hands.add(currentHand);
            this.getPlayers().get(j).setHands(hands);
            this.getShoeStat().setNumHands(this.getShoeStat().getNumHands() + 1);
        }

        //deals dealers cards
        this.getDealer().setHand(new Hand());
        Hand dealerHand = this.getDealer().getHand();
        dealerHand.setCards(new ArrayList<>());
        dealerHand.setAce(false);
        dealerHand.getCards().add(this.getShoe().getShoeCards().get(this.getShoe().getIndex() + i));
        dealerHand.getCards().add(this.getShoe().getShoeCards().get(this.getShoe().getIndex() + i + i + 1));
        dealerHand.getCards().get(1).setAbName("**");
        if (dealerHand.getCards().get(0).getName().equals("A") || dealerHand.getCards().get(1).getName().equals("A")) {
            dealerHand.setAce(true);
        }
        this.getDealer().setHand(dealerHand);

        //moves location in shoe
        int currentLoc = this.getShoe().getIndex();
        currentLoc += 2*(i+1);
        this.getShoe().setIndex(currentLoc);

        //handles dealer having blackjack
        if (dealerHand.blackJack()) {
            this.dealerHasBlackJack();
        } else {
            if (this.gameType.equals("manual")) {
                this.getPlayers().get(0).setButtons();
            }
            this.setCurrentPlayer(0);
            this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).setActive(true);
            this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).setTotal();
            if (this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).getTotal() == 21) {
                this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0).setMessage("BlackJack!!!");
                this.stand();
            }
        }
        if (this.gameType.equals("simMultiple")) {
            this.autoPlay();
        }
    }

    public void doAction(String action) {
        switch (action) {
            case "hit":
            case "double":
                int currentPlayerNum = this.getCurrentPlayer();
                int currentHandNum = this.getPlayers().get(currentPlayerNum).getCurrentHand();
                Hand hand = this.getPlayers().get(currentPlayerNum).getHands().get(currentHandNum);
                hand.hit(this.getShoe());
                List<Hand> hands = this.getPlayers().get(currentPlayerNum).getHands();
                if (action.equals("double")) {
                    hand.setDoubleDown(true);
                    hand.setMessage(hand.getMessage() + "Double Down");
                }
                hands.remove(this.getPlayers().get(currentPlayerNum).getHands().get(currentHandNum));
                hands.add(currentHandNum, hand);
                this.getPlayers().get(currentPlayerNum).setHands(hands);
                this.getPlayers().get(currentPlayerNum).setButtons();
                if (hand.isBust() || hand.getTotal() == 21 || hand.isDoubleDown()) {
                    this.stand();
                }
                break;
            case "stand":
                this.stand();
                break;
            case "split":
                Player current = this.getPlayers().get(this.getCurrentPlayer());
                current.setSplitHands(true);
                this.getShoeStat().setNumHands(this.getShoeStat().getNumHands() + 1);
                current.split();
                break;
        }
    }

    public void setUpMultipleSimulation(String pace, int numShoes, CardTable cardTable) {
        cardTable.setPace(pace);
        cardTable.setNumShoes(numShoes);
    }

    private List<Player> initializePlayers(int numPlayers) {
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
            newPlayer.resetWinList();
            players.add(newPlayer);
        }
        return players;
    }

    private void resetRound() {
        this.setEndRound(false);
        this.setMessage("");
        for (Player player : this.getPlayers()) {
            List<Hand> hands = new ArrayList<>();
            hands.add(new Hand());
            player.setHands(hands);
            player.setCurrentHand(0);
        }
    }

    private void adjustBankrolls() {
        for (Player player : this.getPlayers()) {
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

    private void endShoeInfo() {
        this.setEndShoe(true); //thymeleaf to show stats button
        this.setMessage("End of Shoe.");
        this.getShoe().setEndBankrolls(new ArrayList<>());
        for (Player player : this.getPlayers()) {
            this.getShoe().getEndBankrolls().add(player.getBankroll().get(0));

        }
        int totalDiff = 0;
        int max = 0;
        int min = 0;
        for (int i = 0; i < this.getPlayers().size(); i++) {
            int tempDiff = this.getShoe().getEndBankrolls().get(i) - this.getShoe().getInitBankrolls().get(i);
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
        this.getShoeStat().setWinPercent( (double) this.getShoeStat().getWinHands() /
                this.getShoeStat().getNumHands() * 100);
    }

    private void autoPlay() {
        Hand hand = this.getPlayers().get(this.getCurrentPlayer()).getHands().get(0); //TODO: need to make this current hand
        if (hand.getCards().size() == 2) {
            String action = hand.decisionWith2Cards(this.getDealer().getHand().getCards().get(0).getValue());
            this.doAction(action);
        }
    }
}
