package br.com.senac;

import br.com.senac.domain.jogo.Jogo;

public class Main {
    public static void main(String[] args) {
        Jogo game = Jogo.getInstance();
        game.run();
    }
}
