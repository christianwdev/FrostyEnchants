package com.Emile2250.FrostyEnchants.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("ce")) {
            if (args.length == 0) {
                // Open GUI
            } else {
                // Do something here if theres more arguments (probably admin commands)
            }
        }

        return false;
    }
}
