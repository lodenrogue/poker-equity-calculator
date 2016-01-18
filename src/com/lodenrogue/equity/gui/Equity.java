package com.lodenrogue.equity.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lodenrogue.equity.Card;
import com.lodenrogue.equity.Deck;
import com.lodenrogue.equity.Player;
import com.lodenrogue.equity.handranking.HandRankUtils;

public class Equity {

	/**
	 * Takes in a list of players with cards set or hasRandom flag set and a
	 * list of cards representing the board. Calculates the equity after
	 * assigning any random cards to players and/or the board and returns a
	 * map of players and equity values as floats.
	 * 
	 * @param players
	 * @param board
	 * @return
	 */
	public Map<Player, Float> getEquity(List<Player> players, List<Card> board) {
		Deck deck = new Deck();
		deck.shuffle();

		removeFromDeck(deck, board);
		dealHoleCards(deck, players);
		List<Card> community = getCommunityCards(deck, board);

		List<Player> playersInGame = new ArrayList<>();
		playersInGame.addAll(players);

		List<Player> winners = HandRankUtils.getWinners(playersInGame, community);
		for (Player p : winners) {
			p.addWin();
		}

		return mapResults(players);
	}

	private Map<Player, Float> mapResults(List<Player> players) {
		Map<Player, Float> results = new HashMap<>();

		long totalWins = 0;
		for (Player p : players) {
			totalWins += p.getWins();
		}

		for (Player p : players) {
			float equity = ((float) p.getWins() / totalWins) * 100f;
			results.put(p, equity);
		}
		return results;
	}

	private void removeFromDeck(Deck deck, List<Card> cards) {
		cards.forEach(card -> deck.getCard(card.getRank(), card.getSuit()));
	}

	private void dealHoleCards(Deck deck, List<Player> players) {
		for (Player player : players) {
			if (player.hasRandom()) {
				player.getHand().clear();
				player.addCard(deck.deal());
				player.addCard(deck.deal());

			}
			else {
				Card card1 = player.getHand().get(0);
				Card card2 = player.getHand().get(1);
				deck.getCard(card1.getRank(), card1.getSuit());
				deck.getCard(card2.getRank(), card2.getSuit());
			}
		}
	}

	private List<Card> getCommunityCards(Deck deck, List<Card> board) {
		List<Card> community = new ArrayList<>();
		community.addAll(board);

		while (community.size() < 5) {
			community.add(deck.deal());
		}
		return community;
	}

}
