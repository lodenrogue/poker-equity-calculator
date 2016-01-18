package com.lodenrogue.equity.handranking.comparators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.lodenrogue.equity.card.Card;
import com.lodenrogue.equity.card.Rank;

public class ThreeOfAKindComparator implements CardComparator {

	@Override
	public int compare(Card[] hand1, Card[] hand2) {
		// Find the trip
		int hand1Trip = findTrip(hand1);
		int hand2Trip = findTrip(hand2);

		// Compare the trip
		if (hand1Trip > hand2Trip) {
			return 1;
		}
		else if (hand1Trip < hand2Trip) {
			return -1;
		}

		// Find the kickers
		int[] hand1Kickers = new int[2];
		int[] hand2Kickers = new int[2];

		int hand1Index = 0;
		;
		int hand2Index = 0;
		;

		for (int i = 0; i < 5; i++) {
			int hand1Value = hand1[i].getRank().getValue();
			if (hand1Value != hand1Trip) {
				hand1Kickers[hand1Index++] = hand1Value;
			}

			int hand2Value = hand2[i].getRank().getValue();
			if (hand2Value != hand2Trip) {
				hand2Kickers[hand2Index++] = hand2Value;
			}
		}

		Arrays.sort(hand1Kickers);
		Arrays.sort(hand2Kickers);

		// Compare the kickers
		for (int i = 1; i >= 0; i--) {
			if (hand1Kickers[i] > hand2Kickers[i]) {
				return 1;
			}
			else if (hand1Kickers[i] < hand2Kickers[i]) {
				return -1;
			}
		}

		return 0;
	}

	public int findTrip(Card[] hand) {
		int trip = 0;
		Map<Rank, Integer> rankMap = new HashMap<>();
		for (Card c : hand) {
			if (rankMap.containsKey(c.getRank())) {
				if (rankMap.get(c.getRank()) == 2) {
					trip = c.getRank().getValue();
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
		return trip;
	}

}
