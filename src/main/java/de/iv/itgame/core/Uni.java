package de.iv.itgame.core;

import com.google.gson.Gson;
import de.iv.itgame.data.PlayerData;
import de.iv.itgame.gameElements.InventoryItem;
import de.iv.itgame.gameElements.cosmetics.Cosmetic;
import de.iv.itgame.gameElements.cosmetics.CosmeticHandler;
import de.iv.itgame.gameElements.guns.GunManager;
import de.iv.itgame.gameElements.guns.LaserGun;
import de.iv.itgame.players.ServerPlayer;
import de.iv.itgame.sql.Mysql;
import de.iv.itgame.sql.SQLite;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Uni {

    public static final java.lang.String PREFIX = "&5&lL&d&laser&5&lT&d&lag &8| &7";
    public static final java.lang.String ERROR = "&4&lERROR &8| &c";

    public static void conMsg(java.lang.String msg) {
        Bukkit.getConsoleSender().sendMessage(Uni.color(msg));
    }

    public static java.lang.String color(java.lang.String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void sendActionbar(Player p, java.lang.String text) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
    }

    public static void sendColoredActionbar(Player p, java.lang.String text) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(color(text)));
    }


    public static double round(double x, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(x));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void logInfo(java.lang.String str) {
        Main.getInstance().getLogger().info(str);
    }

    public static void raycast(PlayerInteractEvent e, Color color, float size, double damage) {
        Location start = e.getPlayer().getEyeLocation();
        Vector direction = start.getDirection();
        e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 2);
        for(double i = 1; i < 30; i += 0.15) {
            direction.multiply(i);
            start.add(direction);
            if(start.getBlock().getType().isOccluding()) return;
            for (Entity entity : e.getPlayer().getWorld().getNearbyEntities(start, 0, 0, 0)) {
                if(entity instanceof Player p) {
                    if(p != e.getPlayer()) {
                        Main.getInstance().getServer().getPluginManager().callEvent(new EntityDamageByEntityEvent(e.getPlayer(), p, EntityDamageEvent.DamageCause.CUSTOM, damage));
                    }
                }
                return;
            }


            Particle.DustOptions dustOptions = new Particle.DustOptions(color, size);
            e.getPlayer().getWorld().spawnParticle(Particle.REDSTONE, start, 10, 0, 0, 0, dustOptions);
            start.subtract(direction);
            direction.normalize();
        }
    }

    public static void raycast(PlayerInteractEvent e, Particle particle, int count, double speed, double damage) {
        Location start = e.getPlayer().getEyeLocation();
        Vector direction = start.getDirection();
        e.getPlayer().playSound(e.getPlayer(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 2);
        for(double i = 1; i < 30; i += 0.25) {
            direction.multiply(i);
            start.add(direction);
            if(start.getBlock().getType().isOccluding()) return;
            for (Entity entity : e.getPlayer().getWorld().getNearbyEntities(start, 0, 0, 0)) {
                if(entity instanceof Player p) {
                    if(p != e.getPlayer()) {
                        Main.getInstance().getServer().getPluginManager().callEvent(new EntityDamageByEntityEvent(e.getPlayer(), p, EntityDamageEvent.DamageCause.CUSTOM, damage));
                    }
                }
                return;
            }

            e.getPlayer().getWorld().spawnParticle(particle, start, count, 0, 0, 0, speed, null, true);
            start.subtract(direction);
            direction.normalize();
        }
    }

    /**
     * Spawn a Sphere at a given {@link Location}. The radius and height of the sphere is by default a value between 1 <br>
     * and {@code Math#Pi}. Use the {@code radiusAmplifier} to change the sphere's radius. <br>
     *
     * @param loc - The {@link Location} the particles are spawned
     * @param radiusAmplifier - A value that manipulates the initial radius of the sphere
     * @param particle - The particle the sphere is made of
     * @param count - The amount of particles spawned on each place
     * @param speed - the speed of the particles
     */

    public static void particleSphere(Location loc, int circleCount, int radiusAmplifier, Particle particle, int count, double speed) {
        for (double i = 0; i <= Math.PI; i += Math.PI / circleCount) {
            //let y = height
            double y = Math.cos(i)*radiusAmplifier;
            //let r = radius
            double r = Math.sin(i)*radiusAmplifier;
            /*
            We can use the cosine of i to get a value that represents our sphere's height, since the cosine of a real
            value x is just the sine of x+pi/2. That means that the highest point of the sphere has the lowest radius, in fact zero.
             */
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / circleCount) {
                double x = Math.cos(a) * r;
                double z = Math.sin(a) * r;
                loc.add(x, y, z);
                loc.getWorld().spawnParticle(particle, loc, 1, 0, 0, 0, speed);
                loc.subtract(x, y, z);
            }
        }
    }

    /**
     * Creates a cylinder made out of particles. Every length is calculated in blocks.
     *
     * @param loc - location of the cylinders base
     * @param radius - the cylinders radius
     * @param ppc - amount of particles displayed in each circle [particles per circle]
     * @param height - the height of the cylinders head
     * @param circleAmount - amount of circles between head and base
     * @param particle - the displayed particle
     * @param count - the amount of particles spawned at once
     */
    public static void particleCylinder(Location loc, double radius, int ppc, double height, int circleAmount, Particle particle, int count) {
        // 2, 0.2 => 10/1
        for(double k = 0; k <= height; k += height/circleAmount) {
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / ppc) {
                double x = (Math.cos(a) + 0.05) *radius;
                double z = Math.sin(a) *radius;
                loc.add(x, k, z);
                loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, count, 0, 0, 0, 1);
                loc.subtract(x, k, z);
            }

        }
    }

    /**
     * Creates a cylinder made out of particles. Every length is calculated in blocks.
     *
     * @param loc - location of the cylinders base
     * @param radius - the cylinders radius
     * @param ppc - amount of particles displayed in each circle [particles per circle]
     * @param height - the height of the cylinders head
     * @param circleAmount - amount of circles between head and base
     * @param dustOptions - options for dust particle
     * @param count - the amount of particles spawned at once
     */
    public static void particleCylinder(Location loc, double radius, int ppc, double height, int circleAmount, int count, Particle.DustOptions dustOptions) {
        // 2, 0.2 => 10/1
        for(double k = 0; k <= height; k += height/circleAmount) {
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / ppc) {
                double x = (Math.cos(a) + 0.05) *radius;
                double z = Math.sin(a) *radius;
                loc.add(x, k, z);
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc, count, 0, 0, 0, 1, dustOptions);
                loc.subtract(x, k, z);
            }

        }
    }

    public static PlayerData getPersistentPlayerData(java.lang.String uuid) {
        ResultSet rs = SQLite.query("SELECT data FROM playerData WHERE uuid = '"+uuid+"'");
        try {
            if(rs.next()) {
                java.lang.String jsonData = rs.getString("data");
                HashMap<String, Object> map = new Gson().fromJson(jsonData, HashMap.class);
                Class<? extends LaserGun> gunClass = (Class<? extends LaserGun>) Class.forName((String) map.get("gun"));
                Class<? extends Cosmetic> cosClass = (Class<? extends Cosmetic>) Class.forName((String) map.get("applied_cosmetic"));

                String arrayInJSON = map.get("inventory_items").toString();
                //arrayInJSON -> String[]
                String[] items =  new Gson().fromJson(arrayInJSON, String[].class);

                PlayerData data = new PlayerData(getServerPlayer(uuid), cosClass, gunClass);
                data.setInventoryItemData(items);
                return data;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setPlayerBalance(java.lang.String uuid, double bal) {
        SQLite.update("UPDATE playerData SET balance = '"+bal+"' WHERE uuid = '"+uuid+"'");
    }

    public static double getPlayerBalance(java.lang.String uuid) {
        ResultSet rs = SQLite.query("SELECT balance FROM playerData WHERE uuid = '"+uuid+"'");
        try {
            if(rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                rs.close();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static ArrayList<InventoryItem> getExistingInventoryItems() {
        Collection<ArrayList<? extends InventoryItem>> collection = new ArrayList<>();
        collection.add(GunManager.getLaserGuns());
        collection.add(CosmeticHandler.getCosmetics());

        ArrayList<InventoryItem> inventoryItems = new ArrayList<>();
        for (ArrayList<? extends InventoryItem> items : collection) {
            inventoryItems.addAll(items);
        }
        return inventoryItems;
    }

    public static String[] inventoryItemsToString(InventoryItem[] inventoryItems) {
        String[] ret = new String[inventoryItems.length];
        for (int i = 0; i < inventoryItems.length; i++) {
            ret[i] = inventoryItems[i].getItemIdentifier();
        }
        return ret;
    }

    public static void setPlayerInventoryItems(String uuid, InventoryItem[] inventoryItems) {
        PlayerData data = getPersistentPlayerData(uuid);
        data.setInventoryItemData(inventoryItemsToString(inventoryItems));
        data.store();
    }

    public static void addPlayerInventoryItem(String uuid, InventoryItem item) {
        InventoryItem[] inventoryItems = getPlayerInventoryItems(uuid);
        InventoryItem[] arr = new InventoryItem[inventoryItems.length+1];
        for (int i = 0; i < arr.length; i++) inventoryItems[i] = arr[i];
        item = arr[inventoryItems.length+1];

        setPlayerInventoryItems(uuid, arr);
    }

    public static InventoryItem getInventoryItemFromId(String identifier) {
        for (InventoryItem item : getExistingInventoryItems()) {
            if(item.getItemIdentifier().equals(identifier)) return item;
        }
        return null;
    }

    public static InventoryItem[] getPlayerInventoryItems(String uuid) {
        ArrayList<InventoryItem> items = new ArrayList<>();
        for (String datum : getPersistentPlayerData(uuid).getInventoryItemData()) {
            for (InventoryItem item : getExistingInventoryItems()) {
                if(datum.equals(item.getItemIdentifier())) items.add(item);
            }
        }
        InventoryItem[] ret = new InventoryItem[items.size()];
        return items.toArray(ret);
    }

    public static ServerPlayer getServerPlayer(java.lang.String uuid) {

        try {
            PreparedStatement ps = SQLite.getCon().prepareStatement("SELECT * FROM playerData WHERE uuid = ?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                java.lang.String playerUUID = rs.getString("uuid");
                double bal = rs.getDouble("balance");
                double score = rs.getDouble("score");
                int level = rs.getInt("level");

                OfflinePlayer p = Arrays.stream(Bukkit.getOfflinePlayers()).toList().stream().filter(k -> k.getUniqueId().toString().equals(uuid)).toList().get(0);
                return new ServerPlayer(p, bal, score, level);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void createServerPlayer(Player player) {
        SQLite.update("INSERT INTO playerData(uuid, balance, score, level) VALUES ('"+player.getUniqueId().toString()+"', '0.0', '0.0', '1')");
        player.sendMessage(Uni.color(Uni.PREFIX + "Willkommen auf &5&lL&d&laser&5&lT&d&lag"));
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }

    public static List<ServerPlayer> getServerPlayers() throws SQLException {
        List<ServerPlayer> serverPlayers = new ArrayList<>();
        ResultSet rs = SQLite.query("SELECT * FROM playerData");
        while (rs.next()) {
            java.lang.String uuid = rs.getString("uuid");
            double balance = rs.getDouble("score");
            double score = rs.getDouble("score");
            int level = rs.getInt("level");

            ServerPlayer player = new ServerPlayer(Bukkit.getPlayer(UUID.fromString(uuid)), balance, score, level);
            serverPlayers.add(player);
        }
        rs.close();
        return serverPlayers;
    }


    public static Player getRandomPlayerExclude(Player p) {
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        players.remove(p);
        return getRandomElement(players);
    }

    public static <T> T getRandomElement(Collection<T> coll) {
        int num = (int) (Math.random() * coll.size());
        for(T t: coll) if (--num < 0) return t;
        throw new AssertionError();
    }

    public static List<Player> getNearbyPlayers(Location loc, int size) {
        List<Player> players = new ArrayList<>();

        for(Player all : Bukkit.getOnlinePlayers()) {
            if(loc.distance(all.getLocation()) <= size) {
                players.add(all);
            }
        }
        return players;
    }



    public static class MysqlMethods {

        public static void createServerPlayer(Player player) {
            Mysql.update("INSERT INTO playerData(uuid, balance, score, level) VALUES = ('"+player.getName()+"', `0.0`, `0.0`, `1`)");
        }

        public static List<ServerPlayer> getServerPlayers() {
            List<ServerPlayer> serverPlayers = new ArrayList<>();
            ResultSet rs = Mysql.query("SELECT * FROM playerData");
            try {
                while (rs.next()) {
                    java.lang.String uuid = rs.getString("uuid");
                    double balance = rs.getDouble("score");
                    double score = rs.getDouble("score");
                    int level = rs.getInt("level");

                    ServerPlayer player = new ServerPlayer(Bukkit.getPlayer(UUID.fromString(uuid)), balance, score, level);
                    serverPlayers.add(player);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return serverPlayers;
        }
    }


}
