package com.lodenrogue.equity;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private String name;
	private List<Card> hand;
	private boolean inHand = false;

	public Player(String username) {
		this.name = username;
		hand = new ArrayList<Card>();
	}

	public void addCard(Card card) {
		hand.add(card);
	}

	public List<Card> getHand() {
		return hand;
	}

	public boolean isInHand() {
		return inHand;
	}

	public void setInHand(boolean inHand) {
		this.inHand = inHand;
	}
	
	public String getName(){
		return name;
	}
}
