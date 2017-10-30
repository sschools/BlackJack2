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
            int pNum = i + 1;
            temp.setName("Player #" + Integer.toString(pNum));
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
            player.setHands(hands);
        }
        int i = table.getPlayers().size();
        for (int j = 0; j < i; j++) {
            Hand currentHand = players.get(j).getHands().get(0);
            currentHand.getCards().add(shoe.getShoeCards().get(shoe.getIndex() + j));
            currentHand.getCards().add(shoe.getShoeCards().get(shoe.getIndex() + j + i + 1));
            List<Hand> hands = new ArrayList<>();
            hands.add(currentHand);
            players.get(j).setHands(hands);
        }
        Hand dealerHand = dealer.getHand();
        dealerHand.getCards().add(shoe.getShoeCards().get(shoe.getIndex() + i));
        dealerHand.getCards().add(shoe.getShoeCards().get(shoe.getIndex() + i + i));
        dealer.setHand(dealerHand);

        int currentLoc = shoe.getIndex();
        currentLoc += 2*(i+1);
        shoe.setIndex(currentLoc);

        table.setDealer(dealer);
        table.setPlayers(players);
        table.setShoe(shoe);

        return table;
    }
}
