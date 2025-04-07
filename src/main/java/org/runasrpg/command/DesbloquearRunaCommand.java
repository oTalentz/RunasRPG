package org.runasrpg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.runasrpg.magic.Runa;
import org.runasrpg.magic.RunaRegistry;
import org.runasrpg.player.PlayerData;
import org.runasrpg.player.PlayerDataManager;

public class DesbloquearRunaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar este comando.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUso: /desbloquearruna <id>");
            return true;
        }

        String id = args[0].toLowerCase();
        Runa runa = RunaRegistry.getRuna(id);

        if (runa == null) {
            sender.sendMessage("§cRuna com ID '" + id + "' não encontrada.");
            return true;
        }

        Player p = (Player) sender;
        PlayerData data = PlayerDataManager.get(p.getUniqueId());

        if (data.possuiRuna(id)) {
            p.sendMessage("§eVocê já possui essa runa.");
        } else {
            data.desbloquearRuna(id);
            p.sendMessage("§aRuna §f" + runa.getNome() + " §adesbloqueada com sucesso!");
        }
        return true;
    }
}
