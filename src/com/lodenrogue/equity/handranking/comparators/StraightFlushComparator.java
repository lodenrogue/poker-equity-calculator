package com.lodenrogue.equity.handranking.comparators;

import com.lodenrogue.equity.card.Card;

public class StraightFlushComparator implements CardComparator {
	private CardComparator straightComp;

	public StraightFlushComparator() {
		straightComp = new StraightComparator();
	}

	@Override
	public int compare(Card[] o1, Card[] o2) {
		return straightComp.compare(o1, o2);
	}

}
