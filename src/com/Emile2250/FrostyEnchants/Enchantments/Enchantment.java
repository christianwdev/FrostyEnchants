package com.Emile2250.FrostyEnchants.Enchantments;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Enchantment {

    private String name;
    private String color;
    private ArrayList<String> lore;
    private int slot;
    private int cost;
    private int level;
    private String rawEffect;
    PotionEffectType effect;
    private ArrayList<String> items;
    private ItemStack item;

    public Enchantment(String n, ArrayList<String> lore, int s, int cost, int level, String color, String e, ArrayList<String> i) {
        name = n;
        this.lore = lore;
        slot = s;
        this.cost = cost;
        this.color = color;
        this.level = level;
        rawEffect = e;
        effect = PotionEffectType.getByName(e);
        items = i;

        // Setups the item to be bought

        item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
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

    public String getRawEffect() { return rawEffect; }

    public PotionEffectType getEffect() {
        return effect;
    }

    public ItemStack getItem() {
        return item;
    }

    public ArrayList<String> getItems() {
        return items;
    }
}
