package org.runasrpg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.runasrpg.magic.Runa;
import org.runasrpg.magic.RunaRegistry;

public class ListarRunasCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§eRunas disponíveis:");
        for (Runa runa : RunaRegistry.getRunasRegistradas().values()) {
            sender.sendMessage(" §7- §f" + runa.getId() + " §8(" + runa.getRaridade().getCor() + runa.getNome() + "§8)");
        }
        return true;
    }
}
