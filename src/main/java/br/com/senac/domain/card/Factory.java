package br.com.senac.domain.card;

import lombok.Getter;

@Getter
public enum Factory {
    MOLTRES("Moltres", 90, 80),
    ARTICUNO("Articuno", 85, 95),
    ZAPDOS("Zapdos", 88, 85),
    LUGIA("Lugia", 100, 120),
    HOOH("Ho-Oh", 95, 100),
    GAARA("Gaara", 80, 70),
    NARUTO("Naruto", 70, 60),
    MEWTWO("Mewtwo", 110, 95),
    MESSI("Messi", 70, 55),
    CRISTIANO_RONALDO("Cristiano Ronaldo", 85, 65);

    private final String name;
    private final int atk;
    private final int def;

    Factory(String name, int atk, int def) {
        this.name = name;
        this.atk = atk;
        this.def = def;
    }

    public Card create(CardStrategy state) {
        return new Card.CartaBuilder()
                .setName(this.name)
                .setAtk(this.atk)
                .setDef(this.def)
                .setState(state)
                .build();
    }
}
