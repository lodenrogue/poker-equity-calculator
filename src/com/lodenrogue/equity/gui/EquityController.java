package com.lodenrogue.equity.gui;

import java.net.URL;
import java.util.ResourceBundle;

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
		// TODO
	}

}
