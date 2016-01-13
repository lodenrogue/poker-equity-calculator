package com.lodenrogue.equity;

import java.util.List;

import com.lodenrogue.equity.handranking.HandRank;
import com.lodenrogue.equity.handranking.HandRankUtils;

public class EquityTest {

	public static void main(String[] args) {
		// int arr[] = { 1, 2, 3, 4, 5, 6, 7 };
		// int r = 5;
		// int n = arr.length;
		// printCombination(arr, n, r);

		// testCardRank(CardRank.STRAIGHT);

		// Deck deck = new Deck();
		// deck.shuffle();
		// List<Card> cards = new ArrayList<Card>();
		// for (int i = 0; i < 7; i++) {
		// cards.add(deck.deal());
		// }
		//
		// System.out.println(cards);
		// List<Card> bestHand = HandRankUtils.findBestHand(cards);
		// System.out.print(bestHand + " - ");
		// System.out.println(HandRankUtils.findRank(bestHand.toArray(new
		// Card[bestHand.size()])));

		Card[] hand1 = new Card[5];
		Card[] hand2 = new Card[5];

		hand1[0] = new Card(Rank.THREE, Suit.CLUB);
		hand1[1] = new Card(Rank.FOUR, Suit.CLUB);
		hand1[2] = new Card(Rank.FIVE, Suit.CLUB);
		hand1[3] = new Card(Rank.SIX, Suit.CLUB);
		hand1[4] = new Card(Rank.SEVEN, Suit.CLUB);

		hand2[0] = new Card(Rank.THREE, Suit.CLUB);
		hand2[1] = new Card(Rank.FOUR, Suit.CLUB);
		hand2[2] = new Card(Rank.FIVE, Suit.CLUB);
		hand2[3] = new Card(Rank.SIX, Suit.CLUB);
		hand2[4] = new Card(Rank.SEVEN, Suit.CLUB);

		System.out.println(HandRankUtils.findRank(hand1));
		System.out.println(HandRankUtils.compare(hand1, hand2));

		// testCombinations(cards);

	}

	public static void testCombinations(List<Card> cards) {
		printCombinations(cards, 5);

	}

	static void printCombinations(List<Card> cards, int r) {
		// A temporary array to store all combination one by one
		Card[] data = new Card[r];

		// Print all combination using temporary array 'data[]'
		combinationUtil(cards, data, 0, cards.size() - 1, 0, r);
	}

	static void combinationUtil(List<Card> cards, Card[] data, int start, int end, int index, int r) {
		if (index == r) {
			for (int j = 0; j < r; j++) {
				System.out.print(data[j] + " ");
			}
			System.out.println();
			return;
		}

		// replace index with all possible elements. The condition
		// "end-i+1 >= r-index" makes sure that including one element
		// at index will make a combination with remaining elements
		// at remaining positions
		for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
			data[index] = cards.get(i);
			combinationUtil(cards, data, i + 1, end, index + 1, r);
		}

	}

	private static void testCardRank(HandRank rank) {
		Deck deck = new Deck();
		HandRank cr = null;

		while (cr != rank) {
			cr = deal(deck);
		}
	}

	private static HandRank deal(Deck deck) {
		deck.shuffle();
		Card[] cards = new Card[5];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = deck.deal();
		}

		for (Card c : cards) {
			System.out.print(c.toString() + " ");
		}

		HandRank cr = HandRankUtils.findRank(cards);
		System.out.println("- " + cr);
		return cr;
	}

}
