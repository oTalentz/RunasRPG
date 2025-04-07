package org.runasrpg.grimorio;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GrimoireManager {

    public static ItemStack criarGrimorio(String runaId, String nomeVisivel) {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§5Grimório da " + nomeVisivel);
        meta.setLore(java.util.List.of(
                "§7Clique com este item para desbloquear a runa:",
                "§e" + nomeVisivel,
                "§8ID: " + runaId
        ));

        item.setItemMeta(meta);
        return item;
    }
}
