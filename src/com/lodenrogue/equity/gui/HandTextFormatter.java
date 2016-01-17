package com.lodenrogue.equity.gui;

public class HandTextFormatter implements TextFormatter {

	@Override
	public String format(String text) {
		String formattedText = text.replace("a", "A");
		formattedText = formattedText.replace("k", "K");
		formattedText = formattedText.replace("q", "Q");
		formattedText = formattedText.replace("j", "J");
		formattedText = formattedText.replace("t", "T");

		return formattedText;
	}

}
