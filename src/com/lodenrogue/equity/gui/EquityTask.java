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
import javafx.scene.control.TextField;

public class EquityTask implements Runnable {
	private Map<Player, TextField> playersInHand;
	private boolean continueEval = true;

	public EquityTask(LinkedHashMap<Player, TextField> playersInHand) {
		this.playersInHand = playersInHand;
	}

	public void setContinue(boolean continueEval) {
		this.continueEval = continueEval;
	}

	@Override
	public void run() {
		int iterations = 1;
		int[] playerWins = new int[playersInHand.size()];
		Arrays.fill(playerWins, 0);

		while (iterations < 1_000_000 && continueEval) {
			Deck deck = new Deck();
			deck.shuffle();

			dealHoleCards(deck, playersInHand.keySet());
			List<Card> community = getCommunityCards(deck);

			List<Player> players = new ArrayList<>();
			players.addAll(playersInHand.keySet());

			List<Player> winners = HandRankUtils.getWinners(players, community);
			for (Player p : winners) {
				p.addWin();
			}
			updateEquity(iterations++);
		}
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
			for (Player p : playersInHand.keySet()) {
				totalWins += p.getWins();
			}

			for (Player p : playersInHand.keySet()) {
				final float pEquity = ((float) p.getWins() / totalWins) * 100f;
				Platform.runLater(() -> playersInHand.get(p).setText(df.format(pEquity) + "%"));
			}
		}
	}

	private List<Card> getCommunityCards(Deck deck) {
		List<Card> community = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			community.add(deck.deal());
		}
		return community;
	}

}
