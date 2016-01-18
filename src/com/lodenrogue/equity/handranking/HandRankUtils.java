package com.lodenrogue.equity.handranking;

import java.util.ArrayList;
import java.util.List;

import com.lodenrogue.equity.Player;
import com.lodenrogue.equity.card.Card;
import com.lodenrogue.equity.card.Rank;
import com.lodenrogue.equity.handranking.comparators.CardComparator;
import com.lodenrogue.equity.handranking.comparators.FlushComparator;
import com.lodenrogue.equity.handranking.comparators.FourOfAKindComparator;
import com.lodenrogue.equity.handranking.comparators.FullHouseComparator;
import com.lodenrogue.equity.handranking.comparators.HighCardComparator;
import com.lodenrogue.equity.handranking.comparators.OnePairComparator;
import com.lodenrogue.equity.handranking.comparators.StraightComparator;
import com.lodenrogue.equity.handranking.comparators.StraightFlushComparator;
import com.lodenrogue.equity.handranking.comparators.ThreeOfAKindComparator;
import com.lodenrogue.equity.handranking.comparators.TwoPairComparator;

public class HandRankUtils {

	private HandRankUtils() {

	}

	public static List<Player> getWinners(List<Player> players, List<Card> communityCards) {
		List<List<Card>> comboCards = combinePlayerCards(players, communityCards);
		List<List<Card>> bestHands = getBestHands(comboCards);
		List<Player> winners = findWinningPlayers(players, bestHands);
		return winners;
	}

	private static List<Player> findWinningPlayers(List<Player> players, List<List<Card>> bestHands) {
		List<Player> winners = new ArrayList<>();
		winners.addAll(players);

		for (int i = 0; i < bestHands.size() - 1; i++) {
			int result = HandRankUtils.compare(bestHands.get(i), bestHands.get(i + 1));

			// i is the winner
			if (result == 1) {
				// Remove the losing player/hand
				bestHands.remove(i + 1);
				winners.remove(i + 1);
				i = -1;
			}
			// i + 1 is the winner
			else if (result == -1) {
				// Make a list of the hands and players to
				// remove
				List<List<Card>> removeBestHands = new ArrayList<>();
				List<Player> removePlayers = new ArrayList<>();

				for (int j = 0; j < i + 1; j++) {
					removeBestHands.add(bestHands.get(j));
					removePlayers.add(winners.get(j));
				}

				// Remove hands and players from lists
				for (int j = 0; j < removeBestHands.size(); j++) {
					bestHands.remove(removeBestHands.get(j));
					winners.remove(removePlayers.get(j));
				}
				i = -1;
			}
		}
		return winners;
	}

	private static List<List<Card>> getBestHands(List<List<Card>> playerCards) {
		List<List<Card>> bestHands = new ArrayList<>();
		for (List<Card> cards : playerCards) {
			List<Card> bestHand = HandRankUtils.findBestHand(cards);
			bestHands.add(bestHand);
		}
		return bestHands;
	}

	private static List<List<Card>> combinePlayerCards(List<Player> playersInHand, List<Card> community) {
		List<List<Card>> playerCards = new ArrayList<>();
		for (Player p : playersInHand) {
			List<Card> cards = new ArrayList<>();
			cards.addAll(p.getHand());
			cards.addAll(community);
			playerCards.add(cards);
		}
		return playerCards;
	}

	public static List<Card> findBestHand(List<Card> cards) {
		if (cards.size() != 7) {
			return new ArrayList<>();
		}

		List<Card[]> combinations = new ArrayList<>();
		combinations = getCombinations(cards, new Card[5], 0, cards.size() - 1, 0, 5, combinations);
		List<Card> bestHand = new ArrayList<>();

		for (Card[] combo : combinations) {
			if (!bestHand.isEmpty()) {
				int result = compare(bestHand.toArray(new Card[combo.length]), combo);
				if (result == -1) {
					bestHand = new ArrayList<>();
					for (Card c : combo) {
						bestHand.add(c);
					}
				}
			}
			else {
				bestHand = new ArrayList<>();
				for (Card c : combo) {
					bestHand.add(c);
				}
			}
		}
		return bestHand;

	}

	public static int compare(List<Card> hand1, List<Card> hand2) {
		return compare(hand1.toArray(new Card[hand1.size()]), hand2.toArray(new Card[hand2.size()]));
	}

