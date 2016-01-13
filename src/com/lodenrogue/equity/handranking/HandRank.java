package com.lodenrogue.equity.handranking;

public enum HandRank {

	STRAIGHT_FLUSH(8), FOUR_OF_A_KIND(7), FULL_HOUSE(6), FLUSH(5), STRAIGHT(4), THREE_OF_A_KIND(3), TWO_PAIR(2), ONE_PAIR(1), HIGH_CARD(0);

	private int rankValue;

	private HandRank(int value) {
		rankValue = value;
	}

	public int getValue() {
		return rankValue;
	}
}
