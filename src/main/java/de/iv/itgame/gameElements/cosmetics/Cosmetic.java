package de.iv.itgame.gameElements.cosmetics;

import de.iv.itgame.gameElements.InventoryItem;
import de.iv.itgame.utils.CosmeticExecutionPoint;
import de.iv.itgame.utils.CosmeticType;
import org.bukkit.inventory.ItemStack;

public abstract class Cosmetic implements InventoryItem {

    public abstract int price();
    //public abstract LaserGun[] applicableTo();
    public abstract java.lang.String identifier();
    public abstract ItemStack invIcon();
    public abstract ItemStack shopIcon();
    public abstract CosmeticType type();
    public abstract boolean isActive();
    public abstract void executeEffect();
    public abstract CosmeticExecutionPoint executionPoint();
}
