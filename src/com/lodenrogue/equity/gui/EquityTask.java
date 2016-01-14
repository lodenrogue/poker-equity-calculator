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
		long iterations = 1;
		long[] playerWins = new long[playersInHand.size()];
		Arrays.fill(playerWins, 0);

		while (iterations < 1_000_000 && continueEval) {
			Deck deck = new Deck();
			deck.shuffle();

			// Set random cards for players or remove set cards
			for (Player player : playersInHand.keySet()) {

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

			List<Card> community = getCommunityCards(deck);
			List<List<Card>> comboCards = getPlayerComboCards(community);
			List<List<Card>> bestHands = getBestHands(comboCards);
			List<Player> winners = getWinningPlayers(bestHands);

			for (Player p : winners) {
				p.addWin();
			}

			if (iterations % 10_000 == 0) {
				final DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);

				long totalWins = 0;
				for (Player p : playersInHand.keySet()) {
					totalWins += p.getWins();
				}
				for (Player p : playersInHand.keySet()) {
					final float pEquity = ((float) p.getWins() / totalWins) * 100f;
					Platform.runLater(() -> playersInHand.get(p).setText(df.format(pEquity) + "%"));
				}
			}
			iterations++;
		}
	}

	private List<Player> getWinningPlayers(List<List<Card>> bestHands) {
		Set<Player> playerSet = playersInHand.keySet();
		List<Player> players = new ArrayList<>();
		players.addAll(playerSet);

		for (int i = 0; i < bestHands.size() - 1; i++) {
			int result = HandRankUtils.compare(bestHands.get(i), bestHands.get(i + 1));
			if (result == 1) {
				bestHands.remove(i + 1);
				players.remove(i + 1);
				i = -1;
			}
			else if (result == -1) {
				for (int j = 0; j < i + 1; j++) {
					bestHands.remove(j);
					players.remove(j);
				}
				i = -1;
			}
		}
		return players;
	}

	private List<List<Card>> getBestHands(List<List<Card>> playerCards) {
		List<List<Card>> bestHands = new ArrayList<>();
		for (List<Card> cList : playerCards) {
			List<Card> bestHand = HandRankUtils.findBestHand(cList);
			bestHands.add(bestHand);
		}
		return bestHands;
	}

	private List<List<Card>> getPlayerComboCards(List<Card> community) {
		List<List<Card>> playerCards = new ArrayList<>();
		for (Player p : playersInHand.keySet()) {
			List<Card> pCards = new ArrayList<>();
			pCards.addAll(p.getHand());
			pCards.addAll(community);
			playerCards.add(pCards);
		}
		return playerCards;
	}

	private List<Card> getCommunityCards(Deck deck) {
		List<Card> community = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			community.add(deck.deal());
		}
		return community;
	}

}
