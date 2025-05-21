package br.com.senac.domain.batalha;

import br.com.senac.domain.carta.Carta;
import br.com.senac.domain.carta.CartaStrategy;

public record EscolhaDeBatalha(Carta carta, CartaStrategy modo) {

}
