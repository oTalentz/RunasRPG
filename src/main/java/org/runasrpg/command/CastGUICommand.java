package org.runasrpg.command;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.runasrpg.gui.CastGUI;

public class CastGUICommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;
        CastGUI.abrir(p);
        return true;
    }
}
