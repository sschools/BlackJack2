package com.schools.blackjack.service;

import com.schools.blackjack.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Override
    public CardTable initializeTable(int numPlayers) {
        CardTable cardTable = new CardTable();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            Player temp = new Player();
            temp.setBets(new ArrayList<>());
            temp.setBankroll(new ArrayList<>());
            int pNum = i + 1;
            temp.setName("Player #" + Integer.toString(pNum));
            temp.getBets().add(2);
            temp.getBankroll().add(1000);
            temp.setCanHit(false);
            temp.setCanStand(false);
            temp.setCanDouble(false);
            temp.setCanSplit(false);
            players.add(temp);
        }
        cardTable.setPlayers(players);
        Shoe shoe = new Shoe();
        cardTable.setShoe(shoe);

        Dealer dealer = new Dealer();
        cardTable.setDealer(dealer);

        return cardTable;
    }

    public CardTable dealCards(CardTable table) {
        table.setEndRound(false);
        Shoe shoe = table.getShoe();
        Dealer dealer = table.getDealer();
        List<Player> players = table.getPlayers();
        for (Player player : players) {
            List<Hand> hands = new ArrayList<>();
            Hand hand = new Hand();
            hands.add(hand);
            player.setHands(hands);
        }
        // deals cards to players at table
        int i = table.getPlayers().size();
        for (int j = 0; j < i; j++) {
            Hand currentHand = players.get(j).getHands().get(0);
            List<Card> cards = new ArrayList<>();
            currentHand.setCards(cards);
            currentHand.setAce(false);
            currentHand.setMessage("");
            Card first = shoe.getShoeCards().get(shoe.getIndex() + j);
            Card second = shoe.getShoeCards().get(shoe.getIndex() + j + i + 1);
            currentHand.getCards().add(first);
            currentHand.getCards().add(second);
            if (first.getName().equals("A") || second.getName().equals("A")) {
                currentHand.setAce(true);
            }
            currentHand.setTotal();
            //if statement here to adjust total for ace ... maybe
            List<Hand> hands = new ArrayList<>();
            hands.add(currentHand);
            players.get(j).setHands(hands);
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

        table.setDealer(dealer);
        table.setPlayers(players);
        table.setShoe(shoe);

        //handles dealer having blackjack
        if (dealerHand.blackJack()) {
            table.dealerHasBlackJack();
        } else {
            table.getPlayers().get(0).setButtons();
            table.setCurrentPlayer(0);
        }

        return table;
    }
}
