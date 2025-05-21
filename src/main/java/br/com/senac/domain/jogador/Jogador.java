package br.com.senac.domain.jogador;

import java.util.List;

import br.com.senac.domain.carta.Carta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jogador {
    private String nome;
    private List<Carta> deck;

    public Jogador(String nome) {
        this.nome = nome;
    }
}
