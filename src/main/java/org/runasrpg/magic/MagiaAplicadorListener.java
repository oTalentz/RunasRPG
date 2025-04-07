// Pacote: org.runasrpg.magic

package org.runasrpg.magic;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MagiaAplicadorListener implements Listener {

    private static final List<Material> EQUIPAVEIS = List.of(
            Material.DIAMOND_SWORD, Material.IRON_SWORD, Material.BOW,
            Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS
    );

    @EventHandler
    public void aoFundir(InventoryClickEvent e) {
        if (e.getCursor() == null || e.getCurrentItem() == null) return;

        ItemStack essencia = e.getCursor();
        ItemStack alvo = e.getCurrentItem();

        if (!EssenciaMagiaManager.isLivroDeMagia(essencia)) return;
        if (!EQUIPAVEIS.contains(alvo.getType())) return;

        e.setCancelled(true);
        String nomeMagia = EssenciaMagiaManager.getNomeDaMagia(essencia);
        if (nomeMagia == null) return;

        ItemMeta meta = alvo.getItemMeta();
        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

        boolean jaAplicado = lore.stream().anyMatch(l -> l.contains("§dMagia: "));
        if (jaAplicado) {
            ((Player) e.getWhoClicked()).sendMessage("§cEsse item já possui uma magia aplicada.");
            return;
        }

        lore.add("§dMagia: §f" + nomeMagia);
        meta.setLore(lore);
        alvo.setItemMeta(meta);

        e.setCursor(null); // remove o livro

        Player p = (Player) e.getWhoClicked();
        p.sendMessage("§aA magia §e" + nomeMagia + " §afoi fundida ao item!");
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
    }
}