package RougelikeRPG;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.util.ArrayList;

public interface GameMap {
    void render(TextGraphics g);
    char getTile(int row, int col);
    int getMapWidth();
    int getMapHeight();
    ArrayList<Enemy> enemy_gen(int floor);
    void enemySpawn(GameMap map, Player player);
    ArrayList<Enemy> getEnemies();
}
