package com.schools.blackjack;

import com.schools.blackjack.model.Card;
import com.schools.blackjack.model.CardTable;
import com.schools.blackjack.model.Hand;
import com.schools.blackjack.model.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlackjackApplicationTests {

	@Test
	public void testBlackJack() {
		Hand hand1 = new Hand();
		Hand hand2 = new Hand();
		Hand hand3 = new Hand();
		Hand hand4 = new Hand();
		Hand hand5 = new Hand();
		hand1.setCards(new ArrayList<>());
		hand2.setCards(new ArrayList<>());
		hand3.setCards(new ArrayList<>());
		hand4.setCards(new ArrayList<>());
		hand5.setCards(new ArrayList<>());
		hand1.getCards().add(new Card(1));
		hand1.getCards().add(new Card(10));
		hand2.getCards().add(new Card(10));
		hand2.getCards().add(new Card(1));
		hand3.getCards().add(new Card(10));
		hand3.getCards().add(new Card(10));
		hand4.getCards().add(new Card(1));
		hand4.getCards().add(new Card(1));
		hand5.getCards().add(new Card(8));
		hand5.getCards().add(new Card(2));

		assertTrue(hand1.blackJack());
		assertTrue(hand2.blackJack());
		assertTrue(!hand3.blackJack());
		assertTrue(!hand4.blackJack());
		assertTrue(!hand5.blackJack());
	}

	@Test
	public void testEndHand() {
		//need players with one hand each, bets, and bankrolls
		CardTable table = new CardTable();
		Player player1 = new Player();
		Player player2 = new Player();
		Hand hand1 = new Hand();
		Hand hand2 = new Hand();
		hand1.setCards(new ArrayList<>());
		hand2.setCards(new ArrayList<>());
		hand1.getCards().add(new Card(8));
		hand1.getCards().add(new Card(10));
		hand2.getCards().add(new Card(10));
		hand2.getCards().add(new Card(1));
		List<Hand> hands1 = new ArrayList<>();
		List<Hand> hands2 = new ArrayList<>();
		hands1.add(hand1);
		hands2.add(hand2);
		player1.setHands(hands1);
		player2.setHands(hands2);
		List<Integer> bets1 = new ArrayList<>();
		List<Integer> bets2 = new ArrayList<>();
		List<Integer> bankroll1 = new ArrayList<>();
		List<Integer> bankroll2 = new ArrayList<>();
		bets1.add(2);
		bets1.add(10);
		bets2.add(3);
		bankroll1.add(1000);
		bankroll1.add(1000);
		bankroll2.add(1000);
		player1.setBets(bets1);
		player2.setBets(bets2);
		player1.setBankroll(bankroll1);
		player2.setBankroll(bankroll2);
		List<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		table.setPlayers(players);
		table.dealerHasBlackJack();
		System.out.println("Player 1 win value: " + player1.getHands().get(0).getWin());
		System.out.println("Player 2 win value: " + player2.getHands().get(0).getWin());

		assertTrue(player1.getBankroll().get(0) == 998);
		assertTrue(player1.getBankroll().get(1) == 990);
		assertTrue(player2.getBankroll().get(0) == 1000);
	}

}
