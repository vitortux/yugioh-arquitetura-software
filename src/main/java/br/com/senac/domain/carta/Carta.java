package br.com.senac.domain.carta;

import lombok.Getter;

@Getter
public class Carta {
    private final String name;
    private final int atk;
    private final int def;
    private CartaStrategy state;

    private Carta(String name, int atk, int def, CartaStrategy state) {
        this.name = name;
        this.atk = atk;
        this.def = def;
        this.state = state;
    }

    public static class CartaBuilder {
        private String name;
        private int atk;
        private int def;
        private CartaStrategy state;

        public CartaBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CartaBuilder setAtk(int atk) {
            this.atk = atk;
            return this;
        }

        public CartaBuilder setDef(int def) {
            this.def = def;
            return this;
        }

        public CartaBuilder setState(CartaStrategy state) {
            this.state = state;
            return this;
        }

        public Carta build() {
            return new Carta(name, atk, def, state);
        }
    }

    @Override
    public String toString() {
        return "Card [atk=" + atk + ", def=" + def + ", state=" + state + "]";
    }
}
