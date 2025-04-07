package org.runasrpg.command;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.runasrpg.magic.Spell;
import org.runasrpg.magic.SpellExecutor;
import org.runasrpg.player.PlayerDataManager;

public class CastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;

        if (args.length == 0) {
            p.sendMessage("§cUse: /cast <nomeMagia>");
            return true;
        }

        String nome = args[0];
        for (Spell magia : PlayerDataManager.get(p.getUniqueId()).getMagias()) {
            if (magia.getNome().equalsIgnoreCase(nome)) {
                SpellExecutor.executar(magia, p);
                return true;
            }
        }

        p.sendMessage("§cVocê não conhece essa magia!");
        return true;
    }
}
