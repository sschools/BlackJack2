package com.schools.blackjack.service;

import com.schools.blackjack.model.CardTable;

public interface TableService {
    CardTable add(CardTable cardTable);
    void update (CardTable cardTable);
    CardTable getById (int id);
    void delete (int id);

    CardTable initializeTable(int numPlayers);
}
