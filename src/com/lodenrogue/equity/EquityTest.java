package com.lodenrogue.equity;

import java.util.ArrayList;
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

		// testBestHand();
		// testComparator();

		// testCombinations(cards);

		// testEquity();

	}

	private static void testEquity() {
		long iterations = 1;
		long p1Wins = 0;
		long p2Wins = 0;

		while (iterations < 2_000_000) {
			Player p1 = new Player("p1");
			Player p2 = new Player("p2");

			Deck deck = new Deck();
			deck.shuffle();

			p1.addCard(deck.getCard(Rank.ACE, Suit.CLUB));
			p1.addCard(deck.getCard(Rank.ACE, Suit.SPADE));
			p2.addCard(deck.getCard(Rank.FIVE, Suit.DIAMOND));
			p2.addCard(deck.getCard(Rank.TWO, Suit.HEART));

			List<Card> community = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				community.add(deck.deal());
			}

			List<Card> p1Cards = new ArrayList<>();
			p1Cards.addAll(p1.getHand());
			p1Cards.addAll(community);

			List<Card> p2Cards = new ArrayList<>();
			p2Cards.addAll(p2.getHand());
			p2Cards.addAll(community);

			List<Card> p1BestHand = HandRankUtils.findBestHand(p1Cards);
			List<Card> p2BestHand = HandRankUtils.findBestHand(p2Cards);

			int result = HandRankUtils.compare(p1BestHand, p2BestHand);
			if (result == 1) {
				p1Wins++;
			}
			else if (result == -1) {
				p2Wins++;
			}
			else {
				p1Wins++;
				p2Wins++;
			}

			if (iterations % 10_000 == 0) {
				System.out.println("Player 1 equity: " + ((float) p1Wins / iterations) * 100f);
				System.out.println("Player 2 equity: " + ((float) p2Wins / iterations) * 100f);
				System.out.println();
			}

			iterations++;
		}
	}

	private static void testBestHand() {
		Deck deck = new Deck();
		HandRank rank = HandRank.HIGH_CARD;
		while (!rank.equals(HandRank.STRAIGHT_FLUSH)) {
			deck.shuffle();
			List<Card> cards = new ArrayList<Card>();
			for (int i = 0; i < 7; i++) {
				cards.add(deck.deal());
			}

			System.out.println(cards);
			List<Card> bestHand = HandRankUtils.findBestHand(cards);
			System.out.print(bestHand + " - ");
			rank = HandRankUtils.findRank(bestHand.toArray(new Card[bestHand.size()]));
			System.out.println(rank);
		}
	}

	private static void testComparator() {
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
