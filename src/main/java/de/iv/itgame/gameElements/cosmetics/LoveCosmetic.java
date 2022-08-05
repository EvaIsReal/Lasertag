package de.iv.itgame.gameElements.cosmetics;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;
import de.iv.itgame.utils.CosmeticExecutionPoint;
import de.iv.itgame.utils.CosmeticType;
import de.iv.itgame.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class LoveCosmetic extends Cosmetic{


    @Override
    public int price() {
        return 10;
    }

    @Override
    public String identifier() {
        return "LoveCosmetic";
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public ItemStack shopIcon() {
        return new ItemBuilder(Material.POPPY).setName(Uni.color("&aLove")).setLore(
                "",
                Uni.color("&6Effekt: True Love"),
                Uni.color("&7Zeige wahre Liebe mithilfe von Herzpartikeln"),
                "",
                Uni.color("&a&lGEWÖHNLICH"),
                //Uni.color(""),
                Uni.color("&8&m                               "),
                Uni.color("&7Preis: &6" + price()),
                Uni.color("&8Ausgeführt nach: &bkill"),
                ""
        ).build();
    }

    @Override
    public ItemStack invIcon() {
        return new ItemBuilder(Material.POPPY).setName(Uni.color("&cLove")).setLore(
                "",
                Uni.color("&6Effekt: True Love"),
                Uni.color("&7Zeige wahre Liebe mithilfe von"),
                Uni.color("&7Herzpartikeln bei einem Kill."),
                "",
                Uni.color("&8Status: &roff")
        ).build();
    }

    @Override
    public CosmeticType type() {
        return CosmeticType.PARTICLE;
    }

    @Override
    public String getItemIdentifier() {
        return identifier();
    }

    @Override
    public ItemStack getIcon() {
        return invIcon();
    }

    @Override
    public void executeEffect() {
        Bukkit.getPluginManager().registerEvent(PlayerDeathEvent.class, new Listener() {}, EventPriority.NORMAL, (listener, event) -> {
            PlayerDeathEvent e = (PlayerDeathEvent) event;
            Player t = e.getEntity();
            Location loc = t.getLocation();

            loc.getWorld().spawnParticle(Particle.HEART, loc, 5, 0.5, 0.5, 0.5);

        }, Main.getInstance());
    }

    @Override
    public CosmeticExecutionPoint executionPoint() {
        return CosmeticExecutionPoint.DEATH;
    }
}
