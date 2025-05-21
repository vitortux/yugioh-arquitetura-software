package br.com.senac.domain.batalha;

import br.com.senac.domain.carta.Carta;

public record ResultadoDaBatalha(DesfechoDaBatalha desfecho, Carta cartaDerrotada, int diferencaDeDano) {

}
