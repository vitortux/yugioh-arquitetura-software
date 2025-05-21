package br.com.senac.domain.game.state;

import br.com.senac.domain.game.Game;
import br.com.senac.domain.player.Player;

public class NameSelectionState implements IGameState {
    private final Game game;

    public NameSelectionState(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        Player[] players = new Player[2];

        for (int i = 0; i < players.length; i++) {
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();
            System.out.printf("Digite seu nome, jogador %d: ", i + 1);
            String name = game.getScanner().nextLine().trim();
            players[i] = new Player(name);
        }

        game.setPlayers(players);
        game.setState(new SelectionState(game));
    }
}
