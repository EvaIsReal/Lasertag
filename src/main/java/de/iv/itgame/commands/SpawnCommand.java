package de.iv.itgame.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SpawnCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            if(args.length == 2) {
                String name = args[0];
                String amount = args[1];
                for (int i = 0; i < Integer.parseInt(amount); i++) {
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.valueOf(name.toLowerCase()));
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 1) {
            return Arrays.stream(EntityType.values()).toList().stream()
                    .map(EntityType::toString)
                    .filter(s -> s.startsWith(args[0]))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
