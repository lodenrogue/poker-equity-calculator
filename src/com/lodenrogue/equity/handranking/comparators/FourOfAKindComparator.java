package com.lodenrogue.equity.handranking.comparators;

import java.util.HashMap;
import java.util.Map;

import com.lodenrogue.equity.Card;
import com.lodenrogue.equity.Rank;

public class FourOfAKindComparator implements CardComparator {

	@Override
	public int compare(Card[] hand1, Card[] hand2) {
		// Get quad
		int hand1Quad = getQuad(hand1);
		int hand2Quad = getQuad(hand2);

		// Compare quads
		if (hand1Quad > hand2Quad) {
			return 1;
		}
		else if (hand1Quad < hand2Quad) {
			return -1;
		}

		// Get kickers
		int hand1Kicker = 0;
		int hand2Kicker = 0;

		for (int i = 0; i < 5; i++) {
			int hand1Value = hand1[i].getRank().getValue();
			if (hand1Value != hand1Quad) {
				hand1Kicker = hand1Value;
			}

			int hand2Value = hand2[i].getRank().getValue();
			if (hand2Value != hand2Quad) {
				hand2Kicker = hand2Value;
			}
		}

		// Compare kickers
		if (hand1Kicker > hand2Kicker) {
			return 1;
		}
		else if (hand1Kicker < hand2Kicker) {
			return -1;
		}
		return 0;
	}

	private int getQuad(Card[] hand) {
		int quad = 0;
		Map<Rank, Integer> rankMap = new HashMap<>();
		for (Card c : hand) {
			if (rankMap.containsKey(c.getRank())) {
				if (rankMap.get(c.getRank()) == 3) {
					quad = c.getRank().getValue();
					break;
				}
				else {
					int value = rankMap.get(c.getRank());
					rankMap.put(c.getRank(), value + 1);
				}
			}
			else {
				rankMap.put(c.getRank(), 1);
			}
		}
		return quad;
	}

}
