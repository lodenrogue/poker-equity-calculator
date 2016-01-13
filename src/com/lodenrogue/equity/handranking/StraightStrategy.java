package com.lodenrogue.equity.handranking;

import java.util.Arrays;

import com.lodenrogue.equity.Card;
import com.lodenrogue.equity.Rank;

public class StraightStrategy implements RankStrategy {

	@Override
	public boolean isRank(Card... cards) {
		if (cards.length != 5) {
			return false;
		}

		boolean includes2 = false;
		Rank[] ranks = new Rank[cards.length];

		int i = 0;
		for (Card c : cards) {
			Rank r = c.getRank();
			if (r.equals(Rank.TWO)) {
				includes2 = true;
			}
			ranks[i++] = r;
		}
		
		int[] numValues = HandRankUtils.getNumericValues(ranks, includes2);
		return isStraight(numValues);
	}

	private boolean isStraight(int[] numValues) {
		Arrays.sort(numValues);

		for (int i = 0; i < 4; i++) {
			if (numValues[i] != numValues[i + 1] - 1) {
				return false;
			}
		}
		return true;
	}

}
