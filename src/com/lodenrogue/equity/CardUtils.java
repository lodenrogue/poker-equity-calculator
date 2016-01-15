package com.lodenrogue.equity;

import java.util.ArrayList;
import java.util.List;

public class CardUtils {

	private CardUtils() {

	}

	public static Suit getSuit(char suit) {
		if (suit == 'S' || suit == 's') {
			return Suit.SPADE;
		}
		if (suit == 'D' || suit == 'd') {
			return Suit.DIAMOND;
		}
		if (suit == 'H' || suit == 'h') {
			return Suit.HEART;
		}
		if (suit == 'C' || suit == 'c') {
			return Suit.CLUB;
		}
		return null;
	}

	public static Rank getRank(char rank) {
		if (rank == 'A' || rank == 'a') {
			return Rank.ACE;
		}
		if (rank == '2') {
			return Rank.TWO;
		}
		if (rank == '3') {
			return Rank.THREE;
		}
		if (rank == '4') {
			return Rank.FOUR;
		}
		if (rank == '5') {
			return Rank.FIVE;
		}
		if (rank == '6') {
			return Rank.SIX;
		}
		if (rank == '7') {
			return Rank.SEVEN;
		}
		if (rank == '8') {
			return Rank.EIGHT;
		}
		if (rank == '9') {
			return Rank.NINE;
		}
		if (rank == 'T' || rank == 't') {
			return Rank.TEN;
		}
		if (rank == 'J' || rank == 'j') {
			return Rank.JACK;
		}
		if (rank == 'Q' || rank == 'q') {
			return Rank.QUEEN;
		}
		if (rank == 'K' || rank == 'k') {
			return Rank.KING;
		}
		return null;
	}

	public static List<Card> parseCards(String cards, Deck deck) {
		List<Card> cardList = new ArrayList<>();

		if (cards.equalsIgnoreCase("random")) {
			cardList.add(deck.deal());
			cardList.add(deck.deal());
		}
		else {
			if (cards.length() != 4) {
				return null;
			}

			char[] chars = cards.toCharArray();
			Rank rank1 = CardUtils.getRank(chars[0]);
			Suit suit1 = CardUtils.getSuit(chars[1]);

			Rank rank2 = CardUtils.getRank(chars[2]);
			Suit suit2 = CardUtils.getSuit(chars[3]);

			if (rank1 == null || suit1 == null || rank2 == null || suit2 == null) {
				return null;
			}

			Card card1 = deck.getCard(rank1, suit1);
			Card card2 = deck.getCard(rank2, suit2);

			if (card1 == null || card2 == null) {
				return null;
			}

			cardList.add(new Card(rank1, suit1));
			cardList.add(new Card(rank2, suit2));
		}

		return cardList;
	}

}
