package br.com.senac.domain.game;

import java.util.Scanner;

import br.com.senac.domain.game.state.IGameState;
import br.com.senac.domain.game.state.NameSelectionState;
import br.com.senac.domain.player.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
    private static Game instance;
    private Player[] players;
    private boolean running;
    private IGameState state;
    private final Scanner scanner;

    private Game() {
        this.players = new Player[2];
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.state = new NameSelectionState(this);
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void run() {
        while (running) {
            this.state.execute();
        }
    }
}
