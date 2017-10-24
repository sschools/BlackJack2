package com.schools.blackjack.service;

import com.schools.blackjack.model.Card;
import com.schools.blackjack.model.Shoe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoeServiceImpl implements ShoeService{
    @Override
    public Shoe loadShoe(Shoe shoe) {
        List<String> suits = new ArrayList();
        suits.add("Hearts");
        suits.add("Clubs");
        suits.add("Diamonds");
        suits.add("Spades");
        for (int i = 0; i < shoe.getNumDecks(); i++) {
            for(int j = 0; j < suits.size(); j++) {
                for (int k = 2; k < 11; k++) {
                    Card temp = new Card();
                    temp.setValue(k);
                    temp.setName(Integer.toString(k));
                    temp.setSuit(suits.get(j));
                    shoe.getShoeCards().add(temp);
                }
                Card jack = new Card();
                jack.setValue(10);
                jack.setName("J");
                jack.setSuit(suits.get(j));
                Card queen = new Card();
                queen.setValue(10);
                queen.setName("Q");
                queen.setSuit(suits.get(j));
                Card king = new Card();
                king.setValue(10);
                king.setName("K");
                king.setSuit(suits.get(j));
                Card ace = new Card();
                ace.setValue(1);
                ace.setName("A");
                ace.setSuit(suits.get(j));
                shoe.getShoeCards().add(jack);
                shoe.getShoeCards().add(queen);
                shoe.getShoeCards().add(king);
                shoe.getShoeCards().add(ace);
            }
        }
        return shoe;
    }
}

