package de.iv.itgame.data;

import com.google.gson.Gson;
import de.iv.itgame.gameElements.InventoryItem;
import de.iv.itgame.gameElements.cosmetics.Cosmetic;
import de.iv.itgame.gameElements.guns.LaserGun;
import de.iv.itgame.players.ServerPlayer;
import de.iv.itgame.sql.SQLite;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerData {

    private ServerPlayer player;
    private Class<? extends Cosmetic> cosmeticClass;
    private Class<? extends LaserGun> gunClass;
    private InventoryItem[] inventoryItems;

    private String[] inventoryItemData;

    public PlayerData(ServerPlayer player) {
        this.player = player;
    }


    /**
     *
     * @param player - The player this PlayerData is respective to
     * @param cosmeticClass - Class of the applied cosmetic
     * @param gunClass - Class of the selected gun
     */
    public PlayerData(ServerPlayer player, Class<? extends Cosmetic> cosmeticClass, Class<? extends LaserGun> gunClass) {
        this.player = player;
        //the cosmetic the player has selected
        this.cosmeticClass = cosmeticClass;
        //the gun the player has selected
        this.gunClass = gunClass;
    }

    public void store() {
        Gson gson = new Gson();
        String storable = gson.toJson(toMap());
        SQLite.update("UPDATE playerData SET data = '"+storable+"' WHERE uuid = '"+player.getPlayer().getUniqueId().toString()+"'");
    }

    public HashMap<?, ?> toMap() {
            String gunName = gunClass.getName();

            String cosName = cosmeticClass.getName();

            HashMap<java.lang.String, Object> map = new HashMap<>();
            map.put("gun", gunName);
            map.put("applied_cosmetic", cosName);
            map.put("inventory_items", inventoryItemData);

            return map;
    }

    public InventoryItem[] getInventoryItems() {
        ResultSet rs = SQLite.query("SELECT data FROM playerData WHERE uuid = '"+player.getPlayer().getUniqueId().toString()+"'");
        try {
            if(rs.next()) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public <T extends Cosmetic> T getCosmetic() {
        try {
            return (T) cosmeticClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <G extends LaserGun> G getLaserGun() {
        try {
            return (G) gunClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getInventoryItemData() {
        return inventoryItemData;
    }

    public void setInventoryItems(InventoryItem[] inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public void setInventoryItemData(String[] inventoryItems) {
        this.inventoryItemData = inventoryItems;
    }
}
