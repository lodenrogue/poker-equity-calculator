package com.lodenrogue.equity.handranking.comparators;

import java.util.Arrays;

import com.lodenrogue.equity.card.Card;
import com.lodenrogue.equity.card.Rank;
import com.lodenrogue.equity.handranking.HandRankUtils;

public class StraightComparator implements CardComparator {

	@Override
	public int compare(Card[] hand1, Card[] hand2) {
		Rank[] hand1Ranks = new Rank[5];
		Rank[] hand2Ranks = new Rank[5];

		boolean hand1Includes2 = false;
		boolean hand2Includes2 = false;
		for (int i = 0; i < 5; i++) {
			if (hand1[i].getRank().getValue() == 2) {
				hand1Includes2 = true;
			}
			if (hand2[i].getRank().getValue() == 2) {
				hand2Includes2 = true;
			}
			hand1Ranks[i] = hand1[i].getRank();
			hand2Ranks[i] = hand2[i].getRank();
		}
		int[] hand1Values = HandRankUtils.getNumericValues(hand1Ranks, hand1Includes2);
		int[] hand2Values = HandRankUtils.getNumericValues(hand2Ranks, hand2Includes2);

		Arrays.sort(hand1Values);
		Arrays.sort(hand2Values);

		if (hand1Values[4] > hand2Values[4]) {
			return 1;
		}
		else if (hand1Values[4] < hand2Values[4]) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
