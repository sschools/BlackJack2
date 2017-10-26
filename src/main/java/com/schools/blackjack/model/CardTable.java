package com.schools.blackjack.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "blackjack_tables")
public class CardTable {
    private int id;
    private Shoe shoe;
    private List<Player> players;
    private Dealer dealer;

    public CardTable() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
}
