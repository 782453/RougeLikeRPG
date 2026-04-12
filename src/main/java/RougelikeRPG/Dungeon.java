package RougelikeRPG;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.util.Iterator;

public class Dungeon implements MapLoop {
    private static int floor = 1;
    private static GameMap map;
    public void start(Player player, Screen screen) throws Exception {
        int gold = 1000;
        GameMap map = new DungeonMap(floor);
        player.randomEntityLocation(map);
        map.enemySpawn(map, player); //spawn enemies
        Iterator<Enemy> it = map.getEnemies().iterator();
        boolean combat = false;
        Combat combat_obj = new Combat();

        boolean running = true;
        while (running) {
            screen.clear();
            TextGraphics g = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();

            combat = false;
            map.render(g); //draw map

            g.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            g.putString(player.getP()[1], player.getP()[0], "\u00A7"); // spawn player

            for (Entity e: map.getEnemies()) {
                String[] eRGB = e.getColor().split(",");
                g.setForegroundColor(new TextColor.RGB(Integer.parseInt(eRGB[0]), Integer.parseInt(eRGB[1]), Integer.parseInt(eRGB[2])));
                g.putString(e.getP()[1], e.getP()[0], e.getSymbol()); // spawn enemies
            }

            g.setForegroundColor(TextColor.ANSI.RED);
            g.putString(0, size.getRows() - 1, String.format("HP:%d/%d  Gold:%d  Floor:%d  [WASD=move Q=quit]", player.getHp(), player.getMaxHp(), gold, floor));

            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Escape) {
                running = false;
                Town town = new Town();
                town.start(player, screen, new int[]{32,50});
                break;
            }
            running = player.movement(key,map,running); //Player move
            for (Enemy e : map.getEnemies()) { //Enemies move
                e.EnemyAi(map, player);
                combat = e.equals(player);
                if (combat) {
                    running = combat_obj.combat(screen, e, player);
                    if (!running) {
                        Town town = new Town();
                        town.start(player, screen, new int[]{16,50});
                        break;
                    } else {
                        it.remove();
                        combat = false;
                    }
                }
            }
            if(map.getTile(player.getP()[0], player.getP()[1]) ==  Tiles.LADDER.getSymbol()) {//new dungeon floor
                floor++;
                map = new DungeonMap(floor);
                player.randomEntityLocation(map);
                map.enemySpawn(map, player);
            }
        }
        screen.stopScreen();
    }
}
