package com.Emile2250.FrostyEnchants.Util;

import org.bukkit.entity.Player;

public class ExpFix {

    /*
     * This class is a straight copy from essentials source code. Bukkits exp part currently doesn't work correctly.
     */

    //This method is used to update both the recorded total experience and displayed total experience.
    //We reset both types to prevent issues.
    public static void setTotalExperience(Player player, int exp)
    {
        if (exp < 0)
        {
            throw new IllegalArgumentException("Experience is negative!");
        }
        player.setExp(0);
        player.setLevel(0);
        player.setTotalExperience(0);

        //This following code is technically redundant now, as bukkit now calulcates levels more or less correctly
        //At larger numbers however... player.getExp(3000), only seems to give 2999, putting the below calculations off.
        int amount = exp;
        while (amount > 0)
        {
            final int expToLevel = getExpAtLevel(player);
            amount -= expToLevel;
            if (amount >= 0)
            {
                // give until next level
                player.giveExp(expToLevel);
            }
            else
            {
                // give the rest
                amount += expToLevel;
                player.giveExp(amount);
                amount = 0;
            }
        }
    }

    private static int getExpAtLevel(Player player)
    {
        return getExpAtLevel(player.getLevel());
    }

    public static int getExpAtLevel(int level)
    {
        if (level > 29)
        {
            return 62 + (level - 30) * 7;
        }
        if (level > 15)
        {
            return 17 + (level - 15) * 3;
        }
        return 17;
    }

    public static int getExpToLevel(int level)
    {
        int currentLevel = 0;
        int exp = 0;

        while (currentLevel < level)
        {
            exp += getExpAtLevel(currentLevel);
            currentLevel++;
        }
        if (exp < 0)
        {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    //This method is required because the bukkit player.getTotalExperience() method, shows exp that has been 'spent'.
    //Without this people would be able to use exp and then still sell it.
    public static int getTotalExperience(Player player)
    {
        int exp = (int)Math.round(getExpAtLevel(player) * player.getExp());
        int currentLevel = player.getLevel();

        while (currentLevel > 0)
        {
            currentLevel--;
            exp += getExpAtLevel(currentLevel);
        }
        if (exp < 0)
        {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public static int getExpUntilNextLevel(Player player)
    {
        int exp = (int)Math.round(getExpAtLevel(player) * player.getExp());
        int nextLevel = player.getLevel();
        return getExpAtLevel(nextLevel) - exp;
    }
}