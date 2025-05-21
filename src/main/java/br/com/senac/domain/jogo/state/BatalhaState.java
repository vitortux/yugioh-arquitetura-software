package br.com.senac.domain.jogo.state;

import br.com.senac.domain.batalha.DesfechoDaBatalha;
import br.com.senac.domain.batalha.EscolhaDeBatalha;
import br.com.senac.domain.batalha.ResultadoDaBatalha;
import br.com.senac.domain.jogador.Jogador;
import br.com.senac.domain.jogo.Jogo;

public class BatalhaState implements IJogoState {
	private final Jogo jogo;
	private final Jogador[] jogadores;
	private final EscolhaDeBatalha[] escolhas;

	public BatalhaState(Jogo jogo, EscolhaDeBatalha[] escolhas) {
		this.jogo = jogo;
		this.escolhas = escolhas;
		this.jogadores = jogo.getJogadores();
	}

	@Override
	public void execute() {
		System.out.print("\u001B[2J\u001B[H");
		System.out.flush();

		ResultadoDaBatalha resultado = escolhas[0].modo().usar(escolhas[0], escolhas[1]);
		DesfechoDaBatalha desfecho = resultado.desfecho();

		switch (desfecho) {
			case PRIMEIRA_CARTA_VENCE -> {
				jogadores[1].getDeck().remove(resultado.cartaDerrotada());
				System.out.printf("%nJogador %s vence essa rodada!%n", jogadores[0].getNome());
				System.out.printf("%n>> Jogador %s perdeu a carta \"%s\" por uma diferença de %d de dano! <<%n",
						jogadores[1].getNome(), resultado.cartaDerrotada().getName(), resultado.diferencaDeDano());
			}
			case SEGUNDA_CARTA_VENCE -> {
				jogadores[0].getDeck().remove(resultado.cartaDerrotada());
				System.out.printf("%nJogador %s vence essa rodada!%n", jogadores[1].getNome());
				System.out.printf("%n>> Jogador %s perdeu a carta \"%s\" por uma diferença de %d de dano! <<%n",
						jogadores[0].getNome(), resultado.cartaDerrotada().getName(), resultado.diferencaDeDano());
			}
			case AMBAS_DEFENDEM -> {
				System.out.println("Empate - ambos mantêm suas cartas.");
			}
		}

		System.out.print("\n> ");
		jogo.getScanner().nextLine();

		if (jogadores[0].getDeck().isEmpty()) {
			jogo.setState(new FimDeJogoState(jogo, jogadores[1]));
		} else if (jogadores[1].getDeck().isEmpty()) {
			jogo.setState(new FimDeJogoState(jogo, jogadores[0]));
		} else {
			jogo.setState(new EmJogoState(jogo));
		}
	}
}
