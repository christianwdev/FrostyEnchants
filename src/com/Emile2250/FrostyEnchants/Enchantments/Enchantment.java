package com.Emile2250.FrostyEnchants.Enchantments;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Enchantment {

    private String name;
    private ArrayList<String> lore;
    private int slot;
    private int cost;
    private int level;
    PotionEffectType effect;
    private ArrayList<Material> items;

    public Enchantment(String n, ArrayList<String> lore, int s, int c, int level, PotionEffectType e, ArrayList<Material> i) {
        name = n;
        this.lore = lore;
        slot = s;
        cost = c;
        this.level = level;
        effect = e;
        items = i;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public int getCost() {
        return cost;
    }

    public int getSlot() {
        return slot;
    }

    public int getLevel() {
        return level;
    }

    public PotionEffectType getEffect() {
        return effect;
    }

    public ArrayList<Material> getItems() {
        return items;
    }
}
