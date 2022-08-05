package de.iv.itgame.gameElements.guns;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;
import de.iv.itgame.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AdminSword extends LaserGun{

    @Override
    public String name() {
        return "adminSword";
    }

    @Override
    public ItemStack item() {
        return new ItemBuilder(Material.NETHERITE_SWORD).setName(Uni.color("&4&lSword of the mighty admin")).build();
    }

    @Override
    public double damage() {
        return 40.0;
    }

    @Override
    public double shopPrice() {
        return -1;
    }

    @Override
    public void fire(PlayerInteractEvent e) {
        Uni.raycast(e, Particle.DRAGON_BREATH, 2, 0.1, damage());
    }

    @Override
    public void performAbility(PlayerInteractEvent e) {
        Collection<Entity> entities = new ArrayList<>(e.getPlayer().getWorld().getNearbyEntities(e.getPlayer().getLocation(), 300, 300, 300));
        entities.forEach(en -> en.teleport(e.getPlayer().getEyeLocation().add(0, 15, 0)));
            new BukkitRunnable() {
                int t = 0;
                @Override
                public void run() {
                    if (t > 30) this.cancel();

                    if(t == 20) {
                        for (Entity entity : entities) {
                            if (entity == e.getPlayer()) continue;
                            e.getPlayer().getWorld().spawnEntity(entity.getLocation(), EntityType.LIGHTNING);
                            e.getPlayer().getWorld().spawnEntity(entity.getLocation(), EntityType.LIGHTNING);
                        }
                    }
                    t++;
                }
            }.runTaskTimer(Main.getInstance(), 0, 1);

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
