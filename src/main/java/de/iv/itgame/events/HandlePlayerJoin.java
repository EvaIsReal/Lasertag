package de.iv.itgame.events;

import de.iv.itgame.core.Uni;
import de.iv.itgame.data.PlayerData;
import de.iv.itgame.gameElements.cosmetics.LoveCosmetic;
import de.iv.itgame.gameElements.guns.DefaultGun;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HandlePlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        //Player doesn't exist
        if (Uni.getServerPlayers().stream().filter(k -> k.getPlayer().equals(p)).collect(Collectors.toList()).isEmpty()) {
            System.out.println("PLAYER NICHT VORHANDEN");
            Uni.createServerPlayer(p); //Therefore, create new player
            PlayerData data = new PlayerData(Uni.getServerPlayer(p.getUniqueId().toString()), LoveCosmetic.class, DefaultGun.class);
            ArrayList<String> items = new ArrayList<>();
            items.add(new LoveCosmetic().getItemIdentifier());
            items.add(new DefaultGun().getItemIdentifier());

            data.setInventoryItemData(items.toArray(new String[0]));
            data.store();
        } else {
            //Player does already exist
            PlayerData data = Uni.getPersistentPlayerData(p.getUniqueId().toString());
        }
    }

}
