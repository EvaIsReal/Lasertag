package de.iv.itgame.players;

import de.iv.itgame.core.Uni;
import de.iv.itgame.data.PlayerData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ServerPlayer  {

    private Player player;
    private double balance;
    private double score;
    private int level;
    private PlayerData data;
    private ArrayList<String> inventoryItems = new ArrayList<>();

    public ServerPlayer(Player player, double balance, double score, int level) {
        this.player = player;
        this.balance = balance;
        this.score = score;
        this.level = level;
        this.data = Uni.getPersistentPlayerData(player.getUniqueId().toString());
    }

    public ServerPlayer(OfflinePlayer player, double balance, double score, int level) {
        this.player = (Player) player;
        this.balance = balance;
        this.score = score;
        this.level = level;
    }

    public double getBalance() {
        return balance;
    }

    public PlayerData getData() {
        return data;
    }

    public void setData(PlayerData data) {
        this.data = data;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
