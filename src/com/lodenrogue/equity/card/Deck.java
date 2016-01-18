package com.lodenrogue.equity.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Deck {
	private static ArrayList<Card> protoDeck;
	private ArrayList<Card> userDeck;
	private Random rand;

	static {
		protoDeck = new ArrayList<>();
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				protoDeck.add(new Card(rank, suit));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Deck() {
		rand = new Random();
		userDeck = (ArrayList<Card>) protoDeck.clone();
	}

	public void shuffle() {
		@SuppressWarnings("unchecked")
		ArrayList<Card> tmpUserDeck = (ArrayList<Card>) protoDeck.clone();

		userDeck.clear();
		while (tmpUserDeck.size() > 0) {
			int rCard = rand.nextInt(tmpUserDeck.size());
			userDeck.add(tmpUserDeck.remove(rCard));
		}
	}

	public Card deal() {
		if (userDeck.size() > 0) {
			return userDeck.remove(0);
		}
		return null;
	}

	@Override
	public String toString() {
		return Arrays.toString(userDeck.toArray());
	}

	public Card getCard(Rank rank, Suit suit) {
		for (Card card : userDeck) {
			if (card.getRank().equals(rank) && card.getSuit().equals(suit)) {
				userDeck.remove(card);
				return card;
			}
		}
		return null;
	}

}
