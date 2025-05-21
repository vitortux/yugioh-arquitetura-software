package br.com.senac.domain.jogo;

import java.util.Scanner;

import br.com.senac.domain.jogador.Jogador;
import br.com.senac.domain.jogo.state.DefinirJogadoresState;
import br.com.senac.domain.jogo.state.IJogoState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jogo {
	private static Jogo instance;
	private Jogador[] jogadores;
	private boolean rodando;
	private IJogoState state;
	private final Scanner scanner;

	private Jogo() {
		this.jogadores = new Jogador[2];
		this.rodando = true;
		this.scanner = new Scanner(System.in);
		this.state = new DefinirJogadoresState(this);
	}

	public static Jogo getInstance() {
		if (instance == null) {
			instance = new Jogo();
		}
		return instance;
	}

	public void run() {
		while (rodando) {
			this.state.execute();
		}
	}
}
