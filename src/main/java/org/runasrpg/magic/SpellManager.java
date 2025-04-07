package org.runasrpg.magic;

import java.util.ArrayList;
import java.util.List;

public class SpellManager {

    public static Spell criarMagia(List<Runa> runasSelecionadas) {
        if (runasSelecionadas.size() < 2) return null;

        String nome = gerarNomeMagia(runasSelecionadas);
        return new Spell(nome, new ArrayList<>(runasSelecionadas));
    }

    private static String gerarNomeMagia(List<Runa> runas) {
        StringBuilder nome = new StringBuilder();
        for (Runa runa : runas) {
            nome.append(runa.getNome().split(" ")[2]); // Ex: "Runa de Fogo" -> "Fogo"
        }
        return nome.toString();
    }
}
