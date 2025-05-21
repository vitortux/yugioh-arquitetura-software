package br.com.senac.domain.carta;

import br.com.senac.domain.batalha.DesfechoDaBatalha;
import br.com.senac.domain.batalha.EscolhaDeBatalha;
import br.com.senac.domain.batalha.ResultadoDaBatalha;
import br.com.senac.domain.jogo.JogoException;

public enum CartaStrategy {
    ATAQUE {
        @Override
        public ResultadoDaBatalha usar(EscolhaDeBatalha ownChoice, EscolhaDeBatalha opponentChoice) {
            var own = ownChoice.carta();
            var opp = opponentChoice.carta();
            var oppMode = opponentChoice.modo();

            if (oppMode == ATAQUE) {
                if (own.getAtk() > opp.getAtk())
                    return new ResultadoDaBatalha(DesfechoDaBatalha.PRIMEIRA_CARTA_VENCE, opp,
                            own.getAtk() - opp.getAtk());
                else if (own.getAtk() < opp.getAtk())
                    return new ResultadoDaBatalha(DesfechoDaBatalha.SEGUNDA_CARTA_VENCE, own,
                            opp.getAtk() - own.getAtk());
                else
                    return new ResultadoDaBatalha(DesfechoDaBatalha.AMBAS_DEFENDEM, null, 0);
            } else if (oppMode == DEFESA) {
                if (own.getAtk() > opp.getDef())
                    return new ResultadoDaBatalha(DesfechoDaBatalha.PRIMEIRA_CARTA_VENCE, opp,
                            own.getAtk() - opp.getDef());
                else if (own.getAtk() < opp.getDef())
                    return new ResultadoDaBatalha(DesfechoDaBatalha.SEGUNDA_CARTA_VENCE, own,
                            opp.getDef() - own.getAtk());
                else
                    return new ResultadoDaBatalha(DesfechoDaBatalha.AMBAS_DEFENDEM, null, 0);
            }

            throw new JogoException("Modo desconhecido para carta: " + oppMode);
        }
    },
    DEFESA {
        @Override
        public ResultadoDaBatalha usar(EscolhaDeBatalha ownChoice, EscolhaDeBatalha opponentChoice) {
            var oppMode = opponentChoice.modo();

            if (oppMode == DEFESA) {
                return new ResultadoDaBatalha(DesfechoDaBatalha.AMBAS_DEFENDEM, null, 0);
            } else if (oppMode == ATAQUE) {
                var result = ATAQUE.usar(opponentChoice, ownChoice);
                return switch (result.desfecho()) {
                    case PRIMEIRA_CARTA_VENCE ->
                        new ResultadoDaBatalha(DesfechoDaBatalha.SEGUNDA_CARTA_VENCE, ownChoice.carta(),
                                result.diferencaDeDano());
                    case SEGUNDA_CARTA_VENCE -> new ResultadoDaBatalha(DesfechoDaBatalha.PRIMEIRA_CARTA_VENCE,
                            opponentChoice.carta(), result.diferencaDeDano());
                    default -> new ResultadoDaBatalha(DesfechoDaBatalha.AMBAS_DEFENDEM, null, 0);
                };
            }

            throw new JogoException("Modo desconhecido para carta: " + oppMode);
        }
    };

    public abstract ResultadoDaBatalha usar(EscolhaDeBatalha ownChoice, EscolhaDeBatalha opponentChoice);
}
