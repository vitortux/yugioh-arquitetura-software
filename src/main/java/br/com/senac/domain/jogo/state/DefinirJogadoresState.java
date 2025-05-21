package br.com.senac.domain.jogo.state;

import br.com.senac.domain.jogador.Jogador;
import br.com.senac.domain.jogo.Jogo;

public class DefinirJogadoresState implements IJogoState {
    private final Jogo jogo;

    public DefinirJogadoresState(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public void execute() {
        Jogador[] players = new Jogador[2];

        for (int i = 0; i < players.length; i++) {
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();
            System.out.printf("Digite seu nome, jogador %d: ", i + 1);
            String name = jogo.getScanner().nextLine().trim();
            players[i] = new Jogador(name);
        }

        jogo.setJogadores(players);
        jogo.setState(new SelecionarCartasState(jogo));
    }
}
