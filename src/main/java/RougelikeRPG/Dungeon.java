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

public class Dungeon implements MapLoop {
    private static int floor = 1;
    private static GameMap map;
    public void start(Player player, Screen screen) throws Exception {
        int gold = 1000;
        boolean combat;
        GameMap map = new DungeonMap(floor);
        player.randomEntityLocation(map);
        map.enemySpawn(map, player); //spawn enemies

        boolean running = true;
        while (running) {
            combat = false;
            screen.clear();
            TextGraphics g = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();

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
                    running = combat(e, player);
                    Town town = new Town();
                    town.start(player, screen, new int[]{16,50});
                    break;
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
    public boolean combat(Enemy e, Player player) {
        return false;
    }
}
