package com.lodenrogue.equity.handranking;

import com.lodenrogue.equity.Card;

public class StraightFlushStrategy implements RankStrategy {
	private RankStrategy straight;
	private RankStrategy flush;

	public StraightFlushStrategy() {
		straight = new StraightStrategy();
		flush = new FlushStrategy();
	}

	@Override
	public boolean isRank(Card... cards) {
		if (cards.length != 5) {
			return false;
		}
		return straight.isRank(cards) && flush.isRank(cards);
	}

}
