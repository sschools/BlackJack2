package com.schools.blackjack.repository;

import com.schools.blackjack.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, Integer>{
}
