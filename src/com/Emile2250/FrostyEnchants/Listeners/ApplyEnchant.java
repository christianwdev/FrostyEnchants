package com.Emile2250.FrostyEnchants.Listeners;

import com.Emile2250.FrostyEnchants.Enchantments.Enchantment;
import com.Emile2250.FrostyEnchants.FrostyEnchants;
import com.Emile2250.FrostyEnchants.Util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ApplyEnchant implements Listener {

    @EventHandler
    @SuppressWarnings({"Warning", "deprecation"})
    public void onClick(InventoryClickEvent e) {
        if (itemsValid(e) && e.getClickedInventory().equals(e.getWhoClicked().getInventory()) && e.getSlot() < 36 && e.getSlot() >= 0) {
            Enchantment enchant = FrostyEnchants.getInstance().findByItem(e.getCursor());
            ArrayList<Enchantment> has = new ArrayList<>();
            if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLore()) {
                has.addAll(FrostyEnchants.getInstance().findByLore(e.getCurrentItem().getItemMeta().getLore()));
            }
            if (enchant != null && !has.contains(enchant)) { // Makes sure its a enchantment item and that the item doesnt contain it already.
                for (String applies : enchant.getItems()) { // Loops through items it can go onto
                    if (e.getCurrentItem().getType().name().contains(applies.toUpperCase())) { // Makes sure it applies to this item.
                        e.setCurrentItem(apply(enchant, e.getCurrentItem()));
                        ItemStack cursor = e.getCursor();
                        cursor.setAmount(cursor.getAmount() - 1);
                        e.setCursor(cursor);
                        e.setCancelled(true);
                        ChatUtil.sendMessage((Player) e.getWhoClicked(), FrostyEnchants.getInstance().getMessages().getString("applied-enchant"));
                        return;
                    }
                }

                if (!itsSelf(e.getCurrentItem(), e.getCursor())) {
                    ChatUtil.sendMessage((Player) e.getWhoClicked(), FrostyEnchants.getInstance().getMessages().getString("wrong-item-type"));
                }
                e.setCancelled(true);
            } else if (has.contains(enchant) && enchant != null) {
                ChatUtil.sendMessage((Player) e.getWhoClicked(), FrostyEnchants.getInstance().getMessages().getString("already-has-enchant"));
                e.setCancelled(true);
            }
        }
    }

    private boolean itemsValid(InventoryClickEvent e) {
        return e.getCurrentItem() != null && e.getCursor() != null && e.getCurrentItem().getType() != Material.AIR;
    }

    private boolean itsSelf(ItemStack clicked, ItemStack cursor) {
        int clickedAmount = clicked.getAmount();
        int cursorAmount = cursor.getAmount();
        clicked.setAmount(1);
        cursor.setAmount(1);
        boolean value = clicked.equals(cursor);

        clicked.setAmount(clickedAmount);
        cursor.setAmount(cursorAmount);
        return value;
    }

    private ItemStack apply(Enchantment enchant, ItemStack clicked) {
        ItemMeta meta = clicked.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        if (meta.getLore() != null) {
            lore.addAll(meta.getLore());
        }
        lore.add(enchant.getName());
        meta.setLore(lore);
        clicked.setItemMeta(meta);
        return clicked;
    }
}
