package com.lodenrogue.equity.handranking.comparators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.lodenrogue.equity.card.Card;
import com.lodenrogue.equity.card.Rank;

public class TwoPairComparator implements CardComparator {

	@Override
	public int compare(Card[] hand1, Card[] hand2) {
		// Find the two pairs
		int[] hand1Pairs = findPairs(hand1);
		int[] hand2Pairs = findPairs(hand2);

		Arrays.sort(hand1Pairs);
		Arrays.sort(hand2Pairs);
		
		//Compare the two pairs
		for(int i = 1; i >= 0; i--){
			if(hand1Pairs[i] > hand2Pairs[i]){
				return 1;
			}
			else if(hand1Pairs[i] < hand2Pairs[i]){
				return -1;
			}
		}

		// Find the kickers
		int hand1Kicker = 0;
		int hand2Kicker = 0;

		for (int i = 0; i < 5; i++) {
			int hand1Value = hand1[i].getRank().getValue();
			if (hand1Value != hand1Pairs[0] && hand1Value != hand1Pairs[1]) {
				hand1Kicker = hand1Value;
			}

			int hand2Value = hand2[i].getRank().getValue();
			if (hand2Value != hand2Pairs[0] && hand2Value != hand2Pairs[1]) {
				hand2Kicker = hand2Value;
			}
		}

		// Compare the kickers
		if (hand1Kicker > hand2Kicker) {
			return 1;
		}
		else if (hand1Kicker < hand2Kicker) {
			return -1;
		}
		else {
			return 0;
		}
	}

	private int[] findPairs(Card[] hand) {
		int[] pairs = new int[2];
		int index = 0;

		Map<Rank, Integer> rankMap = new HashMap<>();
		for (Card c : hand) {
			if (rankMap.containsKey(c.getRank())) {
				pairs[index++] = c.getRank().getValue();
			}
			else {
				rankMap.put(c.getRank(), 1);
			}
		}
		return pairs;
	}

}
