package org.runasrpg.magic;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.runasrpg.player.ManaData;
import org.runasrpg.player.PlayerDataManager;

import java.util.Random;

public class SpellExecutor {

    private static final Random random = new Random();

    public static void executar(Spell spell, Player player) {
        ManaData mana = PlayerDataManager.get(player.getUniqueId()).getMana();

        double custoMana = calcularCustoMana(spell);
        int cooldown = calcularCooldown(spell);

        if (mana.emCooldown(spell.getNome())) {
            player.sendMessage("§cEssa magia ainda está em cooldown!");
            return;
        }

        if (!mana.temMana(custoMana)) {
            player.sendMessage("§cVocê não tem mana suficiente!");
            return;
        }

        mana.gastarMana(custoMana);
        mana.colocarEmCooldown(spell.getNome(), cooldown);

        player.sendMessage("§eExecutando magia: §6" + spell.getNome());
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

        boolean fogo = false, cura = false, projecao = false, area = false, tempo = false;

        for (Runa runa : spell.getRunas()) {
            switch (runa.getId()) {
                case "fogo": fogo = true; break;
                case "cura": cura = true; break;
                case "projetil": projecao = true; break;
                case "area": area = true; break;
                case "tempo": tempo = true; break;
            }
        }

        // Efeito crítico aleatório
        boolean critico = random.nextDouble() < 0.1;
        if (critico) {
            player.sendMessage("§d§lCRÍTICO!");
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
            player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
        }

        // Combos especiais
        if (projecao && fogo) {
            Arrow arrow = player.launchProjectile(Arrow.class);
            arrow.setFireTicks(100);
            arrow.setVelocity(player.getLocation().getDirection().multiply(1.8));
        }

        if (area && fogo) {
            player.getWorld().createExplosion(player.getLocation(), critico ? 4F : 2F, false);
        }

        if (cura) {
            double heal = critico ? 10 : 6;
            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + heal));
            player.getWorld().playEffect(player.getLocation().add(0, 2, 0), Effect.HEART, 0);
        }

        if (tempo) {
            player.sendMessage("§bVocê sente o tempo desacelerar ao seu redor...");
            player.setWalkSpeed(0.4f); // Temporariamente aumenta velocidade
        }

        efeitosVisuais(player);
    }

    public static double calcularCustoMana(Spell spell) {
        double custo = 10;
        for (Runa runa : spell.getRunas()) {
            switch (runa.getRaridade()) {
                case COMUM: custo += 2; break;
                case RARA: custo += 4; break;
                case EPICA: custo += 6; break;
                case LENDARIA: custo += 8; break;
            }
        }
        return custo;
    }

    public static int calcularCooldown(Spell spell) {
        int cooldown = 5;
        for (Runa runa : spell.getRunas()) {
            switch (runa.getRaridade()) {
                case COMUM: cooldown += 1; break;
                case RARA: cooldown += 2; break;
                case EPICA: cooldown += 3; break;
                case LENDARIA: cooldown += 4; break;
            }
        }
        return cooldown;
    }

    private static void efeitosVisuais(Player p) {
        Location loc = p.getLocation().add(0, 1, 0);
        for (int i = 0; i < 360; i += 20) {
            double radians = Math.toRadians(i);
            double x = Math.cos(radians);
            double z = Math.sin(radians);
            p.getWorld().playEffect(loc.clone().add(x, 0, z), Effect.WITCH_MAGIC, 0);
        }
    }
}
