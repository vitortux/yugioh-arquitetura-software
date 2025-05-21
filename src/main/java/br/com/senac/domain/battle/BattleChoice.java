package br.com.senac.domain.battle;

import br.com.senac.domain.card.Card;
import br.com.senac.domain.card.CardStrategy;

public record BattleChoice(Card card, CardStrategy mode) {

}
