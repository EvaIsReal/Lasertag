package de.iv.itgame.menus;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuManager {

    private static final HashMap<String, Menu> REGISTERED_MENUS = new HashMap<>();
    private static final HashMap<Player, PlayerMenuUtility> pmuMap = new HashMap<>();
    private static final ArrayList<Class<? extends Menu>> HISTORY = new ArrayList<>();


    public static void openMenu(Class<? extends Menu> menuClass, Player p) {
        try {
            //Gets constructor from Menu class and parses in the needed PMU, calls open() method
            menuClass.getConstructor(PlayerMenuUtility.class).newInstance(getPlayerMenuUtility(p)).open();
            HISTORY.add(menuClass);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Menu getMenuByName(String paramName) {
        return REGISTERED_MENUS.get(paramName);
    }

    public static Class<? extends Menu> getPreviousMenu() {
        return HISTORY.get(HISTORY.size()-2);
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if(pmuMap.containsKey(p)) {
            return pmuMap.get(p);
        } else {
            playerMenuUtility = new PlayerMenuUtility(p);
            pmuMap.put(p, playerMenuUtility);
            return playerMenuUtility;
        }

    }


}