	/**
	 * Compared two hands of five cards. Returns 1 if the first hand is of
	 * greater value, -1 if the second hand is of greater value, or 0 if
	 * both hands are of equal value
	 * 
	 * @param hand1
	 * @param hand2
	 * @return 1 if first hand is of greater value, -1 if second hand is of
	 *         greater value, 0 if both hands are of equal value
	 */
	public static int compare(Card[] hand1, Card[] hand2) {
		HandRank hand1Rank = findRank(hand1);
		HandRank hand2Rank = findRank(hand2);

		if (hand1Rank.getValue() > hand2Rank.getValue()) {
			return 1;
		}
		else if (hand1Rank.getValue() < hand2Rank.getValue()) {
			return -1;
		}

		CardComparator comparator = new HighCardComparator();

		if (hand1Rank.equals(HandRank.STRAIGHT_FLUSH)) {
			comparator = new StraightFlushComparator();
		}
		else if (hand1Rank.equals(HandRank.FOUR_OF_A_KIND)) {
			comparator = new FourOfAKindComparator();
		}
		else if (hand1Rank.equals(HandRank.FULL_HOUSE)) {
			comparator = new FullHouseComparator();
		}
		else if (hand1Rank.equals(HandRank.FLUSH)) {
			comparator = new FlushComparator();
		}
		else if (hand1Rank.equals(HandRank.STRAIGHT)) {
			comparator = new StraightComparator();
		}
		else if (hand1Rank.equals(HandRank.THREE_OF_A_KIND)) {
			comparator = new ThreeOfAKindComparator();
		}
		else if (hand1Rank.equals(HandRank.TWO_PAIR)) {
			comparator = new TwoPairComparator();
		}
		else if (hand1Rank.equals(HandRank.ONE_PAIR)) {
			comparator = new OnePairComparator();
		}

		return comparator.compare(hand1, hand2);
	}

	private static List<Card[]> getCombinations(List<Card> cards, Card[] data, int start, int end, int index, int r, List<Card[]> destination) {
		if (index == r) {
			Card[] combo = new Card[r];
			for (int j = 0; j < r; j++) {
				combo[j] = data[j];
			}
			destination.add(combo);
			return destination;
		}

		// replace index with all possible elements. The condition
		// "end-i+1 >= r-index" makes sure that including one element
		// at index will make a combination with remaining elements
		// at remaining positions
		for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
			data[index] = cards.get(i);
			destination = getCombinations(cards, data, i + 1, end, index + 1, r, destination);
		}

		return destination;
	}

	public static HandRank findRank(Card... cards) {
		RankStrategy rankStrategy = new StraightFlushStrategy();
		if (rankStrategy.isRank(cards)) {
			return HandRank.STRAIGHT_FLUSH;
		}

		rankStrategy = new FourOfAKindStrategy();
		if (rankStrategy.isRank(cards)) {
			return HandRank.FOUR_OF_A_KIND;
		}

		rankStrategy = new FullHouseStrategy();
		if (rankStrategy.isRank(cards)) {
			return HandRank.FULL_HOUSE;
		}

		rankStrategy = new FlushStrategy();
		if (rankStrategy.isRank(cards)) {
			return HandRank.FLUSH;
		}

		rankStrategy = new StraightStrategy();
		if (rankStrategy.isRank(cards)) {
			return HandRank.STRAIGHT;
		}

		rankStrategy = new ThreeOfAKindStrategy();
		if (rankStrategy.isRank(cards)) {
			return HandRank.THREE_OF_A_KIND;
		}

		rankStrategy = new TwoPairStrategy();
		if (rankStrategy.isRank(cards)) {
			return HandRank.TWO_PAIR;
		}

		rankStrategy = new OnePairStrategy();
		if (rankStrategy.isRank(cards)) {
			return HandRank.ONE_PAIR;
		}

		return HandRank.HIGH_CARD;

	}

	public static int[] getNumericValues(Rank[] ranks, boolean includes2) {
		int[] numValues = new int[ranks.length];
		int i = 0;

		for (Rank r : ranks) {
			if (r.equals(Rank.ACE)) {
				if (includes2) {
					numValues[i] = 1;
				}
				else {
					numValues[i] = r.getValue();
				}
			}
			else {
				numValues[i] = r.getValue();
			}
			i++;
		}
		return numValues;
	}

}
