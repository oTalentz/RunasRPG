package org.runasrpg.magic;

import java.util.List;

public class Spell {
    private final String nome;
    private final List<Runa> runas;

    public Spell(String nome, List<Runa> runas) {
        this.nome = nome;
        this.runas = runas;
    }

    public String getNome() {
        return nome;
    }

    public List<Runa> getRunas() {
        return runas;
    }

    public String descreverMagia() {
        StringBuilder sb = new StringBuilder("§6" + nome + "§7: ");
        for (Runa runa : runas) {
            sb.append("§b").append(runa.getNome()).append("§7, ");
        }
        return sb.substring(0, sb.length() - 2);
    }
}
