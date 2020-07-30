package com.Emile2250.FrostyEnchants.Util;

import com.Emile2250.FrostyEnchants.FrostyEnchants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {

    public static void broadcast(String msg) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(color(msg));
        }
    }

    public static void sendMessage(Player p, String msg) {
        p.sendMessage(color(msg));
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
