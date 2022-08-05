package de.iv.itgame.gameElements;

import org.bukkit.inventory.ItemStack;

public interface InventoryItem {
    public abstract String getItemIdentifier();
    public abstract ItemStack getIcon();
}
