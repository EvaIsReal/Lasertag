package de.iv.itgame.events;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class HandlePlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        //Player leaves the server

    }

}
