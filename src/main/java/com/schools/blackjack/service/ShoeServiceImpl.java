package com.schools.blackjack.service;

import com.schools.blackjack.model.Card;
import com.schools.blackjack.model.Shoe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ShoeServiceImpl implements ShoeService{
    @Override
    public Shoe loadShoe(Shoe shoe) {
        shoe.setNumDecks(8);
        List<Card> cards = new ArrayList<>();
        shoe.setShoeCards(cards);
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

    public Shoe shuffleShoe(Shoe shoe) {
        Random r = new Random();
        int pent = r.nextInt(52) + 312;
        shoe.setYellow(pent);
        for (int i = shoe.getShoeCards().size() - 1; i > 1; i--) {
            int num = r.nextInt(i);
            Card temp = shoe.getShoeCards().get(num);
            Card last = shoe.getShoeCards().get(i);
            shoe.getShoeCards().remove(num);
            shoe.getShoeCards().add(num, last);
            shoe.getShoeCards().remove(i);
            shoe.getShoeCards().add(i, temp);
        }
        shoe.setIndex(1);
        return shoe;
    }
}

