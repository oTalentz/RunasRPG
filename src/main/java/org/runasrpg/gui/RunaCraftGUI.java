package org.runasrpg.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.runasrpg.magic.*;
import org.runasrpg.player.PlayerData;
import org.runasrpg.player.PlayerDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RunaCraftGUI implements Listener {

    private static final String GUI_TITULO = "§9Criação de Magia";
    private static final int TAMANHO = 27;
    private static final int SLOT_PREVIEW = 4;
    private static final int SLOT_CONFIRMAR = 22;
    private static final int[] SLOTS_RUNAS = {10, 11, 12, 13, 14, 15, 16};

    public static void abrir(Player player) {
        Inventory inv = Bukkit.createInventory(null, TAMANHO, GUI_TITULO);
        PlayerData data = PlayerDataManager.get(player.getUniqueId());

        // Molduras
        ItemStack vidro = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta vidroMeta = vidro.getItemMeta();
        vidroMeta.setDisplayName(" ");
        vidro.setItemMeta(vidroMeta);

        for (int i = 0; i < TAMANHO; i++) {
            inv.setItem(i, vidro);
        }

        for (int slot : SLOTS_RUNAS) {
            inv.clear(slot);
        }

        // Adiciona runas desbloqueadas ao inventário (limite visual: 7)
        List<Runa> desbloqueadas = data.getRunasDesbloqueadas().stream()
                .map(RunaRegistry::getRuna)
                .filter(r -> r != null)
                .collect(Collectors.toList());

        for (int i = 0; i < Math.min(desbloqueadas.size(), SLOTS_RUNAS.length); i++) {
            inv.setItem(SLOTS_RUNAS[i], desbloqueadas.get(i).getItem());
        }

        // Pré-visualização
        ItemStack preview = new ItemStack(Material.MAP);
        ItemMeta previewMeta = preview.getItemMeta();
        previewMeta.setDisplayName("§7Pré-visualização");
        previewMeta.setLore(List.of("§8Coloque as runas para gerar uma magia."));
        preview.setItemMeta(previewMeta);
        inv.setItem(SLOT_PREVIEW, preview);

        // Botão Confirmar
        ItemStack confirmar = new ItemStack(Material.NETHER_STAR);
        ItemMeta confirmarMeta = confirmar.getItemMeta();
        confirmarMeta.setDisplayName("§a§lConfirmar Criação");
        confirmar.setItemMeta(confirmarMeta);
        inv.setItem(SLOT_CONFIRMAR, confirmar);

        player.openInventory(inv);
    }

    @EventHandler
    public void aoClicar(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(GUI_TITULO)) return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        PlayerData data = PlayerDataManager.get(p.getUniqueId());

        int slot = e.getRawSlot();

        // Feedback nas molduras
        if (slot >= 0 && slot < TAMANHO && !isSlotFuncional(slot)) {
            p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
            return;
        }

        if (slot == SLOT_CONFIRMAR) {
            List<Runa> runasSelecionadas = obterRunas(inv);
            if (runasSelecionadas.size() < 2) {
                p.sendMessage("§cVocê precisa de pelo menos 2 runas.");
                return;
            }

            Spell magia = SpellManager.criarMagia(runasSelecionadas);
            if (magia == null) {
                p.sendMessage("§cErro ao criar a magia.");
                return;
            }

            boolean existe = data.getMagias().stream()
                    .anyMatch(m -> m.getNome().equalsIgnoreCase(magia.getNome()));

            if (existe) {
                p.sendMessage("§cVocê já criou uma magia com esse nome!");
                return;
            }

            p.closeInventory();
            p.sendMessage("§eDigite o nome da sua nova magia no chat:");
            MagicNameInputListener.aguardandoNome.put(p.getUniqueId(), magia);
        } else {
            atualizarPreview(inv);
        }
    }

    private static boolean isSlotFuncional(int slot) {
        for (int s : SLOTS_RUNAS) if (s == slot) return true;
        return slot == SLOT_PREVIEW || slot == SLOT_CONFIRMAR;
    }

    private List<Runa> obterRunas(Inventory inv) {
        List<Runa> runas = new ArrayList<>();
        for (int slot : SLOTS_RUNAS) {
            ItemStack item = inv.getItem(slot);
            if (item == null || !item.hasItemMeta()) continue;

            for (Runa runa : RunaRegistry.getRunasRegistradas().values()) {
                if (runa.getItem().isSimilar(item)) {
                    runas.add(runa);
                }
            }
        }
        return runas;
    }

    private void atualizarPreview(Inventory inv) {
        List<Runa> runas = obterRunas(inv);
        if (runas.size() < 2) {
            ItemStack preview = new ItemStack(Material.MAP);
            ItemMeta meta = preview.getItemMeta();
            meta.setDisplayName("§7Pré-visualização");
            meta.setLore(List.of("§8Coloque as runas para gerar uma magia."));
            preview.setItemMeta(meta);
            inv.setItem(SLOT_PREVIEW, preview);
            return;
        }

        Spell magia = SpellManager.criarMagia(runas);
        if (magia == null) return;

        ItemStack preview = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = preview.getItemMeta();
        meta.setDisplayName("§d" + magia.getNome());
        List<String> lore = new ArrayList<>();
        lore.add("§7Composição:");
        for (Runa r : runas) {
            lore.add(" §f• " + r.getRaridade().getCor() + r.getNome());
        }
        lore.add("");
        lore.add("§7Custo estimado de mana: §b" + SpellExecutor.calcularCustoMana(magia));
        meta.setLore(lore);
        preview.setItemMeta(meta);

        inv.setItem(SLOT_PREVIEW, preview);
    }
}