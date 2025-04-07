package org.runasrpg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.runasrpg.grimorio.GrimoireManager;
import org.runasrpg.magic.Runa;
import org.runasrpg.magic.RunaRegistry;

public class GrimorioCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar este comando.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cUso: /grimorio <id>");
            return true;
        }

        String id = args[0].toLowerCase();
        Runa runa = RunaRegistry.getRuna(id);

        if (runa == null) {
            sender.sendMessage("§cRuna com ID '" + id + "' não encontrada.");
            return true;
        }

        Player p = (Player) sender;
        p.getInventory().addItem(GrimoireManager.criarGrimorio(id, runa.getNome()));
        p.sendMessage("§aVocê recebeu o grimório da runa §f" + runa.getNome() + "§a!");
        return true;
    }
}
