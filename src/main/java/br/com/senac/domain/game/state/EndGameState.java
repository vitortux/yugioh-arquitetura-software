package br.com.senac.domain.game.state;

import br.com.senac.domain.game.Game;
import br.com.senac.domain.player.Player;

public class EndGameState implements IGameState {
    private final Game game;
    private final Player winner;

    public EndGameState(Game game, Player winner) {
        this.game = game;
        this.winner = winner;
    }

    @Override
    public void execute() {
        System.out.print("\u001B[2J\u001B[H");
        System.out.flush();

        System.out.println("=====================================");
        System.out.println("          Jogo Finalizado!            ");
        System.out.println("=====================================");
        System.out.printf("%nParabéns, %s! Você venceu o jogo!%n", winner.getName());
        System.out.println("\n >> Obrigado por jogar! <<\n");
        System.out.print("> ");
        game.getScanner().nextLine();
        game.setRunning(false);
    }
}
