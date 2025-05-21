package br.com.senac.domain.card;

import br.com.senac.domain.battle.BattleChoice;
import br.com.senac.domain.battle.BattleOutcome;
import br.com.senac.domain.battle.BattleResult;
import br.com.senac.domain.game.GameException;

public enum CardStrategy {
    ATAQUE {
        @Override
        public BattleResult play(BattleChoice ownChoice, BattleChoice opponentChoice) {
            var own = ownChoice.card();
            var opp = opponentChoice.card();
            var oppMode = opponentChoice.mode();

            if (oppMode == ATAQUE) {
                if (own.getAtk() > opp.getAtk())
                    return new BattleResult(BattleOutcome.PRIMEIRA_CARTA_VENCE, opp, own.getAtk() - opp.getAtk());
                else if (own.getAtk() < opp.getAtk())
                    return new BattleResult(BattleOutcome.SEGUNDA_CARTA_VENCE, own, opp.getAtk() - own.getAtk());
                else
                    return new BattleResult(BattleOutcome.AMBAS_DEFENDEM, null, 0);
            } else if (oppMode == DEFESA) {
                if (own.getAtk() > opp.getDef())
                    return new BattleResult(BattleOutcome.PRIMEIRA_CARTA_VENCE, opp, own.getAtk() - opp.getDef());
                else if (own.getAtk() < opp.getDef())
                    return new BattleResult(BattleOutcome.SEGUNDA_CARTA_VENCE, own, opp.getDef() - own.getAtk());
                else
                    return new BattleResult(BattleOutcome.AMBAS_DEFENDEM, null, 0);
            }

            throw new GameException("Modo desconhecido para carta: " + oppMode);
        }
    },
    DEFESA {
        @Override
        public BattleResult play(BattleChoice ownChoice, BattleChoice opponentChoice) {
            var oppMode = opponentChoice.mode();

            if (oppMode == DEFESA) {
                return new BattleResult(BattleOutcome.AMBAS_DEFENDEM, null, 0);
            } else if (oppMode == ATAQUE) {
                var result = ATAQUE.play(opponentChoice, ownChoice);
                return switch (result.outcome()) {
                    case PRIMEIRA_CARTA_VENCE -> new BattleResult(BattleOutcome.SEGUNDA_CARTA_VENCE, ownChoice.card(),
                            result.damage());
                    case SEGUNDA_CARTA_VENCE -> new BattleResult(BattleOutcome.PRIMEIRA_CARTA_VENCE,
                            opponentChoice.card(), result.damage());
                    default -> new BattleResult(BattleOutcome.AMBAS_DEFENDEM, null, 0);
                };
            }

            throw new GameException("Modo desconhecido para carta: " + oppMode);
        }
    };

    public abstract BattleResult play(BattleChoice ownChoice, BattleChoice opponentChoice);
}
