package com.Emile2250.FrostyEnchants;

import com.Emile2250.FrostyEnchants.Util.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class FrostyEnchants extends JavaPlugin {

    private static FrostyEnchants instance;
    private static ConfigUtil enchants;

    public void onEnable() {

        // Variables
        instance = this;

        // Configurations
    }

    public static FrostyEnchants getInstance() {
        return instance;
    }

}
