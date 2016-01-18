package com.lodenrogue.equity.handranking;

import com.lodenrogue.equity.card.Card;

public interface RankStrategy {

	public boolean isRank(Card... cards);

}
