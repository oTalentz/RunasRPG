package org.runasrpg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RunasHelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("§6[RunasRPG] §eComandos disponíveis:");

        if (sender.hasPermission("runasrpg.magia"))
            sender.sendMessage(" §f/magia §7- Abrir criação de magias");

        if (sender.hasPermission("runasrpg.cast"))
            sender.sendMessage(" §f/cast <nome> §7- Lançar uma magia");

        if (sender.hasPermission("runasrpg.castgui"))
            sender.sendMessage(" §f/castgui §7- GUI para lançar magias");

        if (sender.hasPermission("runasrpg.minhasmagias"))
            sender.sendMessage(" §f/minhasmagias §7- Ver suas magias");

        sender.sendMessage(" §f/listarrunas §7- Ver todas as runas disponíveis");
        sender.sendMessage(" §f/desbloquearruna <id> §7- Desbloquear runa manualmente");

        if (sender.hasPermission("runasrpg.admin.grimorio"))
            sender.sendMessage(" §f/grimorio <id> §7- Dar grimório de runa");

        if (sender.hasPermission("runasrpg.admin.reload"))
            sender.sendMessage(" §f/reloadrunas §7- Recarregar runas do sistema");

        return true;
    }
}
