package com.schools.blackjack.service;

import com.schools.blackjack.model.Dealer;
import com.schools.blackjack.model.Player;
import com.schools.blackjack.model.Shoe;
import com.schools.blackjack.model.Table;
import com.schools.blackjack.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TableServiceImpl implements TableService {
    @Autowired
    TableRepository tableRepository;

    @Override
    public Table add(Table table) {
        return tableRepository.save(table);
    }

    @Override
    public void update(Table table) {
        tableRepository.save(table);
    }

    @Override
    public Table getById(int id) {
        return tableRepository.findOne(id);
    }

    @Override
    public void delete(int id) {
        tableRepository.delete(id);
    }

    @Override
    public Table initializeTable(int numPlayers) {
        Table table = new Table();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            Player temp = new Player();
            int pNum = i + 1;
            temp.setName("Player #" + Integer.toString(pNum));
            players.add(temp);
        }
        table.setPlayers(players);
        Shoe shoe = new Shoe();
        table.setShoe(shoe);

        Dealer dealer = new Dealer();
        table.setDealer(dealer);

        return table;
    }
}
