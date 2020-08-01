package com.Emile2250.FrostyEnchants.Listeners;

import com.Emile2250.FrostyEnchants.Enchantments.CustomEnchantGUI;
import com.Emile2250.FrostyEnchants.Enchantments.Enchantment;
import com.Emile2250.FrostyEnchants.FrostyEnchants;
import com.Emile2250.FrostyEnchants.Util.ChatUtil;
import com.Emile2250.FrostyEnchants.Util.ExpFix;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    @EventHandler
    public void buy(InventoryClickEvent e) {
        if (e.getInventory().equals(CustomEnchantGUI.getInv())) {
            for (Enchantment enchant : FrostyEnchants.getInstance().getEnchants()) {
                if (enchant.getSlot() == e.getSlot()) {
                    Player player = (Player) e.getWhoClicked();
                    if (player.getTotalExperience() >= enchant.getCost() || player.getGameMode() == GameMode.CREATIVE) {
                        if (hasSpace(player)) {
                            ExpFix.setTotalExperience(player, player.getTotalExperience() - enchant.getCost());

                            player.getInventory().addItem(enchant.getItem());

                            ChatUtil.sendMessage(player, FrostyEnchants.getInstance().getMessages().getString("bought-enchant"));
                        } else {
                            ChatUtil.sendMessage(player, FrostyEnchants.getInstance().getMessages().getString("no-inv-space"));
                        }
                    } else {
                        ChatUtil.sendMessage(player, FrostyEnchants.getInstance().getMessages().getString("cant-afford"));
                    }
                }
            }
            e.setCancelled(true);
        }
    }

    private boolean hasSpace(Player player) {
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }

}
