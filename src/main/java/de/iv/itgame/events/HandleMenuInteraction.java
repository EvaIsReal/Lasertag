package de.iv.itgame.events;

import de.iv.itgame.menus.Menu;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * This class builds on mechanics developed by <a href="https://github.com/KodySimpson">https://github.com/KodySimpson</a>
 * <br>Sorry for not using your API. It constantly throws a ClassNotFoundException while starting. I've tried to fix it, but
 * I've lost hope eventually.
 */
public class HandleMenuInteraction implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if(holder instanceof Menu) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null) {
                return;
            }

            if(!e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                Player p = (Player) e.getWhoClicked();
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 2);
            }

            ((Menu) holder).handleMenu(e);
        }
    }


}
