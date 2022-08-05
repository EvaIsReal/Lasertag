package de.iv.itgame.menus.gui;

import de.iv.itgame.core.Uni;
import de.iv.itgame.gameElements.InventoryItem;
import de.iv.itgame.menus.Menu;
import de.iv.itgame.menus.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GameInventory extends Menu {

    public GameInventory(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return Uni.color("&5I&dnventar");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {
        InventoryItem[] items = Uni.getPlayerInventoryItems(playerMenuUtility.getOwner().getUniqueId().toString());
        for (InventoryItem item : items) {
            inventory.addItem(item.getIcon());
        }
        setFillerGlass();
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
