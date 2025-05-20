package br.com.senac.domain.player;

import java.util.List;

import br.com.senac.domain.card.Card;
import lombok.Data;

@Data
public class Player {
    private List<Card> deck;

    public Player(List<Card> deck) {
        this.deck = deck;
    }

    public Card select(int index) {
        return deck.get(index);
    }
}
