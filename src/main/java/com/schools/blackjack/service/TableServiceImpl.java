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
        Shoe shoe = table.getShoe();
        Dealer dealer = table.getDealer();
        List<Player> players = table.getPlayers();
        for (Player player : players) {
            List<Hand> hands = new ArrayList<>();
            Hand hand = new Hand();
            hands.add(hand);
            player.setHands(hands);
        }
        int i = table.getPlayers().size();
        for (int j = 0; j < i; j++) {
            Hand currentHand = players.get(j).getHands().get(0);
            List<Card> cards = new ArrayList<>();
            currentHand.setCards(cards);
            currentHand.setAce(false);
            Card first = shoe.getShoeCards().get(shoe.getIndex() + j);
            Card second = shoe.getShoeCards().get(shoe.getIndex() + j + i + 1);
            currentHand.getCards().add(first);
            currentHand.getCards().add(second);
            if (first.getName().equals("A") || second.getName().equals("A")) {
                currentHand.setAce(true);
            }
            currentHand.setTotal(first.getValue() + second.getValue());
            //if statement here to adjust total for ace ... maybe
            List<Hand> hands = new ArrayList<>();
            hands.add(currentHand);
            players.get(j).setHands(hands);
        }
        Hand hand = new Hand();
        dealer.setHand(hand);
        Hand dealerHand = dealer.getHand();
        List<Card> cards1 = new ArrayList<>();
        dealerHand.setCards(cards1);
        dealerHand.getCards().add(shoe.getShoeCards().get(shoe.getIndex() + i));
        dealerHand.getCards().add(shoe.getShoeCards().get(shoe.getIndex() + i + i + 1));
        dealer.setHand(dealerHand);

        table.setDealerBlackJack(dealerHand);  //test for dealer black jack

        int currentLoc = shoe.getIndex();
        currentLoc += 2*(i+1);
        shoe.setIndex(currentLoc);

        table.setDealer(dealer);
        table.setPlayers(players);
        table.setShoe(shoe);

        return table;
    }
}
