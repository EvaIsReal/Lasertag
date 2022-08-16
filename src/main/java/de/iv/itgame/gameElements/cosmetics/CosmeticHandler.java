package de.iv.itgame.gameElements.cosmetics;

import java.util.ArrayList;

public class CosmeticHandler {

    private static ArrayList<Cosmetic> cosmetics = new ArrayList<>();

    public CosmeticHandler() {
        cosmetics.add(new LoveCosmetic());
        cosmetics.add(new DefaultCosmetic());
    }

    public static Cosmetic getCosmetic(String identifier) {
        for (Cosmetic cosmetic : cosmetics) {
            if(cosmetic.identifier().equals(identifier)) return cosmetic;
        }
        return null;
    }

    public static void apply(String uuid, Cosmetic cosmetic) {

    }

    public static ArrayList<Cosmetic> getCosmetics() {
        return cosmetics;
    }
}
