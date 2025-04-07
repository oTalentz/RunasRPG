package org.runasrpg.magic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class RunaRegistry {

    private static final Map<String, Runa> runas = new HashMap<>();
    private static final Set<String> runasOcultas = new HashSet<>();

    public static void registrarRunas() {
        runas.clear();
        runasOcultas.clear(); // opcional: limpa ocultas ao reiniciar

        adicionarRuna("fogo", "Runa de Fogo", RunaTipo.ELEMENTO, Material.BLAZE_POWDER, Raridade.COMUM);
        adicionarRuna("agua", "Runa de Água", RunaTipo.ELEMENTO, Material.PRISMARINE_CRYSTALS, Raridade.RARA);
        adicionarRuna("cura", "Runa de Cura", RunaTipo.MODIFICADOR, Material.GHAST_TEAR, Raridade.RARA);
        adicionarRuna("projetil", "Runa de Projétil", RunaTipo.FORMA, Material.ARROW, Raridade.COMUM);
        adicionarRuna("area", "Runa de Área", RunaTipo.FORMA, Material.FIREWORK_CHARGE, Raridade.EPICA);
        adicionarRuna("tempo", "Runa do Tempo", RunaTipo.MODIFICADOR, Material.WATCH, Raridade.LENDARIA);

        // Exemplo: oculta essa runa até desbloqueio por evento
        ocultarRuna("tempo");
    }

    private static void adicionarRuna(String id, String nome, RunaTipo tipo, Material material, Raridade raridade) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(raridade.getCor() + nome);
            item.setItemMeta(meta);
        }
        runas.put(id, new Runa(id, nome, tipo, item, raridade));
    }

    public static Runa getRuna(String id) {
        return runas.get(id);
    }

    public static Map<String, Runa> getRunasRegistradas() {
        return runas.entrySet().stream()
                .filter(entry -> !runasOcultas.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static void ocultarRuna(String id) {
        runasOcultas.add(id);
    }

    public static boolean isOculta(String id) {
        return runasOcultas.contains(id);
    }
}
