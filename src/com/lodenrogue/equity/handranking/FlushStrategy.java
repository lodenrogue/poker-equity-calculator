package com.lodenrogue.equity.handranking;

import com.lodenrogue.equity.Card;
import com.lodenrogue.equity.Suit;

public class FlushStrategy implements RankStrategy {

	@Override
	public boolean isRank(Card... cards) {
		if (cards.length != 5) {
			return false;
		}

		Suit suit = cards[0].getSuit();
		for (Card c : cards) {
			if (!c.getSuit().equals(suit)) {
				return false;
			}
		}
		return true;
	}
}
