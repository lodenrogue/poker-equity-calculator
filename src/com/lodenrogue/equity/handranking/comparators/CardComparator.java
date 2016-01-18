package com.lodenrogue.equity.handranking.comparators;

import java.util.Comparator;

import com.lodenrogue.equity.card.Card;
import com.lodenrogue.equity.handranking.HandRank;

/**
 * Compares hands. Assumes that the arguments passed to its compare method in
 * fact return the same {@link HandRank} value when evaluated. Does not check for this.
 * 
 * @author Miguel Hernandez
 *
 */
public interface CardComparator extends Comparator<Card[]> {

}
