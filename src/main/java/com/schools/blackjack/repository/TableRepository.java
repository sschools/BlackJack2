package com.schools.blackjack.repository;

import com.schools.blackjack.model.CardTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<CardTable, Integer>{
}
