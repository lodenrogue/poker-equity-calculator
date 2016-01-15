package com.lodenrogue.equity.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	private Button p1RandomBtn;
	@FXML
	private TextField p1CardsField;
	@FXML
	private TextField p1EquityField;
	@FXML
	private Button p2RandomBtn;
	@FXML
	private TextField p2CardsField;
	@FXML
	private TextField p2EquityField;
	@FXML
	private Button p3RandomBtn;
	@FXML
	private TextField p3CardsField;
	@FXML
	private TextField p3EquityField;
	@FXML
	private Button p4RandomBtn;
	@FXML
	private TextField p4CardsField;
	@FXML
	private TextField p4EquityField;
	@FXML
	private Button p5RandomBtn;
	@FXML
	private TextField p5CardsField;
	@FXML
	private TextField p5EquityField;
	@FXML
	private Button p6RandomBtn;
	@FXML
	private TextField p6CardsField;
	@FXML
	private TextField p6EquityField;
	@FXML
	private Button p7RandomBtn;
	@FXML
	private TextField p7CardsField;
	@FXML
	private TextField p7EquityField;
	@FXML
	private Button p8RandomBtn;
	@FXML
	private TextField p8CardsField;
	@FXML
	private TextField p8EquityField;
	@FXML
	private Button p9RandomBtn;
	@FXML
	private TextField p9CardsField;
	@FXML
	private TextField p9EquityField;
	@FXML
	private Button p10RandomBtn;
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
	private Map<Button, TextField> randomCardFieldMap;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> randomCardFieldMap = createRandomCardMap());
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

	@FXML
	public void onRandomButtonPressed(ActionEvent e) {
		TextField field = randomCardFieldMap.get(((Button) e.getSource()));
		field.setText("random");
	}

	private void getEquity() {
		Deck deck = new Deck();
		deck.shuffle();
	
		LinkedHashMap<Player, TextField> playersInHand = new LinkedHashMap<>();
		Map<TextField, TextField> cardEquityFieldMap = createCardEquityMap();
	
		for (TextField cardField : cardEquityFieldMap.keySet()) {
			try {
				List<Card> hand = parseCards(deck, cardField.getText());
	
				Player player = new Player("Player " + UUID.randomUUID());
				if (cardField.getText().equalsIgnoreCase("random")) {
					player.setHasRandom(true);
				}
	
				player.addCard(hand.get(0));
				player.addCard(hand.get(1));
				playersInHand.put(player, cardEquityFieldMap.get(cardField));
			}
			catch (InvalidFormatException e) {
				// TODO
				return;
			}
		}
	
		equityTask = new EquityTask(playersInHand);
		new Thread(equityTask).start();
		isEquityRunning = true;
	
		Platform.runLater(() -> evaluateBtn.setText("Stop"));
	}

	private void stopEquity() {
		equityTask.setContinue(false);
		isEquityRunning = false;
		Platform.runLater(() -> evaluateBtn.setText("Evaluate"));
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

	private void clearAllFields() {
		p1CardsField.clear();
		p1EquityField.clear();
		p2CardsField.clear();
		p2EquityField.clear();
		p3CardsField.clear();
		p3EquityField.clear();
		p4CardsField.clear();
		p4EquityField.clear();
		p5CardsField.clear();
		p5EquityField.clear();
		p6CardsField.clear();
		p6EquityField.clear();
		p7CardsField.clear();
		p7EquityField.clear();
		p8CardsField.clear();
		p8EquityField.clear();
		p9CardsField.clear();
		p9EquityField.clear();
		p10CardsField.clear();
		p10EquityField.clear();
	}

	private Map<Button, TextField> createRandomCardMap() {
		Map<Button, TextField> randomCardMap = new HashMap<>();
		randomCardMap.put(p1RandomBtn, p1CardsField);
		randomCardMap.put(p2RandomBtn, p2CardsField);
		randomCardMap.put(p3RandomBtn, p3CardsField);
		randomCardMap.put(p4RandomBtn, p4CardsField);
		randomCardMap.put(p5RandomBtn, p5CardsField);
		randomCardMap.put(p6RandomBtn, p6CardsField);
		randomCardMap.put(p7RandomBtn, p7CardsField);
		randomCardMap.put(p8RandomBtn, p8CardsField);
		randomCardMap.put(p9RandomBtn, p9CardsField);
		randomCardMap.put(p10RandomBtn, p10CardsField);

		return randomCardMap;
	}

	private LinkedHashMap<TextField, TextField> createCardEquityMap() {
		LinkedHashMap<TextField, TextField> cardEquityMap = new LinkedHashMap<>();

		if (p1CardsField.getText().length() > 0) {
			cardEquityMap.put(p1CardsField, p1EquityField);
		}
		if (p2CardsField.getText().length() > 0) {
			cardEquityMap.put(p2CardsField, p2EquityField);
		}
		if (p3CardsField.getText().length() > 0) {
			cardEquityMap.put(p3CardsField, p3EquityField);
		}
		if (p4CardsField.getText().length() > 0) {
			cardEquityMap.put(p4CardsField, p4EquityField);
		}
		if (p5CardsField.getText().length() > 0) {
			cardEquityMap.put(p5CardsField, p5EquityField);
		}
		if (p6CardsField.getText().length() > 0) {
			cardEquityMap.put(p6CardsField, p6EquityField);
		}
		if (p7CardsField.getText().length() > 0) {
			cardEquityMap.put(p7CardsField, p7EquityField);
		}
		if (p8CardsField.getText().length() > 0) {
			cardEquityMap.put(p8CardsField, p8EquityField);
		}
		if (p9CardsField.getText().length() > 0) {
			cardEquityMap.put(p9CardsField, p9EquityField);
		}
		if (p10CardsField.getText().length() > 0) {
			cardEquityMap.put(p10CardsField, p10EquityField);
		}
		return cardEquityMap;
	}
}
