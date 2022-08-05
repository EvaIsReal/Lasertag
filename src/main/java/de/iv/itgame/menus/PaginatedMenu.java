package de.iv.itgame.menus;

import de.iv.itgame.core.Uni;
import de.iv.itgame.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class PaginatedMenu extends Menu{

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

    public void setMaxItemsPerPage(int maxItemsPerPage) {
        this.maxItemsPerPage = maxItemsPerPage;
    }


    protected int page = 0;

    protected int maxItemsPerPage = 28;

    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public void setBorderItems() {
        ItemStack left, right, close;

        right = new ItemBuilder(Material.DARK_OAK_BUTTON).setName(Uni.color("&anächste Seite")).build();
        left = new ItemBuilder(Material.DARK_OAK_BUTTON).setName(Uni.color("&aletzte Seite")).build();
        close = new ItemBuilder(Material.BARRIER).setName(Uni.color("&4schließen")).build();

        inventory.setItem(48, left);
        inventory.setItem(49, close);
        inventory.setItem(50, right);

        for(int i = 0; i < 10; i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
        inventory.setItem(17, super.FILLER_GLASS);
        inventory.setItem(18, super.FILLER_GLASS);
        inventory.setItem(26, super.FILLER_GLASS);
        inventory.setItem(27, super.FILLER_GLASS);
        inventory.setItem(35, super.FILLER_GLASS);
        inventory.setItem(36, super.FILLER_GLASS);

        for(int i = 44; i < 54; i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }


    }

}