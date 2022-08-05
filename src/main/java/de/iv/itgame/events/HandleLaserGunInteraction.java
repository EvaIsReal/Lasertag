package de.iv.itgame.events;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;
import de.iv.itgame.data.PlayerData;
import de.iv.itgame.gameElements.guns.DefaultGun;
import de.iv.itgame.gameElements.guns.GunManager;
import de.iv.itgame.gameElements.guns.LaserGun;
import de.iv.itgame.utils.CosmeticExecutionPoint;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;

public class HandleLaserGunInteraction implements Listener {



    @EventHandler
    public void onShoot(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if(e.hasItem()) {
            if((e.getAction().equals(Action.RIGHT_CLICK_AIR) | e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && e.getPlayer().isSneaking()) {
                if (!(e.getItem() == null) && e.getItem().hasItemMeta()) {
                    switch (e.getItem().getType()) {
                        case WOODEN_HOE -> GunManager.getLaserGun("DefaultGun").performAbility(e);
                        case NETHERITE_HOE -> GunManager.getLaserGun("chokeGun").performAbility(e);
                        case NETHERITE_SWORD -> GunManager.getLaserGun("adminSword").performAbility(e);
                    }
                }
            } else if(!(e.getItem() == null) && e.getItem().hasItemMeta() && (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) | e.getAction().equals(Action.RIGHT_CLICK_AIR))) {
                switch (e.getItem().getType()) {
                    case WOODEN_HOE -> GunManager.getLaserGun("DefaultGun").fire(e);
                    case NETHERITE_HOE -> GunManager.getLaserGun("chokeGun").fire(e);
                    case NETHERITE_SWORD -> GunManager.getLaserGun("adminSword").fire(e);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getCause().equals(EntityDamageEvent.DamageCause.CUSTOM)) {
            if(e.getEntity() instanceof Player p) {
                p.damage(e.getDamage(), e.getDamager());
                p.setVelocity(new Vector(0,0,0));
                PlayerData data = Uni.getPersistentPlayerData(p.getUniqueId().toString());

                if(data.getCosmetic().executionPoint().equals(CosmeticExecutionPoint.DEATH)) {
                    data.getCosmetic().executeEffect();
                }
            }
        }
    }

}
