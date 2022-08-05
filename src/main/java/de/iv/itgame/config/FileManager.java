package de.iv.itgame.config;

import de.iv.itgame.core.Main;
import de.iv.itgame.core.Uni;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class FileManager {

    private static ArrayList<ConfigurationFile> configs = new ArrayList<>();

    static LocalDate date = LocalDate.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    /**
     * Get current date and format it into CET format
     */
    public static String getLocalizedDate() {
        return formatter.format(date).replace(".", "_");
    }

    public static void setup() {
        //createPersistentDataFolder();
        //initLog();
        registerConfigs();
    }

    /*public static void createPersistentDataFolder() {
        File folder = new File(Main.getInstance().getDataFolder(), "persistentData");
        if(!folder.exists()) folder.mkdirs();
    }*/

    public static void registerConfigs() {
        ConfigurationFile settings = new ConfigurationFile("settings.yml", Main.getInstance().getDataFolder());
        configs.add(settings);

        ConfigurationFile mysql = new ConfigurationFile("mysql.yml", Main.getInstance().getDataFolder());
        configs.add(mysql);

    }
    //Not using it but too afraid to delete ,_,

    /*
    public static FileConfiguration getConfig(String fileName) {
        for (ConfigurationFile config : configs) {
            if(config.getSource().getName().equals(fileName)) {
                return config.toFileConfiguration();
            }
        }
        return null;
    }
     */

    public static FileConfiguration getConfig(String fileName) throws IOException, InvalidConfigurationException {
        FileConfiguration cfg;

        for(File file : Arrays.stream(Main.getInstance().getDataFolder().listFiles()).toList()) {
            if(file.getName().equals(fileName)) {
                cfg = YamlConfiguration.loadConfiguration(file);
                return cfg;
            }
        }
        return null;
    }


    public static File getSource(String path, String fileName) {
        return new File(path, fileName);
    }


    public static void save(String fileName) {
        FileConfiguration cfg;
        for(File file : Arrays.stream(Main.getInstance().getDataFolder().listFiles()).toList()) {
            if(file.getName().equals(fileName)) {
                cfg = YamlConfiguration.loadConfiguration(file);
                try {
                    cfg.save(file);
                    System.out.println("SAVED CONFIG " + file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void initLog() {
        Date date = new Date(System.currentTimeMillis());
        File logFile = new File(DataManager.SER_PATH, "db.log_" + getLocalizedDate());
        if (!logFile.exists()) {
            try {
                Scanner scanner = new Scanner(logFile);
                if(scanner.hasNext()) {
                    logFile.createNewFile();
                }
                Uni.logInfo("DB_LOG EMPTY, NOT CREATING LOG FILE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void dbLog(String sql) {
        File logFile = new File(DataManager.SER_PATH, "db.log_" + getLocalizedDate());
        try {
            FileWriter writer = new FileWriter(logFile);
            writer.write( "[" + getLocalizedDate() + "]: "+ sql + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public static ArrayList<ConfigurationFile> getConfigs() {
        return configs;
    }
}