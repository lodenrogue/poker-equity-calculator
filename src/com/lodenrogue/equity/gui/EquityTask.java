package com.lodenrogue.equity.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lodenrogue.equity.Equity;
import com.lodenrogue.equity.Player;
import com.lodenrogue.equity.card.Card;

import javafx.application.Platform;

public class EquityTask implements Runnable {
	private Map<Player, RowController> playerRows;
	private List<Card> board;
	private boolean continueEval = true;

	public EquityTask(LinkedHashMap<Player, RowController> playerRows, List<Card> board) {
		this.playerRows = playerRows;
		this.board = board;
	}

	public void setContinue(boolean continueEval) {
		this.continueEval = continueEval;
	}

	@Override
	public void run() {
		int iterations = 1;
		int[] playerWins = new int[playerRows.size()];
		Arrays.fill(playerWins, 0);

		Equity equity = new Equity();
		while (iterations < 1_000_000 && continueEval) {
			Map<Player, Float> playerMap;
			playerMap = equity.getEquity(new ArrayList<>(playerRows.keySet()), board);
			updateEquity(playerMap, iterations++, 10_000);
		}

		EquityController controller = EquityController.getInstance();
		controller.stopEquity();
	}

	private void updateEquity(Map<Player, Float> playerMap, int iterations, int updateRate) {
		if (iterations % updateRate == 0) {
			final DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);

			for (Player p : playerMap.keySet()) {
				Platform.runLater(() -> playerRows.get(p).setEquity(df.format(playerMap.get(p)) + "%"));
			}
		}
	}

}
