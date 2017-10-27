package com.schools.blackjack.service;

import com.schools.blackjack.model.CardTable;
import com.schools.blackjack.model.Dealer;
import com.schools.blackjack.model.Player;
import com.schools.blackjack.model.Shoe;
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
}
