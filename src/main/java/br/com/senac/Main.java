package br.com.senac;

import br.com.senac.domain.game.Game;

public class Main {
    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.run();
    }
}
