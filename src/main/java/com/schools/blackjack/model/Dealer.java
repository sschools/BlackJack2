package com.schools.blackjack.model;

public class Dealer {
    private Hand hand;

    public Dealer() {
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Card setDealerShortName(Card card) {
        card.setAbName(card.getName() + card.getSuit().substring(0,1));
        return card;
    }
}
