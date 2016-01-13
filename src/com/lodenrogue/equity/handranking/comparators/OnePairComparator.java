package com.lodenrogue.equity.handranking.comparators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.lodenrogue.equity.Card;
import com.lodenrogue.equity.Rank;

public class OnePairComparator implements CardComparator {

	@Override
	public int compare(Card[] hand1, Card[] hand2) {
		// Find the pair
		int hand1Pair = findPair(hand1);
		int hand2Pair = findPair(hand2);

		// Compare the pair
		if (hand1Pair > hand2Pair) {
			return 1;
		}
		else if (hand1Pair < hand2Pair) {
			return -1;
		}

		//Find the kickers
		int[] hand1Kickers = new int[3];
		int[] hand2Kickers = new int[3];
		
		int hand1Index = 0;
		int hand2Index = 0;

		for (int i = 0; i < 5; i++) {
			int hand1Value = hand1[i].getRank().getValue();
			if (hand1Value != hand1Pair) {
				hand1Kickers[hand1Index++] = hand1Value;
			}

			int hand2Value = hand2[i].getRank().getValue();
			if (hand2Value != hand2Pair) {
				hand2Kickers[hand2Index++] = hand2Value;
			}
		}
		Arrays.sort(hand1Kickers);
		Arrays.sort(hand2Kickers);

		//Compare the kickers
		for (int i = 2; i >= 0; i--) {
			if (hand1Kickers[i] > hand2Kickers[i]) {
				return 1;
			}
			else if (hand1Kickers[i] < hand2Kickers[i]) {
				return -1;
			}
		}

		return 0;
	}

	private int findPair(Card[] hand) {
		int pair = 0;
		Map<Rank, Integer> rankMap = new HashMap<>();
		for (Card c : hand) {
			if (rankMap.containsKey(c.getRank())) {
				pair = c.getRank().getValue();
				break;
			}
			else {
				rankMap.put(c.getRank(), 1);
			}
		}
		return pair;
	}

}
