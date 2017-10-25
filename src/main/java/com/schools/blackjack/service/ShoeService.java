package com.schools.blackjack.service;

import com.schools.blackjack.model.Shoe;

public interface ShoeService {
    Shoe loadShoe(Shoe shoe);
    Shoe shuffleShoe(Shoe shoe);
}
