package com.lodenrogue.equity.handranking.comparators;

import java.util.HashMap;
import java.util.Map;

import com.lodenrogue.equity.Card;
import com.lodenrogue.equity.Rank;

public class FullHouseComparator implements CardComparator {

	@Override
	public int compare(Card[] hand1, Card[] hand2) {
		// Get trips
		int hand1Trip = findTrip(hand1);
		int hand2Trip = findTrip(hand2);

		// Compare trips
		if (hand1Trip > hand2Trip) {
			return 1;
		}
		else if (hand1Trip < hand2Trip) {
			return -1;
		}

		// Get pairs
		int hand1Pair = findPair(hand1, hand1Trip);
		int hand2Pair = findPair(hand2, hand2Trip);

		// Compare pairs
		if (hand1Pair > hand2Pair) {
			return 1;
		}
		else if (hand1Pair < hand2Pair) {
			return -1;
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

	private int findPair(Card[] hand, int trip) {
		int pair = 0;
		Map<Rank, Integer> rankMap = new HashMap<>();
		for (Card c : hand) {
			if (c.getRank().getValue() != trip) {
				if (rankMap.containsKey(c.getRank())) {
					pair = c.getRank().getValue();
					break;
				}
				else {
					rankMap.put(c.getRank(), 1);
				}
			}
		}
		return pair;
	}

}
