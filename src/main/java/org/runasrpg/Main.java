package org.runasrpg;

import org.bukkit.plugin.java.JavaPlugin;
import org.runasrpg.command.*;
import org.runasrpg.gui.*;
import org.runasrpg.magic.*;
import org.runasrpg.grimorio.*;
import org.runasrpg.player.PlayerDataManager;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Registrar runas
        RunaRegistry.registrarRunas();

        // Registrar eventos
        getServer().getPluginManager().registerEvents(new RunaCraftGUI(), this);
        getServer().getPluginManager().registerEvents(new MagicNameInputListener(), this);
        getServer().getPluginManager().registerEvents(new GrimoireUseListener(), this);
        getServer().getPluginManager().registerEvents(new CastGUI(), this);
        getServer().getPluginManager().registerEvents(new MinhasMagiasGUI(), this);
        getServer().getPluginManager().registerEvents(new MagiaAplicadorListener(), this);
        getServer().getPluginManager().registerEvents(new MagiaEfeitoCombateListener(), this);
        getServer().getPluginManager().registerEvents(new LivroDeMagiaBigornaListener(), this);

        // Registrar comandos
        getCommand("magia").setExecutor(new RunasCommand());
        getCommand("cast").setExecutor(new CastCommand());
        getCommand("castgui").setExecutor(new CastGUICommand());
        getCommand("minhasmagias").setExecutor(new MinhasMagiasCommand());
        getCommand("reloadrunas").setExecutor(new ReloadRunasCommand());
        getCommand("listarrunas").setExecutor(new ListarRunasCommand());
        getCommand("desbloquearruna").setExecutor(new DesbloquearRunaCommand());
        getCommand("grimorio").setExecutor(new GrimorioCommand());
        getCommand("runasrpg").setExecutor(new RunasHelpCommand());

        getLogger().info("§aRunasRPG iniciado com sucesso!");
    }

    @Override
    public void onDisable() {
        PlayerDataManager.salvarTudo();
        getLogger().info("§cRunasRPG desligado.");
    }
}
