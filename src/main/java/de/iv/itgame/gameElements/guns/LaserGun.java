package de.iv.itgame.gameElements.guns;

import de.iv.itgame.gameElements.InventoryItem;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public abstract class LaserGun implements InventoryItem {

    public LaserGun() {

    }

    //name != item display name!!!
    public abstract java.lang.String name();
    //The item for the PlayerInteractEvent to listen
    public abstract ItemStack item();
    //The damage it does
    public abstract double damage();
    //The price in the itemshop
    public abstract double shopPrice();
    //Do things when fired
    public abstract void fire(PlayerInteractEvent e);
    public abstract void performAbility(PlayerInteractEvent e);
    public abstract long abilityCooldownTicks();



}
