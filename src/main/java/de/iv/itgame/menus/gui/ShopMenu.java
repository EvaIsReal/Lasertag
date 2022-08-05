package de.iv.itgame.menus.gui;

import de.iv.itgame.core.Uni;
import de.iv.itgame.gameElements.cosmetics.Cosmetic;
import de.iv.itgame.gameElements.cosmetics.CosmeticHandler;
import de.iv.itgame.menus.Menu;
import de.iv.itgame.menus.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopMenu extends Menu {

    public ShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return Uni.color("&5S&dhop &8>> &8Coins: " + Uni.getPlayerBalance(playerMenuUtility.getOwner().getUniqueId().toString()));
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        var ref = new Object() {
            int i = 0;
        };
        CosmeticHandler.getCosmetics().forEach(c -> {
            if(ref.i <= 27) {
                inventory.setItem(ref.i, c.shopIcon());
                ref.i++;
            }
        });

    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
