package com.lodenrogue.equity.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.lodenrogue.equity.Card;
import com.lodenrogue.equity.CardUtils;
import com.lodenrogue.equity.Deck;
import com.lodenrogue.equity.Player;
import com.lodenrogue.equity.Rank;
import com.lodenrogue.equity.Suit;
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
	private TextField p3CardsField;
	@FXML
	private TextField p3EquityField;
	@FXML
	private Button evaluateBtn;
	@FXML
	private Button clearBtn;

	private EquityTask equityTask;
	private boolean isEquityRunning = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void onButtonPressed(ActionEvent e) {
		if (e.getSource().equals(evaluateBtn)) {
			if (isEquityRunning) {
				stopEquity();
			}
			else {
				getEquity();
			}
		}
		else if (e.getSource().equals(clearBtn)) {
			clearAllFields();
		}
	}

	private void clearAllFields() {
		if (isEquityRunning) {
			stopEquity();
		}

		p1CardsField.clear();
		p1EquityField.clear();
		p2CardsField.clear();
		p2EquityField.clear();
		p3CardsField.clear();
		p3EquityField.clear();
	}

	private void stopEquity() {
		equityTask.setContinue(false);
		isEquityRunning = false;
		Platform.runLater(() -> evaluateBtn.setText("Evaluate"));
	}

	private void getEquity() {
		String p1CardsString = p1CardsField.getText();
		String p2CardsString = p2CardsField.getText();
		String p3CardsString = p3CardsField.getText();

		List<Card> p1Hand = null;
		List<Card> p2Hand = null;
		List<Card> p3Hand = null;

		Deck deck = new Deck();

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

		try {
			p3Hand = parseCards(deck, p3CardsString);
		}
		catch (InvalidFormatException e) {
			// TODO Let the user know
		}

		if (p1Hand == null || p2Hand == null || p3Hand == null) {
			return;
		}

		Player p1 = new Player("Player 1");
		Player p2 = new Player("Player 2");
		Player p3 = new Player("Player 3");

		p1.addCard(p1Hand.get(0));
		p1.addCard(p1Hand.get(1));
		p2.addCard(p2Hand.get(0));
		p2.addCard(p2Hand.get(1));
		p3.addCard(p3Hand.get(0));
		p3.addCard(p3Hand.get(1));

		LinkedHashMap<Player, TextField> playersInHand = new LinkedHashMap<>();
		playersInHand.put(p1, p1EquityField);
		playersInHand.put(p2, p2EquityField);
		playersInHand.put(p3, p3EquityField);

		equityTask = new EquityTask(playersInHand);
		new Thread(equityTask).start();
		isEquityRunning = true;

		Platform.runLater(() -> evaluateBtn.setText("Stop"));
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
