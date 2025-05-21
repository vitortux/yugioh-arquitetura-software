package br.com.senac.domain.game.state;

import java.util.List;

import br.com.senac.domain.battle.BattleChoice;
import br.com.senac.domain.card.Card;
import br.com.senac.domain.card.CardStrategy;
import br.com.senac.domain.game.Game;
import br.com.senac.domain.game.GameException;
import br.com.senac.domain.player.Player;

public class InGameState implements IGameState {
    private final Game game;
    private BattleChoice[] choices = new BattleChoice[2];

    public InGameState(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        Player[] players = game.getPlayers();

        for (int i = 0; i < players.length; i++) {
            System.out.print("\u001B[2J\u001B[H");
            System.out.flush();

            System.out.printf("Jogador %s, escolha a carta que deseja jogar!%n%n", players[i].getName());

            this.displayPlayerDeck(players[i].getDeck());

            boolean loop = true;
            BattleChoice selected = null;

            while (loop) {
                System.out.println("\nDigite o número da carta e o modo (ataque ou defesa).");
                System.out.println("\tEx: 2, ataque");
                System.out.print("\n> ");

                String input = game.getScanner().nextLine().trim();

                try {
                    selected = parseInput(input, players[i].getDeck());
                    choices[i] = selected;
                    loop = false;
                } catch (GameException e) {
                    System.out.println("\n" + e.getMessage());
                }
            }
        }

        game.setState(new BattleState(game, choices));
    }

    private void displayPlayerDeck(List<Card> deck) {
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            System.out.printf("%d - %-20s | ATK: %3d | DEF: %3d%n",
                    i + 1, card.getName(), card.getAtk(), card.getDef());

        }
    }

    private BattleChoice parseInput(String input, List<Card> deck) {
        String[] parts = input.split(",");

        if (parts.length != 2) {
            throw new GameException("Formato inválido! Use: número, ataque/defesa");
        }

        int index;

        try {
            index = Integer.parseInt(parts[0].trim()) - 1;
        } catch (NumberFormatException e) {
            throw new GameException("Número da carta inválido!");
        }

        if (index < 0 || index >= deck.size()) {
            throw new GameException("Carta fora do intervalo!");
        }

        String modeInput = parts[1].trim().toUpperCase();
        CardStrategy mode;

        try {
            mode = CardStrategy.valueOf(modeInput);
        } catch (IllegalArgumentException e) {
            throw new GameException("Modo inválido! Use ATAQUE ou DEFESA.");
        }

        return new BattleChoice(deck.get(index), mode);
    }
}
