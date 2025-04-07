package org.runasrpg.magic;

public enum Raridade {
    COMUM("§7"),
    RARA("§9"),
    EPICA("§5"),
    LENDARIA("§6");

    private final String cor;

    Raridade(String cor) {
        this.cor = cor;
    }

    public String getCor() {
        return cor;
    }
}
