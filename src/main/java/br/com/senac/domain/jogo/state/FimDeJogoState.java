package br.com.senac.domain.jogo.state;

import br.com.senac.domain.jogador.Jogador;
import br.com.senac.domain.jogo.Jogo;

public class FimDeJogoState implements IJogoState {
    private final Jogo jogo;
    private final Jogador vencedor;

    public FimDeJogoState(Jogo jogo, Jogador vencedor) {
        this.jogo = jogo;
        this.vencedor = vencedor;
    }

    @Override
    public void execute() {
        System.out.print("\u001B[2J\u001B[H");
        System.out.flush();

        System.out.println("=====================================");
        System.out.println("          Jogo Finalizado!            ");
        System.out.println("=====================================");
        System.out.printf("%nParabéns, %s! Você venceu o jogo!%n", vencedor.getNome());
        System.out.println("\n >> Obrigado por jogar! <<\n");
        System.out.print("> ");
        jogo.getScanner().nextLine();
        jogo.setRodando(false);
    }
}
