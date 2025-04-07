package org.runasrpg.player;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.runasrpg.Main;
import org.runasrpg.magic.Runa;
import org.runasrpg.magic.RunaRegistry;
import org.runasrpg.magic.Spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerDataManager {
    private static final Map<UUID, PlayerData> dados = new HashMap<>();

    public static PlayerData get(UUID uuid) {
        return dados.computeIfAbsent(uuid, PlayerDataManager::carregar);
    }

    public static void salvar(UUID uuid) {
        PlayerData data = dados.get(uuid);
        if (data == null) return;

        File file = new File(Main.getInstance().getDataFolder(), "data/" + uuid + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> lista = new ArrayList<>();
        for (Spell spell : data.getMagias()) {
            String ids = spell.getRunas().stream().map(Runa::getId).reduce((a, b) -> a + "," + b).orElse("");
            lista.add(spell.getNome() + ";" + ids);
        }

        config.set("magias", lista);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerData carregar(UUID uuid) {
        PlayerData data = new PlayerData(uuid);
        File file = new File(Main.getInstance().getDataFolder(), "data/" + uuid + ".yml");
        if (!file.exists()) return data;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<String> lista = config.getStringList("magias");

        for (String linha : lista) {
            String[] partes = linha.split(";");
            if (partes.length != 2) continue;
            String nome = partes[0];
            String[] ids = partes[1].split(",");
            List<Runa> runas = new ArrayList<>();
            for (String id : ids) {
                Runa r = RunaRegistry.getRuna(id);
                if (r != null) runas.add(r);
            }
            data.adicionarMagia(new Spell(nome, runas));
        }

        return data;
    }

    public static void salvarTudo() {
        for (UUID uuid : dados.keySet()) {
            salvar(uuid);
        }
    }
}