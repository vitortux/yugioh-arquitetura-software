package br.com.senac.domain.jogo.state;

import java.util.List;

import br.com.senac.domain.batalha.EscolhaDeBatalha;
import br.com.senac.domain.carta.Carta;
import br.com.senac.domain.carta.CartaStrategy;
import br.com.senac.domain.jogador.Jogador;
import br.com.senac.domain.jogo.Jogo;
import br.com.senac.domain.jogo.JogoException;

public class EmJogoState implements IJogoState {
    private final Jogo jogo;
    private EscolhaDeBatalha[] escolhas = new EscolhaDeBatalha[2];

    public EmJogoState(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public void execute() {
        Jogador[] players = jogo.getJogadores();

        for (int i = 0; i < players.length; i++) {
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();

            System.out.printf("Jogador %s, escolha a carta que deseja jogar!%n%n", players[i].getNome());

            this.imprimirDeckDoJogador(players[i].getDeck());

            boolean loop = true;
            EscolhaDeBatalha selecionada = null;

            while (loop) {
                System.out.println("\nDigite o número da carta e o modo (ataque ou defesa).");
                System.out.println("\tEx: 2, ataque");
                System.out.print("\n> ");

                String input = jogo.getScanner().nextLine().trim();

                try {
                    selecionada = parseInput(input, players[i].getDeck());
                    escolhas[i] = selecionada;
                    loop = false;
                } catch (JogoException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
        }

        jogo.setState(new BatalhaState(jogo, escolhas));
    }

    private void imprimirDeckDoJogador(List<Carta> deck) {
        for (int i = 0; i < deck.size(); i++) {
            Carta card = deck.get(i);
            System.out.printf("%d - %-20s | ATK: %3d | DEF: %3d%n",
                    i + 1, card.getName(), card.getAtk(), card.getDef());

        }
    }

    private EscolhaDeBatalha parseInput(String input, List<Carta> deck) {
        String[] partes = input.split(",");

        if (partes.length != 2) {
            throw new JogoException("Formato inválido! Use: número, ataque/defesa");
        }

        int index;

        try {
            index = Integer.parseInt(partes[0].trim()) - 1;
        } catch (NumberFormatException e) {
            throw new JogoException("Número da carta inválido!");
        }

        if (index < 0 || index >= deck.size()) {
            throw new JogoException("Carta fora do intervalo!");
        }

        String estrategiaInput = partes[1].trim().toUpperCase();
        CartaStrategy estrategia;

        try {
            estrategia = CartaStrategy.valueOf(estrategiaInput);
        } catch (IllegalArgumentException e) {
            throw new JogoException("Modo inválido! Use ATAQUE ou DEFESA.");
        }

        return new EscolhaDeBatalha(deck.get(index), estrategia);
    }
}
