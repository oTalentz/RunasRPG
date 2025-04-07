package org.runasrpg.magic;

import org.bukkit.inventory.ItemStack;

public class Runa {
    private final String id;
    private final String nome;
    private final RunaTipo tipo;
    private final ItemStack item;
    private final Raridade raridade;

    public Runa(String id, String nome, RunaTipo tipo, ItemStack item, Raridade raridade) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.item = item;
        this.raridade = raridade;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public RunaTipo getTipo() {
        return tipo;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public Raridade getRaridade() {
        return raridade;
    }
}
