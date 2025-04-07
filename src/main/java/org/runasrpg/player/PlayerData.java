package org.runasrpg.player;

import org.runasrpg.magic.Runa;
import org.runasrpg.magic.RunaRegistry;
import org.runasrpg.magic.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private final List<Spell> magias = new ArrayList<>();
    private final List<String> runasDesbloqueadas = new ArrayList<>();
    private final ManaData manaData;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.manaData = new ManaData(uuid);

        // Desbloqueia todas as runas disponíveis automaticamente
        runasDesbloqueadas.addAll(RunaRegistry.getRunasRegistradas().keySet());
    }

    public void adicionarMagia(Spell magia) {
        magias.add(magia);
    }

    public List<Spell> getMagias() {
        return magias;
    }

    public ManaData getMana() {
        return manaData;
    }

    public void desbloquearRuna(String id) {
        if (!runasDesbloqueadas.contains(id)) runasDesbloqueadas.add(id);
    }

    public boolean possuiRuna(String id) {
        return runasDesbloqueadas.contains(id);
    }

    public List<String> getRunasDesbloqueadas() {
        return runasDesbloqueadas;
    }
}
