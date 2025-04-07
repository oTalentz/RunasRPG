package org.runasrpg.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.runasrpg.magic.Runa;
import org.runasrpg.magic.Spell;
import org.runasrpg.magic.SpellExecutor;
import org.runasrpg.player.PlayerData;
import org.runasrpg.player.PlayerDataManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CastGUI implements Listener {

    private static final String TITULO = "§6Suas Magias";

    public static void abrir(Player p) {
        PlayerData data = PlayerDataManager.get(p.getUniqueId());
        Inventory gui = Bukkit.createInventory(null, 27, TITULO);

        for (Spell magia : data.getMagias()) {
            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§b" + magia.getNome());

            List<String> lore = new ArrayList<>();
            lore.add("§7Composição:");
            for (Runa r : magia.getRunas()) {
                lore.add(" §8• " + r.getRaridade().getCor() + r.getNome());
            }
            lore.add("");
            lore.add("§7Mana: §b" + SpellExecutor.calcularCustoMana(magia));

            meta.setLore(lore);
            item.setItemMeta(meta);
            gui.addItem(item);
        }

        p.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(TITULO)) return;
        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String nome = item.getItemMeta().getDisplayName().replace("§b", "");
        PlayerData data = PlayerDataManager.get(p.getUniqueId());
        for (Spell magia : data.getMagias()) {
            if (magia.getNome().equalsIgnoreCase(nome)) {
                SpellExecutor.executar(magia, p);
                p.closeInventory();
                break;
            }
        }
    }
}
