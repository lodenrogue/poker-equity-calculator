package com.lodenrogue.equity.card;

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

	private static Card parseCard(String cardString, Deck deck) {
		if (cardString.length() != 2) {
			return null;
		}
		else {
			Rank rank = CardUtils.getRank(cardString.charAt(0));
			Suit suit = CardUtils.getSuit(cardString.charAt(1));
			if (rank == null || suit == null) {
				return null;
			}

			return deck.getCard(rank, suit);
		}

	}

	public static List<Card> parseBoard(String cards, Deck deck) {
		List<Card> cardList = new ArrayList<>();

		if (cards.length() > 0 && cards.length() % 2 == 0 && cards.length() <= 10) {
			char[] chars = cards.toCharArray();

			for (int i = 0; i < chars.length - 1; i += 2) {
				Card card = CardUtils.parseCard(String.valueOf(chars[i]) + String.valueOf(chars[i + 1]), deck);
				if (card == null) {
					return null;
				}
				else {
					cardList.add(card);
				}
			}

			return cardList;
		}
		else {
			return null;
		}
	}

	public static List<Card> parseHand(String cards, Deck deck) {
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
			Card card1 = CardUtils.parseCard(String.valueOf(chars[0]) + String.valueOf(chars[1]), deck);
			Card card2 = CardUtils.parseCard(String.valueOf(chars[2]) + String.valueOf(chars[3]), deck);

			if (card1 == null || card2 == null) {
				return null;
			}

			cardList.add(card1);
			cardList.add(card2);
		}

		return cardList;
	}

}
