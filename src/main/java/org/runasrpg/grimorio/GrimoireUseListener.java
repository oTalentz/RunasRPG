package org.runasrpg.grimorio;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.runasrpg.magic.Runa;
import org.runasrpg.magic.RunaRegistry;
import org.runasrpg.player.PlayerData;
import org.runasrpg.player.PlayerDataManager;

import java.util.List;

public class GrimoireUseListener implements Listener {

    @EventHandler
    public void aoUsarGrimorio(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();

        if (item == null || item.getType() != Material.BOOK || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null || lore.size() < 2 || !lore.get(2).startsWith("§8ID: ")) return;

        String id = lore.get(2).replace("§8ID: ", "").trim();
        Runa runa = RunaRegistry.getRuna(id);
        if (runa == null) {
            p.sendMessage("§cEste grimório está corrompido (runa inválida).");
            return;
        }

        PlayerData data = PlayerDataManager.get(p.getUniqueId());
        if (data.possuiRuna(id)) {
            p.sendMessage("§eVocê já desbloqueou a runa §f" + runa.getNome());
            return;
        }

        // Desbloquear
        data.desbloquearRuna(id);
        p.getInventory().removeItem(item);
        p.sendMessage("§aVocê desbloqueou a runa §f" + runa.getNome() + "§a com sucesso!");
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
        p.getWorld().playEffect(p.getLocation(), Effect.FIREWORKS_SPARK, 0);
    }
}
