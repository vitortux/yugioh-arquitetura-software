package br.com.senac.domain.jogo.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.senac.domain.carta.Carta;
import br.com.senac.domain.carta.CartaFactory;
import br.com.senac.domain.carta.CartaStrategy;
import br.com.senac.domain.jogador.Jogador;
import br.com.senac.domain.jogo.Jogo;
import br.com.senac.domain.jogo.JogoException;

public class SelecionarCartasState implements IJogoState {
    private final Jogo jogo;
    private List<CartaFactory> opcoes = Arrays.asList(CartaFactory.values());

    public SelecionarCartasState(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public void execute() {
        Jogador[] players = jogo.getJogadores();

        for (int i = 0; i < players.length; i++) {
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();

            System.out.printf("Seleção de cartas: jogador %s, escolha suas cartas!%n%n", players[i].getNome());

            this.displayAllCards();

            boolean loop = true;
            List<Carta> selected = null;

            while (loop) {
                System.out.println("\nDigite (entre virgulas) suas cartas:");
                System.out.println("\t-> Ex: 1, 3, 4, 7, 8, 10");
                System.out.print("\n> ");

                String input = jogo.getScanner().nextLine();

                try {
                    selected = parseInput(input);
                    loop = false;
                } catch (JogoException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }

            players[i].setDeck(selected);
        }

        jogo.setState(new EmJogoState(jogo));
    }

    private void displayAllCards() {
        for (int i = 0; i < opcoes.size(); i++) {
            CartaFactory card = opcoes.get(i);
            System.out.printf("%d - %-20s | ATK: %3d | DEF: %3d%n",
                    i + 1,
                    card.getName(),
                    card.getAtk(),
                    card.getDef());
        }
    }

    private List<Carta> parseInput(String input) throws JogoException {
        String[] parts = input.split(",");

        if (parts.length != 6) {
            throw new JogoException("Você deve selecionar exatamente 6 cartas!");
        }

        Set<Integer> deck = new HashSet<>();

        for (String part : parts) {
            try {
                int number = Integer.parseInt(part.trim()) - 1;
                deck.add(number);
            } catch (NumberFormatException e) {
                throw new JogoException("Você deve selecionar apenas números! Valor inválido:" + part);
            }
        }

        if (deck.size() != 6) {
            throw new JogoException("Você não pode adicionar cartas repetidas!");
        }

        return new ArrayList<>(deck.stream()
                .map(index -> opcoes.get(index).create(CartaStrategy.ATAQUE))
                .toList());
    }
}
