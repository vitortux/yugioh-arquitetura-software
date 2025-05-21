package br.com.senac.domain.card;

import lombok.Getter;

@Getter
public class Card {
    private final String name;
    private final int atk;
    private final int def;
    private CardStrategy state;

    private Card(String name, int atk, int def, CardStrategy state) {
        this.name = name;
        this.atk = atk;
        this.def = def;
        this.state = state;
    }

    public static class CartaBuilder {
        private String name;
        private int atk;
        private int def;
        private CardStrategy state;

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

        public CartaBuilder setState(CardStrategy state) {
            this.state = state;
            return this;
        }

        public Card build() {
            return new Card(name, atk, def, state);
        }
    }

    @Override
    public String toString() {
        return "Card [atk=" + atk + ", def=" + def + ", state=" + state + "]";
    }
}
