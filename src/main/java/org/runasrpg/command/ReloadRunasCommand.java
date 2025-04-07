package org.runasrpg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.runasrpg.magic.RunaRegistry;

public class ReloadRunasCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("runasrpg.admin.reload")) {
            sender.sendMessage("§cVocê não tem permissão para isso.");
            return true;
        }

        RunaRegistry.registrarRunas();
        sender.sendMessage("§aRunas recarregadas com sucesso!");
        return true;
    }
}
