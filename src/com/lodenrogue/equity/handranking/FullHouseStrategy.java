package com.lodenrogue.equity.handranking;

import java.util.Arrays;

import com.lodenrogue.equity.Card;

public class FullHouseStrategy implements RankStrategy {

	@Override
	public boolean isRank(Card... cards) {
		if (cards.length != 5) {
			return false;
		}

		int[] rankValues = new int[cards.length];
		int i = 0;

		for (Card c : cards) {
			rankValues[i] = c.getRank().getValue();
			i++;
		}

		Arrays.sort(rankValues);

		// Find a three of a kind
		int threeValue = 0;
		for (int j = 0; j < 3; j++) {
			if (rankValues[j] == rankValues[j + 1]) {
				if (rankValues[j] == rankValues[j + 2]) {
					threeValue = rankValues[j];
					break;
				}
			}
		}

		// If a three of a kind has been found, find a pair
		if (threeValue != 0) {
			for (int j = 0; j < 4; j++) {
				if (rankValues[j] != threeValue) {
					if (rankValues[j] == rankValues[j + 1]) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
