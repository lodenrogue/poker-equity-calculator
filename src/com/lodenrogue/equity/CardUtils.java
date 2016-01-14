package com.lodenrogue.equity;

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

}
