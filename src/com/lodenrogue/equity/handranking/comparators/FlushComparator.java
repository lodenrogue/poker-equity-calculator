package com.lodenrogue.equity.handranking.comparators;

import java.util.Arrays;

import com.lodenrogue.equity.card.Card;

public class FlushComparator implements CardComparator {

	@Override
	public int compare(Card[] hand1, Card[] hand2) {
		int[] hand1Values = new int[5];
		int[] hand2Values = new int[5];

		// Get values
		for (int i = 0; i < 5; i++) {
			hand1Values[i] = hand1[i].getRank().getValue();
			hand2Values[i] = hand2[i].getRank().getValue();
		}

		Arrays.sort(hand1Values);
		Arrays.sort(hand2Values);

		// Compare values
		for (int i = 4; i >= 0; i--) {
			if (hand1Values[i] > hand2Values[i]) {
				return 1;
			}
			else if (hand1Values[i] < hand2Values[i]) {
				return -1;
			}
		}
		return 0;
	}

}
