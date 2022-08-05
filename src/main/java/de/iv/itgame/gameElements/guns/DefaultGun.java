package de.iv.itgame.gameElements.guns;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;
import de.iv.itgame.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DefaultGun extends LaserGun{


    @Override
    public String name() {
        return "DefaultGun";
    }

    @Override
    public ItemStack item() {
        return new ItemBuilder(Material.WOODEN_HOE).setName(Uni.color("&aDefault Gun")).setLore(
                Uni.color("&7Damage: &c" + damage()),
                Uni.color("&7XP-Kosten: &b10.0"),
                "",
                Uni.color("&6FÄHIGKEIT: &6Tausch"),
                Uni.color("&7Tausche deine Position mit der eines "),
                Uni.color("&7zufälligen Spielers."),
                "",
                Uni.color("&e&lSHIFT+RECHTSKLICK"),
                Uni.color("&8Cooldown: &b3 sek."),
                "",
                Uni.color("&8Cosmetic:"),
                Uni.color("&cin development")
        ).build();
    }

    @Override
    public double damage() {
        return 5.0;
    }

    @Override
    public double shopPrice() {
        return 0;
    }

    @Override
    public void fire(PlayerInteractEvent e) {
        Uni.raycast(e, Color.GRAY, 0.35F, damage());
    }

    @Override
    public long abilityCooldownTicks() {
        return 30L;
    }

    @Override
    public void performAbility(PlayerInteractEvent e) {
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.FUCHSIA, 1);
        Location loc = e.getPlayer().getLocation();
        Player rdm = Uni.getRandomPlayerExclude(e.getPlayer());


        new BukkitRunnable() {
            int t = 1;
            double i = 0.1;
            @Override
            public void run() {
                if(t == 60) this.cancel();
                if(i < 2.5) Uni.particleCylinder(e.getPlayer().getLocation(), 1, 20, i, 15, 1, dustOptions);
                i+=0.25;
                t++;
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
        e.getPlayer().playSound(e.getPlayer(), Sound.BLOCK_BEACON_ACTIVATE,1, 2);
        e.getPlayer().teleport(rdm);

        new BukkitRunnable() {
            int t = 1;
            double i = 0.1;
            @Override
            public void run() {
                if(t == 60) this.cancel();
                if(i < 2.5) Uni.particleCylinder(rdm.getLocation(), 1, 20, i, 15, 1, dustOptions);
                i+=0.25;
                t++;
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
        rdm.playSound(e.getPlayer(), Sound.BLOCK_BEACON_ACTIVATE,1, 2);
        rdm.teleport(loc);


        /*e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        Uni.particleSphere(e.getPlayer().getEyeLocation(), 20, 2, Particle.VILLAGER_HAPPY, 2, 1);*/
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
