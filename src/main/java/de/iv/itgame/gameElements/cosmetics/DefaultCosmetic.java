package de.iv.itgame.gameElements.cosmetics;

import de.iv.itgame.core.Main;
import de.iv.itgame.utils.CosmeticExecutionPoint;
import de.iv.itgame.utils.CosmeticType;
import de.iv.itgame.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DefaultCosmetic extends Cosmetic {

    @Override
    public String getItemIdentifier() {
        return identifier();
    }

    @Override
    public ItemStack getIcon() {
        return invIcon();
    }

    @Override
    public int price() {
        return 0;
    }

    @Override
    public String identifier() {
        return "DefaultCosmetic";
    }

    @Override
    public ItemStack invIcon() {
        return new ItemBuilder(Material.EMERALD_BLOCK).setLore("Inv").setName("&5DEBUG").build();
    }

    @Override
    public ItemStack shopIcon() {
        return new ItemBuilder(Material.EMERALD_BLOCK).setLore("shop").setName("&5DEBUG").build();
    }

    @Override
    public CosmeticType type() {
        return CosmeticType.PARTICLE;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void executeEffect() {
        Bukkit.getPluginManager().registerEvent(PlayerDeathEvent.class, new Listener() {}, EventPriority.NORMAL, (listener, event) -> {
            PlayerDeathEvent e = (PlayerDeathEvent) event;
            Bukkit.broadcastMessage("DEBUG-COSMETIC");
        }, Main.getInstance());
    }

    @Override
    public CosmeticExecutionPoint executionPoint() {
        return null;
    }
}
