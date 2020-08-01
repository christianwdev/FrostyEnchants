package com.Emile2250.FrostyEnchants.Enchantments;

import com.Emile2250.FrostyEnchants.Util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Enchantment {

    private String name;
    private ArrayList<String> lore;
    private int slot;
    private int cost;
    private int level;
    PotionEffectType effect;
    private ArrayList<String> items;
    private ItemStack item;

    public Enchantment(String n, ArrayList<String> lore, int s, int c, int level, PotionEffectType e, ArrayList<String> i) {
        name = n;
        this.lore = lore;
        slot = s;
        cost = c;
        this.level = level;
        effect = e;
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
