package org.runasrpg.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.runasrpg.magic.Spell;
import org.runasrpg.player.PlayerData;
import org.runasrpg.player.PlayerDataManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MagicNameInputListener implements Listener {

    public static final Map<UUID, Spell> aguardandoNome = new HashMap<>();

    @EventHandler
    public void aoDigitarNome(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!aguardandoNome.containsKey(p.getUniqueId())) return;

        e.setCancelled(true);

        String nome = e.getMessage();
        if (nome.length() < 3 || nome.length() > 20) {
            p.sendMessage("§cNome inválido. Use entre 3 e 20 caracteres.");
            return;
        }

        Spell magia = aguardandoNome.remove(p.getUniqueId());
        Spell magiaNomeada = new Spell(nome, magia.getRunas());
        PlayerData data = PlayerDataManager.get(p.getUniqueId());

        boolean existe = data.getMagias().stream().anyMatch(m -> m.getNome().equalsIgnoreCase(nome));
        if (existe) {
            p.sendMessage("§cVocê já tem uma magia com esse nome!");
            return;
        }

        data.adicionarMagia(magiaNomeada);
        PlayerDataManager.salvar(p.getUniqueId());
        p.sendMessage("§aMagia criada com sucesso como §e" + nome + "§a!");
    }
}