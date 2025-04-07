// Pacote: org.runasrpg.magic

package org.runasrpg.magic;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EssenciaMagiaManager {

    public static ItemStack gerarLivroDeMagia(Spell magia) {
        ItemStack livro = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = livro.getItemMeta();

        meta.setDisplayName("§dMagia: §f" + magia.getNome());
        meta.addEnchant(Enchantment.DURABILITY, 1, true); // Apenas para brilhar

        List<String> lore = new ArrayList<>();
        lore.add("§7Composição:");
        for (Runa r : magia.getRunas()) {
            lore.add(" §8• " + r.getRaridade().getCor() + r.getNome());
        }
        lore.add("§8[Magia=" + magia.getNome() + "]");
        lore.add("§7Use este livro em uma bigorna para fundir.");

        meta.setLore(lore);
        livro.setItemMeta(meta);
        return livro;
    }

    public static boolean isLivroDeMagia(ItemStack item) {
        if (item == null || item.getType() != Material.ENCHANTED_BOOK || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) return false;
        return meta.getLore().stream().anyMatch(l -> l.contains("[Magia="));
    }

    public static String getNomeDaMagia(ItemStack item) {
        if (!isLivroDeMagia(item)) return null;
        for (String linha : item.getItemMeta().getLore()) {
            if (linha.contains("[Magia=")) {
                return linha.replace("§8[Magia=", "").replace("]", "").trim();
            }
        }
        return null;
    }
}
