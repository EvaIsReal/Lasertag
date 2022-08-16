package de.iv.itgame.commands;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;
import de.iv.itgame.mechanics.Leveling;
import de.iv.itgame.menus.MenuManager;
import de.iv.itgame.menus.gui.BaseMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Arrays;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player p && args[0].equals("inv")) {
            MenuManager.openMenu(BaseMenu.class, p);
            p.sendMessage(Arrays.toString(Uni.getPlayerInventoryItems(p.getUniqueId().toString())));
        }


        /*if(args[0].equals("inv") && args[1] != null && sender instanceof Player p) {
            p.sendMessage(String.valueOf(Uni.getPersistentPlayerData(Bukkit.getPlayer(args[1]).getUniqueId().toString()).toMap()));
        }*/

        return false;
    }
}
