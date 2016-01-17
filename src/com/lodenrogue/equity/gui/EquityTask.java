package com.lodenrogue.equity.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lodenrogue.equity.Card;
import com.lodenrogue.equity.Deck;
import com.lodenrogue.equity.Player;
import com.lodenrogue.equity.handranking.HandRankUtils;

import javafx.application.Platform;

public class EquityTask implements Runnable {
	private Map<Player, RowController> playerRows;
	private List<Card> board;
	private boolean continueEval = true;

	public EquityTask(LinkedHashMap<Player, RowController> playerRows, List<Card> board) {
		this.playerRows = playerRows;
		this.board = board;
		// TODO use board when determining community cards
	}

	public void setContinue(boolean continueEval) {
		this.continueEval = continueEval;
	}

	@Override
	public void run() {
		int iterations = 1;
		int[] playerWins = new int[playerRows.size()];
		Arrays.fill(playerWins, 0);

		while (iterations < 1_000_000 && continueEval) {
			Deck deck = new Deck();
			deck.shuffle();

			removeFromDeck(deck, board);
			dealHoleCards(deck, playerRows.keySet());
			List<Card> community = getCommunityCards(deck);

			List<Player> players = new ArrayList<>();
			players.addAll(playerRows.keySet());

			List<Player> winners = HandRankUtils.getWinners(players, community);
			for (Player p : winners) {
				p.addWin();
			}
			updateEquity(iterations++);
		}

		EquityController controller = EquityController.getInstance();
		controller.stopEquity();
	}

	private void removeFromDeck(Deck deck, List<Card> cards) {
		cards.forEach(card -> deck.getCard(card.getRank(), card.getSuit()));
	}

	private void dealHoleCards(Deck deck, Set<Player> players) {
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

	private void updateEquity(int iterations) {
		if (iterations % 10_000 == 0) {
			final DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);

			int totalWins = 0;
			for (Player p : playerRows.keySet()) {
				totalWins += p.getWins();
			}

			for (Player p : playerRows.keySet()) {
				final float pEquity = ((float) p.getWins() / totalWins) * 100f;
				Platform.runLater(() -> playerRows.get(p).setEquity(df.format(pEquity) + "%"));
			}
		}
	}

	private List<Card> getCommunityCards(Deck deck) {
		List<Card> community = new ArrayList<>();
		community.addAll(board);
		while (community.size() < 5) {
			community.add(deck.deal());
		}
		return community;
	}

}
