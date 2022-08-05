package de.iv.itgame.gameElements.guns;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;
import de.iv.itgame.utils.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.time.LocalDateTime;

public class ChokeGun extends LaserGun{

    @Override
    public String name() {
        return "chokeGun";
    }

    @Override
    public ItemStack item() {
        return new ItemBuilder(Material.NETHERITE_HOE).setName(Uni.color("&4ChokeGun")).build();
    }

    @Override
    public double damage() {
        return 10.0;
    }

    @Override
    public double shopPrice() {
        return 0;
    }

    @Override
    public void fire(PlayerInteractEvent e) {
        Uni.raycast(e, Color.RED, 0.35F, damage());
    }

    private void choke(Player player) {
        Location loc = player.getEyeLocation();
        Vector v = loc.getDirection();

        for(double i = 1; i <= 5; i+=0.25) {
            v.multiply(i);
            loc.add(v);
            Uni.getNearbyPlayers(player.getEyeLocation(), 10).forEach(p -> {
                if(p != player) {
                    loc.setDirection(player.getEyeLocation().getDirection().multiply(-1));
                    loc.setY(player.getEyeLocation().getY());
                    Block b = loc.getBlock();
                    if(!loc.getBlock().getType().equals(Material.AIR)) {
                        loc.setX(b.getBoundingBox().getMinX());
                        loc.setZ(b.getBoundingBox().getMinZ());
                    }
                    p.teleport(loc);
                    //p.getEyeLocation().setDirection(player.getEyeLocation().getDirection().multiply(-1));
                }
            });
            loc.subtract(v);
            v.normalize();
        }
    }

    @Override
    public void performAbility(PlayerInteractEvent e) {
        e.getPlayer().playSound(e.getPlayer(), Sound.AMBIENT_CRIMSON_FOREST_MOOD, 1, 0);

        new BukkitRunnable() {
            int t = 0;
            @Override
            public void run() {
                if(t!=100) {
                    Uni.sendActionbar(e.getPlayer(), String.valueOf(t));
                    choke(e.getPlayer());
                    t++;
                } else this.cancel();

            }
        }.runTaskTimer(Main.getInstance(), 0, 1L);
    }

    @Override
    public long abilityCooldownTicks() {
        return 0;
    }

    @Override
    public ItemStack getIcon() {
        return item();
    }

    @Override
    public String getItemIdentifier() {
        return name();
    }
}
