package com.Emile2250.FrostyEnchants;

import com.Emile2250.FrostyEnchants.Commands.CommandHandler;
import com.Emile2250.FrostyEnchants.Enchantments.CustomEnchantGUI;
import com.Emile2250.FrostyEnchants.Enchantments.Enchantment;
import com.Emile2250.FrostyEnchants.Util.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class FrostyEnchants extends JavaPlugin {

    private static FrostyEnchants instance;
    private ConfigUtil config;
    private ArrayList<Enchantment> enchantments;

    public void onEnable() {

        // Variables
        instance = this;

        // Configurations

        config = new ConfigUtil("enchants.yml");
        loadEnchantments();

        // Commands

        getCommand("ce").setExecutor(new CommandHandler());

        // Events

        // Enchantments

        CustomEnchantGUI ceGUI = new CustomEnchantGUI();
        for (Enchantment e : enchantments) { ceGUI.add(e); }
    }

    public static FrostyEnchants getInstance() {
        return instance;
    }

    public ConfigUtil config() { return config; }

    private void loadEnchantments() {
        if (config.isSection("enchants")) {
            for (String enchant : config.getSection("enchants").getKeys(false)) {
                String path = "enchants." + enchant + ".";
                String name = config.getString(path + "name");
                PotionEffectType effect = PotionEffectType.getByName(config.getString(path + "effect"));
                int level = config.getInt(path + "level");
                int slot = config.getInt(path + "slot");
                int cost = config.getInt(path + "cost");

                ArrayList<String> lore = new ArrayList<>();

                for (String l : config.getStringList(path + "lore")) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', l));
                }

                ArrayList<Material> items = new ArrayList<>();

                for (String i : config.getStringList(path + "items")) {
                    items.add(Material.getMaterial(i));
                }

                enchantments.add(new Enchantment(name, lore, slot, cost, level, effect, items));
            }
        }
    }

}
