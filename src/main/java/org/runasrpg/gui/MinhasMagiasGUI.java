// Pacote: org.runasrpg.gui

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
import org.runasrpg.magic.EssenciaMagiaManager;
import org.runasrpg.magic.Runa;
import org.runasrpg.magic.Spell;
import org.runasrpg.player.PlayerData;
import org.runasrpg.player.PlayerDataManager;

import java.util.ArrayList;
import java.util.List;

public class MinhasMagiasGUI implements Listener {

    private static final String TITULO = "§dSuas Magias Criadas";

    public static void abrir(Player player) {
        PlayerData data = PlayerDataManager.get(player.getUniqueId());
        List<Spell> magias = data.getMagias();

        int tamanho = ((magias.size() / 9) + 1) * 9;
        Inventory inv = Bukkit.createInventory(null, Math.max(tamanho, 9), TITULO);

        for (Spell magia : magias) {
            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§bMagia: §f" + magia.getNome());

            List<String> lore = new ArrayList<>();
            lore.add("§7Composição:");
            for (Runa r : magia.getRunas()) {
                lore.add(" §8• " + r.getRaridade().getCor() + r.getNome());
            }
            lore.add("");
            lore.add("§eClique para receber esta magia como livro.");

            meta.setLore(lore);
            item.setItemMeta(meta);

            inv.addItem(item);
        }

        player.openInventory(inv);
    }

    @EventHandler
    public void aoClicar(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(TITULO)) return;

        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        String nome = item.getItemMeta().getDisplayName().replace("§bMagia: §f", "");
        PlayerData data = PlayerDataManager.get(p.getUniqueId());

        for (Spell magia : data.getMagias()) {
            if (magia.getNome().equalsIgnoreCase(nome)) {
                ItemStack livro = EssenciaMagiaManager.gerarLivroDeMagia(magia);
                p.getInventory().addItem(livro);
                p.sendMessage("§aVocê recebeu a magia §e" + magia.getNome() + " §acomo livro!");
                break;
            }
        }
    }
}