package de.iv.itgame.mechanics;

import de.iv.itgame.core.Uni;

public class Leveling {

    /**
     * @param level - The required Level
     * @return - needed xp to achieve said level
     */
    public static double getRequirement(int level) {
        return Uni.round(1.2 * (level^2 / 5) + 10, 4);
    }




}
