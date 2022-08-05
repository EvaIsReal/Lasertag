package de.iv.itgame.sql;

import de.iv.itgame.config.DataManager;
import de.iv.itgame.config.FileManager;
import de.iv.itgame.core.Uni;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.sql.*;

public class Mysql {

    private static Connection con;
    private static Statement s;
    private String host, db, user, password;
    private int port;
    private FileConfiguration cfg;

    {
        try {
            cfg = FileManager.getConfig("mysql.yml");
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Mysql() {
        host = cfg.getString("host");
        db = cfg.getString("database");
        user = cfg.getString("user");
        password = cfg.getString("password");
        port = cfg.getInt("port");
    }

    public Mysql connect() {
        con = null;
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?autoReconnect=true";
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("CONNECTED TO MYSQL DATABASE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Mysql disconnect() {
        if(con != null) {
            try {
                con.close();
                System.out.println("DISCONNECTED FROM MYSQL DATABASE");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public static void update(String sql) {
        try{
            s = con.createStatement();
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet query(String sql) {
        try {
            return s.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void init() {
        connect();
        update("CREATE TABLE IF NOT EXISTS `playerData`(" +
                "`uuid` VARCHAR NOT NULL," +
                "`balance` DOUBLE," +
                "`score` DOUBLE," +
                "`level` INT" +
                ");");
        Uni.MysqlMethods.getServerPlayers().forEach(k -> System.out.println(k.getPlayer().getName()));
    }


    public String getHost() {
        return host;
    }

    public String getDb() {
        return db;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }
}
