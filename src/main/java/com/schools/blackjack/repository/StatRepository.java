package com.schools.blackjack.repository;

import com.schools.blackjack.model.ShoeStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<ShoeStat, Integer> {
}
