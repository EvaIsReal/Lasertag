package de.iv.itgame.sql;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class SQLite {

    private static Connection con;
    private static Statement s;
    private static File dbFile = new File(Main.getInstance().getDataFolder() + "/data", "database.db");

    public static void connect() {
        con = null;

        if(!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String url = "jdbc:sqlite:" + dbFile.getPath();
        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Uni.logInfo("CONNECTED TO DATABASE");
    }

    public static void disconnect() {
        if(con != null) {
            try {
                con.close();
                Uni.logInfo("DISCONNECTED FROM DATABASE");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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


    public static void init() {
        connect();
        update("CREATE TABLE IF NOT EXISTS `playerData`(" +
                "`uuid` VARCHAR NOT NULL," +
                "`balance` DOUBLE," +
                "`score` DOUBLE," +
                "`level` INT," +
                "`data` VARCHAR," +
                "`inventory_items` VARCHAR" +
                ");");
    }

    public static Connection getCon() {
        return con;
    }
}
