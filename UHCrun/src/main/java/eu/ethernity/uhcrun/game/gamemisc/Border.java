package eu.ethernity.uhcrun.game.gamemisc;

import eu.ethernity.uhcrun.configs.GameConfig;
import eu.ethernity.uhcrun.map.Map;
import org.bukkit.WorldBorder;

public class Border {
    private final Map map;
    private final WorldBorder border;

    private static final int DEFAULT_SIZE = GameConfig.getConfig().getInt("border.starting-size");
    private static final int FINAL_SIZE = GameConfig.getConfig().getInt("border.final-size");
    private static final int SECS_TO_FINAL = GameConfig.getConfig().getInt("border.secs-to-final");

    public Border(Map map) {
        this.map = map;
        border = map.getWorld().getWorldBorder();
        border.setSize(DEFAULT_SIZE);
    }

    public double getSize() {
        return border.getSize();
    }

    public void start() {
        border.setCenter(map.getCenter());
    }

    public void shrink() {
        border.setSize(FINAL_SIZE, SECS_TO_FINAL);
    }

    public void reset() {
        border.reset();
    }
}
