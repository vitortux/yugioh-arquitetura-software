package br.com.senac.domain.game.state;

import br.com.senac.domain.battle.BattleChoice;
import br.com.senac.domain.battle.BattleOutcome;
import br.com.senac.domain.battle.BattleResult;
import br.com.senac.domain.game.Game;
import br.com.senac.domain.player.Player;

public class BattleState implements IGameState {
    private final Game game;
    private final Player[] players;
    private final BattleChoice[] choices;

    public BattleState(Game game, BattleChoice[] choices) {
        this.game = game;
        this.choices = choices;
        this.players = game.getPlayers();
    }

    @Override
    public void execute() {
        System.out.print("\u001B[2J\u001B[H");
        System.out.flush();

        BattleResult resultado = choices[0].mode().play(choices[0], choices[1]);
        BattleOutcome outcome = resultado.outcome();

        switch (outcome) {
            case PRIMEIRA_CARTA_VENCE -> {
                players[1].getDeck().remove(resultado.defeated());
                System.out.printf("%nJogador %s vence essa rodada!%n", players[0].getName());
                System.out.printf("%n>> Jogador %s perdeu a carta \"%s\" por uma diferença de %d de dano! <<%n",
                        players[1].getName(),
                        resultado.defeated().getName(), resultado.damage());
            }
            case SEGUNDA_CARTA_VENCE -> {
                players[0].getDeck().remove(resultado.defeated());
                System.out.printf("%nJogador %s vence essa rodada!%n", players[1].getName());
                System.out.printf("%n>> Jogador %s perdeu a carta \"%s\" por uma diferença de %d de dano! <<%n",
                        players[0].getName(),
                        resultado.defeated().getName(), resultado.damage());
            }
            case AMBAS_DEFENDEM -> {
                System.out.println("Empate - ambos mantêm suas cartas.");
            }
        }

        System.out.print("\n> ");
        game.getScanner().nextLine();

        if (players[0].getDeck().isEmpty()) {
            game.setState(new EndGameState(game, players[1]));
        } else if (players[1].getDeck().isEmpty()) {
            game.setState(new EndGameState(game, players[0]));
        } else {
            game.setState(new InGameState(game));
        }
    }
}
