package com.lodenrogue.equity.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

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
	private TextField p4CardsField;
	@FXML
	private TextField p4EquityField;
	@FXML
	private TextField p5CardsField;
	@FXML
	private TextField p5EquityField;
	@FXML
	private TextField p6CardsField;
	@FXML
	private TextField p6EquityField;
	@FXML
	private TextField p7CardsField;
	@FXML
	private TextField p7EquityField;
	@FXML
	private TextField p8CardsField;
	@FXML
	private TextField p8EquityField;
	@FXML
	private TextField p9CardsField;
	@FXML
	private TextField p9EquityField;
	@FXML
	private TextField p10CardsField;
	@FXML
	private TextField p10EquityField;
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
			if (isEquityRunning) {
				stopEquity();
			}
			clearAllFields();
		}
	}

	private void clearAllFields() {
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
		Deck deck = new Deck();
		deck.shuffle();

		LinkedHashMap<Player, TextField> playersInHand = new LinkedHashMap<>();
		LinkedHashMap<TextField, TextField> equityFieldMap = getEquityFieldMap();

		for (TextField cardField : equityFieldMap.keySet()) {
			try {
				List<Card> hand = parseCards(deck, cardField.getText());

				Player player = new Player("Player " + UUID.randomUUID());
				if (cardField.getText().equalsIgnoreCase("random")) {
					player.setHasRandom(true);
				}

				player.addCard(hand.get(0));
				player.addCard(hand.get(1));
				playersInHand.put(player, equityFieldMap.get(cardField));
			}
			catch (InvalidFormatException e) {
				// TODO let the user know
				System.out.println("invalid format temp()");
				return;
			}
		}

		equityTask = new EquityTask(playersInHand);
		new Thread(equityTask).start();
		isEquityRunning = true;

		Platform.runLater(() -> evaluateBtn.setText("Stop"));
	}

	private LinkedHashMap<TextField, TextField> getEquityFieldMap() {
		LinkedHashMap<TextField, TextField> equityFieldMap = new LinkedHashMap<>();

		if (p1CardsField.getText().length() > 0) {
			equityFieldMap.put(p1CardsField, p1EquityField);
		}
		if (p2CardsField.getText().length() > 0) {
			equityFieldMap.put(p2CardsField, p2EquityField);
		}
		if (p3CardsField.getText().length() > 0) {
			equityFieldMap.put(p3CardsField, p3EquityField);
		}
		if (p4CardsField.getText().length() > 0) {
			equityFieldMap.put(p4CardsField, p4EquityField);
		}
		if (p5CardsField.getText().length() > 0) {
			equityFieldMap.put(p5CardsField, p5EquityField);
		}
		if (p6CardsField.getText().length() > 0) {
			equityFieldMap.put(p6CardsField, p6EquityField);
		}
		if (p7CardsField.getText().length() > 0) {
			equityFieldMap.put(p7CardsField, p7EquityField);
		}
		if (p8CardsField.getText().length() > 0) {
			equityFieldMap.put(p8CardsField, p8EquityField);
		}
		if (p9CardsField.getText().length() > 0) {
			equityFieldMap.put(p9CardsField, p9EquityField);
		}
		if (p10CardsField.getText().length() > 0) {
			equityFieldMap.put(p10CardsField, p10EquityField);
		}
		return equityFieldMap;
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
