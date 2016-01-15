package com.lodenrogue.equity.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RowController implements Initializable {
	@FXML
	private Button playerBtn;
	@FXML
	private Button randomBtn;
	@FXML
	private TextField handField;
	@FXML
	private TextField equityField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void onButtonPressed(ActionEvent e) {
		handField.setText("random");
	}

	public void setPlayerName(String name) {
		playerBtn.setText(name);
	}

	public String getHand() {
		return handField.getText();
	}

	public void clearEquity() {
		equityField.clear();
	}

	public void clearHand() {
		handField.clear();
	}

	public void clearFields() {
		clearEquity();
		clearHand();
	}

	public void setEquity(String equity) {
		equityField.setText(equity);
	}

}
