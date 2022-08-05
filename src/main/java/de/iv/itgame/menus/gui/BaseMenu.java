package de.iv.itgame.menus.gui;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;
import de.iv.itgame.menus.Menu;
import de.iv.itgame.menus.MenuManager;
import de.iv.itgame.menus.PlayerMenuUtility;
import de.iv.itgame.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.ChatPaginator;

import javax.naming.Name;

public class BaseMenu extends Menu {

    public BaseMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return Uni.color("&5&lL&d&laser&5&lT&d&lag");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        try {
            switch (e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "gui"), PersistentDataType.STRING)) {
                case "shop" -> MenuManager.openMenu(ShopMenu.class, p);
                case "inv" -> MenuManager.openMenu(GameInventory.class, p);
            }
        } catch (Exception ignored) {}

    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        inventory.setItem(11, new ItemBuilder(Material.PLAYER_HEAD)
                .getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjk4YmM2M2YwNWY2Mzc4YmYyOWVmMTBlM2Q4MmFjYjNjZWI3M2E3MjBiZjgwZjMwYmM1NzZkMGFkOGM0MGNmYiJ9fX0=")
                .setName(Uni.color("&aInventar")).setLore(
                "",
                Uni.color("&7Sieh dir alle deine Gegenstände an")
        ).addToPDC(new NamespacedKey(Main.getInstance(), "gui"), "inv").build());

        inventory.setItem(15, new ItemBuilder(Material.PLAYER_HEAD)
                .getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjFiZjZhODM4NjYxZTE1ZGY2NjE5OTA5NDE2NWI3NTM4MzJjNzIzNDcxZDJiMjA0ZDRkNTA3NGFhNjA4Yzc4NCJ9fX0=")
                .setName(Uni.color("&aShop"))
                .setLore(
                        "",
                        Uni.color("&7Kaufe dir kosmetische Gegenstände,"),
                        Uni.color("&7um deinen Spielalltag bunter zu gestalten")
                ).addToPDC(new NamespacedKey(Main.getInstance(), "gui"), "shop")
                .build());

    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
