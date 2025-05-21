package br.com.senac.domain.player;

import java.util.List;

import br.com.senac.domain.card.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private String name;
    private List<Card> deck;

    public Player(String name) {
        this.name = name;
    }
}
