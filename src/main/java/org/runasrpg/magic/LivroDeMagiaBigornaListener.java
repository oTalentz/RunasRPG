// Pacote: org.runasrpg.magic

package org.runasrpg.magic;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LivroDeMagiaBigornaListener implements Listener {

    @EventHandler
    public void aoUsarLivroNaBigorna(InventoryClickEvent e) {
        if (!(e.getInventory() instanceof AnvilInventory)) return;
        if (e.getSlotType() != InventoryType.SlotType.RESULT) return;

        AnvilInventory anvil = (AnvilInventory) e.getInventory();
        ItemStack base = anvil.getItem(0);
        ItemStack livro = anvil.getItem(1);

        if (base == null || livro == null) return;
        if (!EssenciaMagiaManager.isLivroDeMagia(livro)) return;

        String nomeMagia = EssenciaMagiaManager.getNomeDaMagia(livro);
        if (nomeMagia == null) return;

        ItemMeta meta = base.getItemMeta();
        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

        boolean jaTem = lore.stream().anyMatch(l -> l.contains("§dMagia: "));
        if (jaTem) {
            if (e.getWhoClicked() instanceof Player) {
                ((Player) e.getWhoClicked()).sendMessage("§cEste item já possui uma magia.");
            }
            return;
        }

        lore.add("§dMagia: §f" + nomeMagia);
        meta.setLore(lore);
        base.setItemMeta(meta);

        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            p.sendMessage("§aA magia §e" + nomeMagia + " §afoi fundida com sucesso!");
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
        }
    }
}