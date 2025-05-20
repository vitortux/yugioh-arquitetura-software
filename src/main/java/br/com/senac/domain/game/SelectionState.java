package br.com.senac.domain.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.senac.domain.card.AttackStrategy;
import br.com.senac.domain.card.Card;
import br.com.senac.domain.card.Factory;
import br.com.senac.domain.player.Player;

public class SelectionState implements IGameState {
    private List<Factory> options = Arrays.asList(Factory.values());

    @Override
    public void execute(Game game) {
        Player[] players = game.getPlayers();

        for (int i = 0; i < players.length; i++) {
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();

            System.out.printf("Seleção de cartas: jogador %d, escolha suas cartas!%n%n", i + 1);

            this.displayAllCards();

            boolean loop = true;
            List<Card> selected = null;

            while (loop) {
                System.out.println("\nDigite (entre virgulas) suas cartas:");
                System.out.println("\t-> Ex: 1, 3, 4, 7, 8, 10\n");
                System.out.print("> ");

                String input = game.getScanner().nextLine();

                try {
                    selected = parseInput(input);
                    loop = false;
                } catch (GameException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }

            players[i] = new Player(selected);
        }
    }

    private void displayAllCards() {
        for (int i = 0; i < options.size(); i++) {
            Factory card = options.get(i);
            System.out.printf("%d - %-20s | ATK: %3d | DEF: %3d%n",
                    i + 1,
                    card.getName(),
                    card.getAtk(),
                    card.getDef());
        }
    }

    private List<Card> parseInput(String input) throws GameException {
        String[] parts = input.split(",");

        if (parts.length != 6) {
            throw new GameException("Você deve selecionar exatamente 6 cartas!");
        }

        Set<Integer> deck = new HashSet<>();

        for (String part : parts) {
            try {
                int number = Integer.parseInt(part.trim()) - 1;
                deck.add(number);
            } catch (NumberFormatException e) {
                throw new GameException("Você deve selecionar apenas números! Valor inválido:" + part);
            }
        }

        if (deck.size() != 6) {
            throw new GameException("Você não pode adicionar cartas repetidas!");
        }

        return deck.stream().map(index -> options.get(index).create(new AttackStrategy())).toList();
    }
}
