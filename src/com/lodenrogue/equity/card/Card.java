package com.lodenrogue.equity.card;

public class Card {
	private Rank rank;
	private Suit suit;

	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public String toString() {
		String suit = String.valueOf(getSuit().toString().charAt(0)).toLowerCase();
		String rank = "";
		if(getRank().getValue() >= 10){
			if(getRank().getValue() == 10){
				rank = "T";
			}
			else if(getRank().getValue() == 11){
				rank = "J";
			}
			else if(getRank().getValue() == 12){
				rank = "Q";
			}
			else if(getRank().getValue() == 13){
				rank = "K";
			}
			else if(getRank().getValue() == 14){
				rank = "A";
			}
		}
		else {
			rank = String.valueOf(getRank().getValue());
		}
		
		return rank + suit;
	}
}
