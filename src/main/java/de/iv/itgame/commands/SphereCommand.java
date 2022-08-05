package de.iv.itgame.commands;

import de.iv.itgame.core.Uni;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.List;

public class SphereCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            if(args.length == 0) {
                Uni.particleSphere(p.getEyeLocation(), 20, 3, Particle.VILLAGER_HAPPY, 1, 1);
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
