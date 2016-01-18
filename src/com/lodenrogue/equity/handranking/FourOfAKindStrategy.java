package com.lodenrogue.equity.handranking;

import java.util.Arrays;

import com.lodenrogue.equity.card.Card;

public class FourOfAKindStrategy implements RankStrategy {

	@Override
	public boolean isRank(Card... cards) {
		if (cards.length < 4) {
			return false;
		}

		int[] rankValues = new int[cards.length];

		int i = 0;
		for (Card c : cards) {
			rankValues[i++] = c.getRank().getValue();
		}

		Arrays.sort(rankValues);
		int iterations = Math.max(cards.length - 3, 0);
		for (int j = 0; j < iterations; j++) {
			if (rankValues[j] == rankValues[j + 1]) {
				if (rankValues[j] == rankValues[j + 2]) {
					if (rankValues[j] == rankValues[j + 3]) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
