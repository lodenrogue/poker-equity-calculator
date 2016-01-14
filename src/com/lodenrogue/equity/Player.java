package com.lodenrogue.equity;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private List<Card> hand;
	private boolean hasRandom = false;
	private long wins;

	public Player(String username) {
		this.name = username;
		hand = new ArrayList<Card>();
		wins = 0;
	}

	public void addCard(Card card) {
		hand.add(card);
	}

	public List<Card> getHand() {
		return hand;
	}

	public boolean hasRandom() {
		return hasRandom;
	}

	public void setHasRandom(boolean hasRandom) {
		this.hasRandom = hasRandom;
	}

	public String getName() {
		return name;
	}

	public void addWin() {
		wins++;
	}

	public long getWins() {
		return wins;
	}
}
