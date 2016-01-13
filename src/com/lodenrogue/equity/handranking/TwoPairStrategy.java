package com.lodenrogue.equity.handranking;

import java.util.Arrays;

import com.lodenrogue.equity.Card;

public class TwoPairStrategy implements RankStrategy {

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
		int iterations = cards.length - 1;

		// Find the first pair
		int firstPairValue = 0;
		for (int j = 0; j < iterations; j++) {
			if (rankValues[j] == rankValues[j + 1]) {
				firstPairValue = rankValues[j];

			}
		}

		// If a pair was found, find the other one
		if (firstPairValue != 0) {
			for (int j = 0; j < 3; j++) {
				if (rankValues[j] != firstPairValue) {
					if (rankValues[j] == rankValues[j + 1]) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
