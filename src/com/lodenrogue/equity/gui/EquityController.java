package com.lodenrogue.equity.gui;

import java.io.IOException;
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

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EquityController implements Initializable {
	@FXML
	private VBox vBox;
	@FXML
	private TextField boardField;
	@FXML
	private Button evaluateBtn;
	@FXML
	private Button clearBtn;

	private EquityTask equityTask;
	private boolean isEquityRunning = false;
	private List<RowController> rows;

	private static EquityController instance;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		Platform.runLater(() -> initializeRows());
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

	private void initializeRows() {
		rows = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("row.fxml"));
				Parent p = fxmlLoader.load();

				RowController row = ((RowController) fxmlLoader.getController());
				row.setPlayerName("Player " + i);
				rows.add(row);

				vBox.getChildren().add(p);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void getEquity() {
		clearEquityFields();

		Deck deck = new Deck();
		deck.shuffle();

		LinkedHashMap<Player, RowController> playerRows = new LinkedHashMap<>();

		for (RowController row : rows) {
			if (row.getHand().length() > 0) {
				List<Card> hand = CardUtils.parseHand(row.getHand(), deck);
				if (hand == null) {
					// TODO let the user know
					System.out.println("Invalid hand format");
					return;
				}

				Player player = new Player("Player " + UUID.randomUUID());
				if (row.getHand().equalsIgnoreCase("random")) {
					player.setHasRandom(true);
				}

				player.addCard(hand.get(0));
				player.addCard(hand.get(1));
				playerRows.put(player, row);
			}
		}

		List<Card> board = getBoard(deck);

		if (playerRows.size() > 0) {
			equityTask = new EquityTask(playerRows, board);
			new Thread(equityTask).start();
			isEquityRunning = true;

			Platform.runLater(() -> evaluateBtn.setText("Stop"));
		}
	}

	private List<Card> getBoard(Deck deck) {
		String boardString = boardField.getText();
		List<Card> board = CardUtils.parseBoard(boardString, deck);
		if (board == null) {
			board = new ArrayList<>();
		}
		return board;
	}

	public void stopEquity() {
		equityTask.setContinue(false);
		isEquityRunning = false;
		Platform.runLater(() -> evaluateBtn.setText("Evaluate"));
	}

	private void clearEquityFields() {
		for (RowController row : rows) {
			row.clearEquity();
		}
	}

	private void clearAllFields() {
		for (RowController row : rows) {
			row.clearFields();
		}
		boardField.clear();
	}

	public static EquityController getInstance() {
		return instance;
	}
}
