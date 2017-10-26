package com.schools.blackjack.service;

import com.schools.blackjack.model.CardTable;
import com.schools.blackjack.model.Dealer;
import com.schools.blackjack.model.Player;
import com.schools.blackjack.model.Shoe;
import com.schools.blackjack.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {
    @Autowired
    TableRepository tableRepository;

    @Override
    public CardTable add(CardTable cardTable) {
        return tableRepository.save(cardTable);
    }

    @Override
    public void update(CardTable cardTable) {
        tableRepository.save(cardTable);
    }

    @Override
    public CardTable getById(int id) {
        return tableRepository.findOne(id);
    }

    @Override
    public void delete(int id) {
        tableRepository.delete(id);
    }

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

        return tableRepository.save(cardTable);
    }
}
