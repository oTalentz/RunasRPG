// Pacote: org.runasrpg.magic

package org.runasrpg.magic;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class MagiaEfeitoCombateListener implements Listener {

    @EventHandler
    public void aoAtacarComMagia(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof LivingEntity)) return;

        Player atacante = (Player) e.getDamager();
        LivingEntity alvo = (LivingEntity) e.getEntity();
        ItemStack arma = atacante.getItemInHand();

        if (arma == null || !arma.hasItemMeta()) return;

        ItemMeta meta = arma.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : null;
        if (lore == null) return;

        for (String linha : lore) {
            if (linha.startsWith("§dMagia: §f")) {
                String nomeMagia = linha.replace("§dMagia: §f", "");
                ativarEfeito(nomeMagia, atacante, alvo);
                break;
            }
        }
    }

    private void ativarEfeito(String nome, Player atacante, LivingEntity alvo) {
        // Exemplo de efeitos por nome da magia
        if (nome.toLowerCase().contains("cura")) {
            atacante.setHealth(Math.min(atacante.getMaxHealth(), atacante.getHealth() + 2));
            atacante.getWorld().playEffect(atacante.getLocation(), Effect.HEART, 0);
        }

        if (nome.toLowerCase().contains("fogo")) {
            alvo.setFireTicks(60);
            alvo.getWorld().playEffect(alvo.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
        }

        if (nome.toLowerCase().contains("explosao")) {
            alvo.getWorld().createExplosion(alvo.getLocation(), 0F);
        }

        if (nome.toLowerCase().contains("congelante") || nome.toLowerCase().contains("gelo")) {
            alvo.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1));
        }

        if (nome.toLowerCase().contains("projecao")) {
            alvo.setVelocity(atacante.getLocation().getDirection().multiply(1.5));
        }

        atacante.playSound(atacante.getLocation(), Sound.ORB_PICKUP, 1, 2);
    }
}
