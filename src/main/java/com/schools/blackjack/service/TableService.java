package com.schools.blackjack.service;

import com.schools.blackjack.model.Table;

public interface TableService {
    Table add(Table table);
    void update (Table table);
    Table getById (int id);
    void delete (int id);

    Table initializeTable(int numPlayers);
}
