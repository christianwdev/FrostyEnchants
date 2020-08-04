package com.Emile2250.FrostyEnchants.Listeners;

import com.Emile2250.FrostyEnchants.Enchantments.Enchantment;
import com.Emile2250.FrostyEnchants.FrostyEnchants;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public class ApplyEffects implements Listener {

    @EventHandler
    public void armorBreak(PlayerItemBreakEvent e) {
        if (e.getBrokenItem() != null && e.getBrokenItem().hasItemMeta() && e.getBrokenItem().getItemMeta().hasLore()) {
            removeEffects(e.getBrokenItem(), e.getPlayer());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                applyEffects(item, e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                removeEffects(item, e.getPlayer());
            }
        }
    }

    @EventHandler
    public void armorEquip(InventoryClickEvent e) {
        if (e.getCursor() != null) {
            if (e.getClickedInventory() == e.getWhoClicked().getInventory()) {
                if (e.getSlot() >= 36 && e.getSlot() <= 39) {
                    if (e.getCursor().getType() != Material.AIR) {
                        if (e.getCursor().hasItemMeta() && e.getCursor().getItemMeta().hasLore()) {
                            applyEffects(e.getCursor(), (Player) e.getWhoClicked());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void armorRemove(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            if (e.getClickedInventory() == e.getWhoClicked().getInventory()) {
                if (e.getSlot() >= 36 && e.getSlot() <= 39) {
                    if (e.getCurrentItem().getType() != Material.AIR) {
                        if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLore()) {
                            removeEffects(e.getCurrentItem(), (Player) e.getWhoClicked());
                        }
                    }
                }
            }
        }
    }

    public static void applyEffects(ItemStack item, Player p) {
        ArrayList<Enchantment> appliedEnchantments = FrostyEnchants.getInstance().findByLore(item.getItemMeta().getLore());
        for (Enchantment enchant : appliedEnchantments) {
            p.addPotionEffect(new PotionEffect(enchant.getEffect(), Integer.MAX_VALUE, enchant.getLevel(), false, true));
        }
    }

    public static void removeEffects(ItemStack item, Player p) {
        ArrayList<Enchantment> appliedEnchantments = FrostyEnchants.getInstance().findByLore(item.getItemMeta().getLore());
        for (Enchantment enchant : appliedEnchantments) {
            p.removePotionEffect(enchant.getEffect());
        }
    }
}
