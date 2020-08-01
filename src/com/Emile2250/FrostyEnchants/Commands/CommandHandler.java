package com.Emile2250.FrostyEnchants.Commands;

import com.Emile2250.FrostyEnchants.FrostyEnchants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("ce")) {
                if (args.length == 0) {
                    FrostyEnchants.getInstance().customEnchantGUI().open(player);
                } else {
                    // Do something here if theres more arguments (probably admin commands)
                }
            }
        }

        return false;
    }
}
