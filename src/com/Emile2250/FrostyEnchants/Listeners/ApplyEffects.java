package com.Emile2250.FrostyEnchants.Listeners;

import com.Emile2250.FrostyEnchants.Enchantments.Enchantment;
import com.Emile2250.FrostyEnchants.FrostyEnchants;
import net.minecraft.server.v1_8_R3.ItemArmor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ApplyEffects implements Listener {

    HashMap<UUID, Boolean> inInventory = new HashMap<>();

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
                inInventory.put(player.getUniqueId(), false);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                removeEffects(item, e.getPlayer());
                inInventory.remove(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void armorEquip(InventoryClickEvent e) {
        if (e.getCursor() != null) {
            if (e.getClickedInventory() == e.getWhoClicked().getInventory()) {
                if (e.getSlot() >= 36 && e.getSlot() <= 39) {
                    if (e.getCursor().getType() != Material.AIR) {
                        if (e.getCursor().hasItemMeta() && e.getCursor().getItemMeta().hasLore() && isArmor(e.getCursor())) {
                            applyEffects(e.getCursor(), (Player) e.getWhoClicked());
                        }
                    }
                } else if (e.isShiftClick()) {
                    String itemName = e.getCurrentItem().getType().name();
                    ItemStack[] armor = e.getWhoClicked().getInventory().getArmorContents();
                    if (e.getCurrentItem().getType() != Material.AIR && !inInventory.get(e.getWhoClicked().getUniqueId()) && isArmor(e.getCurrentItem())) {
                        if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLore()) {
                            if (itemName.contains("HELMET") && armor[3].getType() == Material.AIR) {
                                applyEffects(e.getCurrentItem(), (Player) e.getWhoClicked());
                            } else if (itemName.contains("CHESTPLATE") && armor[2].getType() == Material.AIR) {
                                applyEffects(e.getCurrentItem(), (Player) e.getWhoClicked());
                            } else if (itemName.contains("LEGGINGS") && armor[1].getType() == Material.AIR) {
                                applyEffects(e.getCurrentItem(), (Player) e.getWhoClicked());
                            } else if (itemName.contains("BOOTS") && armor[0].getType() == Material.AIR) {
                                applyEffects(e.getCurrentItem(), (Player) e.getWhoClicked());
                            }
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
                        if (isArmor(e.getCursor()) && (sameItemType(e.getCurrentItem(), e.getCursor()) || e.getCursor().getType() == Material.AIR)) {
                            if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLore()) {
                                removeEffects(e.getCurrentItem(), (Player) e.getWhoClicked());
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void openInv(InventoryOpenEvent e) {
        inInventory.put(e.getPlayer().getUniqueId(), true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        inInventory.put(e.getPlayer().getUniqueId(), false);
    }

    @EventHandler
    public void killMob(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player player = e.getEntity().getKiller();
            ItemStack item = player.getItemInHand();
            if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                applyEffects(item, player, e);
            }
        }
    }

    private boolean isArmor(ItemStack item) {
        return (CraftItemStack.asNMSCopy(item).getItem() instanceof ItemArmor);
    }

    private boolean sameItemType(ItemStack item1, ItemStack item2) {
        return item2.getType().name().contains(item1.getType().name().substring(item1.getType().name().length() - 4));
    }

    public static void applyEffects(ItemStack item, Player p) {
        applyEffects(item, p, null);
    }

    public static void applyEffects(ItemStack item, Player p, EntityDeathEvent e) {
        ArrayList<Enchantment> appliedEnchantments = FrostyEnchants.getInstance().findByLore(item.getItemMeta().getLore());
        for (Enchantment enchant : appliedEnchantments) {
            if (enchant.getEffect() != null) {
                p.addPotionEffect(new PotionEffect(enchant.getEffect(), Integer.MAX_VALUE, enchant.getLevel(), false, true));
            } else if (e != null) {
                if (enchant.getRawEffect().equalsIgnoreCase("XP")) {
                    e.setDroppedExp(e.getDroppedExp() * enchant.getLevel());
                }
            }
        }
    }

    public static void removeEffects(ItemStack item, Player p) {
        ArrayList<Enchantment> appliedEnchantments = FrostyEnchants.getInstance().findByLore(item.getItemMeta().getLore());
        for (Enchantment enchant : appliedEnchantments) {
            p.removePotionEffect(enchant.getEffect());
        }
    }
}
