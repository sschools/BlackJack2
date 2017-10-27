package com.schools.blackjack.service;

import com.schools.blackjack.model.CardTable;

public interface TableService {


    CardTable initializeTable(int numPlayers);
}
