package org.runasrpg.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.runasrpg.magic.Spell;

import java.util.*;

public class ManaData {
    private final UUID uuid;
    private double manaAtual = 100;
    private final double manaMax = 100;
    private final Map<String, Long> cooldowns = new HashMap<>();

    public ManaData(UUID uuid) {
        this.uuid = uuid;

        // Regen loop
        Bukkit.getScheduler().runTaskTimerAsynchronously(
                Bukkit.getPluginManager().getPlugin("RunasRPG"),
                this::regenerarMana,
                20L, 20L
        );
    }

    public double getManaAtual() {
        return manaAtual;
    }

    public void setManaAtual(double valor) {
        manaAtual = Math.min(manaMax, Math.max(0, valor));
        atualizarBarra();
    }

    public void gastarMana(double quantia) {
        setManaAtual(manaAtual - quantia);
    }

    public boolean temMana(double custo) {
        return manaAtual >= custo;
    }

    public boolean emCooldown(String spellNome) {
        return cooldowns.containsKey(spellNome)
                && System.currentTimeMillis() < cooldowns.get(spellNome);
    }

    public void colocarEmCooldown(String spellNome, int segundos) {
        cooldowns.put(spellNome, System.currentTimeMillis() + (segundos * 1000L));
    }

    private void atualizarBarra() {
        Player p = Bukkit.getPlayer(uuid);
        if (p != null) {
            p.setExp((float) (manaAtual / manaMax));
            p.setLevel((int) manaAtual);
        }
    }

    private void regenerarMana() {
        if (manaAtual < manaMax) {
            setManaAtual(manaAtual + 2);
        }
    }
}
