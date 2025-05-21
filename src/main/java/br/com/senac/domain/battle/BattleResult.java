package br.com.senac.domain.battle;

import br.com.senac.domain.card.Card;

public record BattleResult(BattleOutcome outcome, Card defeated, int damage) {

}
