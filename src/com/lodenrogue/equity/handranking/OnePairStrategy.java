package com.lodenrogue.equity.handranking;

import java.util.Arrays;

import com.lodenrogue.equity.Card;

public class OnePairStrategy implements RankStrategy {

	@Override
	public boolean isRank(Card... cards) {
		if (cards.length < 2) {
			return false;
		}

		int[] rankValues = new int[cards.length];
		int i = 0;
		for (Card c : cards) {
			rankValues[i++] = c.getRank().getValue();
		}

		Arrays.sort(rankValues);
		for (int j = 0; j < cards.length - 1; j++) {
			if (rankValues[j] == rankValues[j + 1]) {
				return true;
			}
		}
		return false;
	}
}
