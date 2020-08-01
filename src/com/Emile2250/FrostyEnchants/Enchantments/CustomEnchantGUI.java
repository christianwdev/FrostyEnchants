package com.Emile2250.FrostyEnchants.Enchantments;

import com.Emile2250.FrostyEnchants.Util.ChatUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CustomEnchantGUI {

    private static Inventory gui;

    public CustomEnchantGUI() {
        gui = Bukkit.createInventory(null, 45, ChatUtil.color("&8&l» &3&lFrosty &b&lEnchanter"));
    }

    public void add(Enchantment e) {

        ItemStack enchant = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = enchant.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', e.getName()));

        // Handles the lore, saying the costs and items it applies to
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Cost: " + e.getCost()));
        String items = ChatColor.translateAlternateColorCodes('&', "&7Applies to");
        for (String i : e.getItems()) {
            items += ChatUtil.color(" " + i);
        }
        lore.add(items);
        meta.setLore(lore);
        enchant.setItemMeta(meta);

        gui.setItem(e.getSlot(), enchant);
    }

    public void open(Player player) {
        player.openInventory(gui);
    }

    public static Inventory getInv() { return gui; }

}
