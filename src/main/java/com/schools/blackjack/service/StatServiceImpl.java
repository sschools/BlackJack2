package com.schools.blackjack.service;

import com.schools.blackjack.model.ShoeStat;
import com.schools.blackjack.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StatServiceImpl implements StatService {
    @Autowired
    StatRepository statRepository;

    @Override
    public ShoeStat add(ShoeStat shoeStat) {
        return statRepository.save(shoeStat);
    }
}
