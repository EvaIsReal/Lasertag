package de.iv.itgame.gameElements.guns;

import de.iv.itgame.core.Main;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class GunManager {

    private static ArrayList<LaserGun> laserGuns = new ArrayList<>();

    public GunManager() {
        laserGuns.add(new DefaultGun());
        laserGuns.add(new ChokeGun());
        laserGuns.add(new AdminSword());
    }

    public static LaserGun getLaserGun(String name) {
        for (LaserGun laserGun : laserGuns) {
            if(laserGun.name().equals(name)) return laserGun;
        }
        return null;
    }

    public static ArrayList<LaserGun> getLaserGuns() {
        return laserGuns;
    }
}
