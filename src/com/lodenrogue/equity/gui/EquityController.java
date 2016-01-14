package com.lodenrogue.equity.gui;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.lodenrogue.equity.Card;
import com.lodenrogue.equity.CardUtils;
import com.lodenrogue.equity.Deck;
import com.lodenrogue.equity.Player;
import com.lodenrogue.equity.Rank;
import com.lodenrogue.equity.Suit;
import com.lodenrogue.equity.handranking.HandRankUtils;
import com.sun.media.sound.InvalidFormatException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EquityController implements Initializable {
	@FXML
	private TextField p1CardsField;
	@FXML
	private TextField p1EquityField;
	@FXML
	private TextField p2CardsField;
	@FXML
	private TextField p2EquityField;

	@FXML
	private Button evaluateBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void onButtonPressed(ActionEvent e) {
		if (e.getSource().equals(evaluateBtn)) {
			getEquity();
		}
	}

	private void getEquity() {
		Runnable task = () -> {
			String p1CardsString = p1CardsField.getText();
			String p2CardsString = p2CardsField.getText();

			long iterations = 1;
			long p1Wins = 0;
			long p2Wins = 0;

			while (iterations < 1_000_000) {
				Deck deck = new Deck();
				deck.shuffle();

				List<Card> p1Hand = null;
				List<Card> p2Hand = null;

				try {
					p1Hand = parseCards(deck, p1CardsString);
				}
				catch (InvalidFormatException e) {
					// TODO let the user know
				}

				try {
					p2Hand = parseCards(deck, p2CardsString);
				}
				catch (InvalidFormatException e) {
					// TODO Let the user know
				}

				if (p1Hand == null || p2Hand == null) {
					return;
				}

				Player p1 = new Player("Player 1");
				Player p2 = new Player("Player 2");

				p1.addCard(p1Hand.get(0));
				p1.addCard(p1Hand.get(1));
				p2.addCard(p2Hand.get(0));
				p2.addCard(p2Hand.get(1));

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
					final DecimalFormat df = new DecimalFormat();
					df.setMaximumFractionDigits(2);

					final float p1Equity = ((float) p1Wins / iterations) * 100f;
					final float p2Equity = ((float) p2Wins / iterations) * 100f;

					Platform.runLater(() -> p1EquityField.setText(df.format(p1Equity) + "%"));
					Platform.runLater(() -> p2EquityField.setText(df.format(p2Equity) + "%"));
				}

				iterations++;
			}
		};
		new Thread(task).start();
	}

	private List<Card> parseCards(Deck deck, String cards) throws InvalidFormatException {
		if (cards.equalsIgnoreCase("random")) {
			List<Card> cardList = new ArrayList<>();
			cardList.add(deck.deal());
			cardList.add(deck.deal());
			return cardList;

		}
		else {
			if (cards.length() != 4) {
				throw new InvalidFormatException();
			}

			char[] chars = cards.toCharArray();
			Rank rank1 = CardUtils.getRank(chars[0]);
			Suit suit1 = CardUtils.getSuit(chars[1]);

			Rank rank2 = CardUtils.getRank(chars[2]);
			Suit suit2 = CardUtils.getSuit(chars[3]);

			if (rank1 == null || suit1 == null || rank2 == null || suit2 == null) {
				throw new InvalidFormatException();
			}

			List<Card> cardList = new ArrayList<>();
			Card card1 = deck.getCard(rank1, suit1);
			Card card2 = deck.getCard(rank2, suit2);

			if (card1 == null || card2 == null) {
				throw new InvalidFormatException();
			}

			cardList.add(new Card(rank1, suit1));
			cardList.add(new Card(rank2, suit2));
			return cardList;
		}

	}

}
