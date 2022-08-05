package de.iv.itgame.core;

import de.iv.itgame.commands.DebugCommand;
import de.iv.itgame.commands.GiveGunCommand;
import de.iv.itgame.commands.SpawnCommand;
import de.iv.itgame.commands.SphereCommand;
import de.iv.itgame.config.DataManager;
import de.iv.itgame.config.FileManager;
import de.iv.itgame.data.PlayerData;
import de.iv.itgame.events.HandleLaserGunInteraction;
import de.iv.itgame.events.HandleMenuInteraction;
import de.iv.itgame.events.HandlePlayerJoin;
import de.iv.itgame.events.HandlePlayerQuit;
import de.iv.itgame.gameElements.cosmetics.CosmeticHandler;
import de.iv.itgame.gameElements.guns.GunManager;
import de.iv.itgame.menus.PlayerMenuUtility;
import de.iv.itgame.players.ServerPlayer;
import de.iv.itgame.sql.Mysql;
import de.iv.itgame.sql.SQLite;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;

public final class Main extends JavaPlugin {

    private static Main instance = null;
    private NamespacedKey gunKey;
    public String storageMethod;
    public FileConfiguration settings;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();




    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        gunKey = new NamespacedKey(instance, "laser_tag");
        GunManager gunManager = new GunManager();
        DataManager dataManager = new DataManager();
        CosmeticHandler cosmeticHandler = new CosmeticHandler();
        FileManager.setup();
        SQLite.init();


        registerCommands();
        registerListeners(getServer().getPluginManager());

        Uni.conMsg(Uni.PREFIX + "&aPlugin started successfully");
        getLogger().info("Started Plugin " + getDescription().getName() + " at Version " + getDescription().getVersion());
    }

    private void registerListeners(PluginManager pm) {
        pm.registerEvents(new HandleLaserGunInteraction(), this);
        pm.registerEvents(new HandlePlayerJoin(), instance);
        pm.registerEvents(new HandleMenuInteraction(), instance);
        pm.registerEvents(new HandlePlayerQuit(), instance);
    }

    private void registerCommands() {
        getCommand("givegun").setExecutor(new GiveGunCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("sphere").setExecutor(new SphereCommand());
        getCommand("debug").setExecutor(new DebugCommand());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        switch (storageMethod) {
            case "mysql":
                Mysql mysql = new Mysql().disconnect();
                break;
            case "sqlite":
                SQLite.disconnect();
                break;
        }
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (playerMenuUtilityMap.containsKey(p)) {
            return playerMenuUtilityMap.get(p);
        } else {
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);
            return playerMenuUtility;
        }

    }


    public static Main getInstance() {
        return instance;
    }

    public NamespacedKey getGunKey() {
        return gunKey;
    }

}
