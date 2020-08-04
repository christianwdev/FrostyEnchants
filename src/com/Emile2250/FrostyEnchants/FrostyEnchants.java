package com.Emile2250.FrostyEnchants;

import com.Emile2250.FrostyEnchants.Commands.CommandHandler;
import com.Emile2250.FrostyEnchants.Enchantments.CustomEnchantGUI;
import com.Emile2250.FrostyEnchants.Enchantments.Enchantment;
import com.Emile2250.FrostyEnchants.Listeners.ApplyEffects;
import com.Emile2250.FrostyEnchants.Listeners.ApplyEnchant;
import com.Emile2250.FrostyEnchants.Listeners.GUIListener;
import com.Emile2250.FrostyEnchants.Util.ChatUtil;
import com.Emile2250.FrostyEnchants.Util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class FrostyEnchants extends JavaPlugin {

    private static FrostyEnchants instance;
    private ConfigUtil config;
    private CustomEnchantGUI gui;
    private ConfigUtil messages;
    private ArrayList<Enchantment> enchantments;

    public void onEnable() {

        // Variables
        instance = this;
        enchantments = new ArrayList<>();

        // Configurations

        config = new ConfigUtil("enchants.yml");
        messages = new ConfigUtil("messages.yml");

        // Commands

        getCommand("ce").setExecutor(new CommandHandler());

        // Events
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new ApplyEnchant(), this);
        Bukkit.getPluginManager().registerEvents(new ApplyEffects(), this);

        // Enchantments
        gui = new CustomEnchantGUI();
        loadEnchantments();

        for (Enchantment e : enchantments) { gui.add(e); }
    }

    public static FrostyEnchants getInstance() {
        return instance;
    }

    public ArrayList<Enchantment> getEnchants() { return enchantments; }

    public ConfigUtil config() { return config; }

    public ConfigUtil getMessages() { return messages; }

    public CustomEnchantGUI customEnchantGUI() { return gui; }

    public Enchantment findByItem(ItemStack i) {
        int amount = i.getAmount(); // Stores amount to make sure not to delete any items
        i.setAmount(1); // Changes amounts to compare objects
        for (Enchantment e : enchantments) {
            if (e.getItem().equals(i)) {
                i.setAmount(amount); // Resets amount back to normal
                return e;
            }
        }

        i.setAmount(amount);
        return null;
    }

    public ArrayList<Enchantment> findByLore(List<String> lore) {
        if (lore != null) {
            ArrayList<Enchantment> enchants = new ArrayList<>();
            for (Enchantment e : enchantments) {
                for (String l : lore) {
                    if (l.equalsIgnoreCase(e.getName())) {
                        enchants.add(e);
                        break;
                    }
                }
            }
            return enchants;
        }

        return null;
    }

    private void loadEnchantments() {
        if (config.isSection("enchants")) {
            for (String enchant : config.getSection("enchants").getKeys(false)) {
                String path = "enchants." + enchant + ".";
                String name = ChatUtil.color(config.getString(path + "name"));
                String color = config.getString(ChatUtil.color("enchant-color"));
                PotionEffectType effect = PotionEffectType.getByName(config.getString(path + "effect"));
                int level = config.getInt(path + "level");
                int slot = config.getInt(path + "slot");
                int cost = config.getInt(path + "cost");

                ArrayList<String> lore = new ArrayList<>();

                for (String l : config.getStringList(path + "lore")) {
                    lore.add(ChatUtil.color(l));
                }

                ArrayList<String> items = new ArrayList<>(config.getStringList(path + "items"));

                enchantments.add(new Enchantment(name, lore, slot, cost, level, color, effect, items));
            }
        }
    }
}
