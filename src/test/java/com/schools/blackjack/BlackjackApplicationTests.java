package com.schools.blackjack;

import com.schools.blackjack.model.Card;
import com.schools.blackjack.model.Hand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;

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

}
