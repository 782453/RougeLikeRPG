package RougelikeRPG;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import java.util.ArrayList;

public class Town implements GameMap{
    private // 34 rows x 100 cols
    char[][] map = {
            "████████████████████████████≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈█████████████████████████████████████████".toCharArray(), // 0
            "█♣♣♣,╔╗╔╗,,,,,,,,,,,,,,,♣♣♣♣≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈♣♣|░|,,≈≈≈≈≈≈≈,,,,,,,,,║★──,,,,,,,,,,,,,,,,♣♣♣♣,,,,,,,,,█".toCharArray(), // 1
            "█,╔══╝╚╝╚═════════════╗,,♣♣≈≈≈≈≈≈≈≈≈≈≈≈≈≈♣≈♣,|░|,╔══════════════════════════════════╗,♣♣♣,,,,,♣♣♣♣,█".toCharArray(), // 2
            "█,║     ♨  IN  ♨      ║,,♣♣,≈≈≈≈≈≈≈≈≈♣♣♣♣≈≈♣,|░|,║         ★  TOWN HALL  ★          ║,,,♣♣♣♣♣,,♣♣,,█".toCharArray(), // 3
            "█,║                  ,║,,♣♣,,≈≈≈≈≈≈≈♣♣♣♣♣≈≈♣,|░|,║                                 ,║,,♣♣♣♣♣♣♣♣♣,,,█".toCharArray(), // 4
            "█,║ ▌·▐           ▌·▐,║,,♣♣,,,♣♣♣♣♣,,,♣♣♣≈≈♣,|░|,║ ▌·▐ ▌·▐ ▌·▐ ▌·▐ ▌·▐ ▌·▐ ▌·▐ ▌·▐▐,║,,,♣♣♣♣♣♣♣♣♣♣,█".toCharArray(), // 5
            "█,║ ▌┌──┐  │ ┌──┐ ▌·▐,║,,♣,,,,,,,♣♣♣,,,║≈≈≈♣,|░|,║ ▌·▐  ▲   ▲   ▲   ▲   ▲   ▲   ▌·▐,║,,♣♣♣♣♣♣♣♣♣,,,█".toCharArray(), // 6
            "█,║  └──┘  │ └──┘    ,║,,♣,,,,,♣♣♣♣♣♣,,╨♣♣♣♣,|░|,║    ╔════════════════════════╗   ,║,,,♣♣,,,,,,,,,█".toCharArray(), // 7
            "█,║                  ,║,,♣,,,,,,,,♣,,,,,,,,♣,|░|,║    ║  ♚                     ║   ,║,,♣♣♣♣♣♣,,,,,,█".toCharArray(), // 8
            "█,║╞───────────────╡ ,║,,♣,,,,,♣♣♣♣♣,,,,,,,♣,|░|,║    ║  ─                     ║   ,║,,,,,♣♣♣♣♣,,,,█".toCharArray(), // 9
            "█,║   ! !  o  ! !    ,║,,♣,,,,,,,♣♣♣♣♣,,,,,♣,|░|,║    ╚════════════════════════╝   ,║,,,,♣♣♣♣♣♣♣♣♣♣█".toCharArray(), // 10
            "█,║        ▼         ,║,,,,,,,,,,,,,,,,,,,,♣,|░|,║      ▽   ▽   ▽   ▽   ▽   ▽      ,║,,,♣♣,,,,♣♣♣,,█".toCharArray(), // 11
            "█,║                  ,║,,,,,,,,,,,,,,♣,,,,,,,|░|,║                                 ,║,,,,♣♣♣♣♣,,♣♣,█".toCharArray(), // 12
            "█,║                  ,║,,,,,,,,,,,,,,,,,,,,,,|░|,╚════════════════╡▄╞═══════════════╝,,,,,,,,♣♣♣♣,,█".toCharArray(), // 13
            "█♣╚════════╡▄╞════════╝,,♣♣,†,,,,,,,,,†,,,,♣♣|░|,,,,,,,,,†,,,,,,,,,|░|,,,†,,,,,,,,,†,,,,♣♣,♣♣,†,,,,█".toCharArray(), // 14
            "█▄▄▄▄▄▄▄▄▄▄▄░▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄░▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄░▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█".toCharArray(), // 15
            "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░█".toCharArray(), // 16
            "█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀░▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█".toCharArray(), // 17
            "█,╔═══════════════════╗,,,,♣♣♣,,,,,,,,,,,,,,,|░|,╔══════════════════════════════════╗,,,,,,,,,,,,,,█".toCharArray(), // 18
            "█,║     ⚒ SMITHY ⚒    ║,,,♣♣♣♣♣,,,,,,,,,,,,,,|░|,║        ◆  GENERAL SHOP  ◆        ║,,♣♣,,,,,,,,,,█".toCharArray(), // 19
            "█,║             ~╔╗  ,║,,,,,╔═══════════╗,,,,|░|,║ ╞───────────────────────────────╡║,,♣♣,,,,,,,,,,█".toCharArray(), // 20
            "█,║ │†▐         ↑▓▓·▐,║,,,,,║♣·≈≈≈≈≈≈≈·♣║,,,,|░|,║  !  !  ?  !  §  ◎  !  ?  ◆  ★ ·  ║,,♣,♣,,,,,,,,,█".toCharArray(), // 21
            "█,║ │†▐         ↑▓▓·▐,║,,,,,║≈≈┌─────┐≈≈║♣,,,|░|,║  ·                            ·  ║,,♣,♣,,,,,,,,,█".toCharArray(), // 22
            "█,║ │†           ▀▀  ,║,♣♣,,║≈≈│≈≈↑≈≈│≈≈║,,,,|░|,║ ╞───────────────────────────────╡║,,♣,♣,,,,,,,,,█".toCharArray(), // 23
            "█,║ │⚔  ╦═╦          ,║,,,♣,║≈≈│≈~◎~≈│≈≈║,,,,|░|,║  †  ⚔  ○  ▪  ◇  !  §  ◎  †  !   ,║,,♣,,,,,,♣♣♣,,█".toCharArray(), // 24
            "█,║                  ,║,,,,,║≈≈│≈≈·≈≈│≈≈║,,,,|░|,║                                 ,║,,,♣,,,,,♣♣♣♣,█".toCharArray(), // 25
            "█,║                  ,║,,,,,║≈≈└─────┘≈≈║,,,,|░|,║                                 ,║,,,♣♣,,,,,,,,,█".toCharArray(), // 26
            "█,║ ╞════════════╡   ,║,,,,,║♣·≈≈≈≈≈≈≈·♣║,,,♣|░|,║ ╞═══════════════════════════════╡║,,♣♣,,,,,♣,,,,█".toCharArray(), // 27
            "█,║   ◆  ○  ◇  ▪     ,║,,,,,╚═══════════╝,,,,|░|,║     │      $$$         │        ,║,,♣♣,,,,,♣♣,,,█".toCharArray(), // 28
            "█,║                  ,║,,,,,,,,┌─────────────|░|,║                                 ,║,,♣,,,,,,,,♣♣,█".toCharArray(), // 29
            "█,║                  ,║,,,,,,,,|░░░░░░░░░░░░░░░|,╚════════════════╡>╞═══════════════╝,,♣♣,,,,♣♣♣,,,█".toCharArray(), // 30
            "█♣╚════════╡>╞════════╝♣♣──────┘░┌───────────┐░░░░░░░░░░░░░░░░░░░░░░|,,,,,,,,,,,,,,,,,,♣♣♣,,,,,,,,,█".toCharArray(), // 31
            "█♣♣,,,,,,,,|░░░░░░░░░░░░░░░░░░░░░|,,,,,,,,,,♣└───|░|────────────────┘,,,,,,,,,,,,,,,,,,,♣♣,,,♣♣♣♣♣,█".toCharArray(), // 32
            "█████████████████████████████████████████████████╡>╞████████████████████████████████████████████████".toCharArray(), // 33
    };
    private static final int ROWS = 34;
    private static final int COLS = 100;
    private static final String[][] enemy_type = {
            {"Rat", "20", "5", "0"},
            {"Bat", "25", "8", "0"},
            {"Goblin", "50", "12", "3"}
    };
    private ArrayList<Enemy> enemies = new ArrayList<>();
    public char getTile(int i, int j) {
        return map[i][j];
    }
    public int getMapWidth() {
        return map[0].length;
    }
    public int getMapHeight() {
        return map.length;
    }
    public void render(TextGraphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (map[row][col] ==  Tiles.WALL.getSymbol()) {
                    g.setForegroundColor(new TextColor.RGB(51, 25, 0));
                    g.putString(col, row, "█");
                }else if (map[row][col] ==  '|') {
                    g.setForegroundColor(new TextColor.RGB(132, 132, 132));
                    g.putString(col, row, "|");
                }else if (map[row][col] ==  Tiles.ROAD.getSymbol()) {
                    g.setForegroundColor(new TextColor.RGB(102, 102, 102));
                    g.putString(col, row, "░");
                }else if (map[row][col] ==  Tiles.SIDEWALK_u.getSymbol()) {
                    g.setForegroundColor(new TextColor.RGB(132, 132, 132));
                    g.putString(col, row, "▄");
                }else if (map[row][col] ==  Tiles.SIDEWALK_d.getSymbol()) {
                    g.setForegroundColor(new TextColor.RGB(132, 132, 132));
                    g.putString(col, row, "▀");
                }else if (map[row][col] ==  '╡') {
                    g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    g.putString(col, row, "╡");
                }else if (map[row][col] ==  '>') {
                    g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    g.putString(col, row, ">");
                }else if (map[row][col] ==  '╞') {
                    g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    g.putString(col, row, "╞");
                }else if (map[row][col] ==  '╚') {
                    g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    g.putString(col, row, "╚");
                }else if (map[row][col] ==  '╔') {
                    g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    g.putString(col, row, "╔");
                }else if (map[row][col] ==  '╗') {
                    g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    g.putString(col, row, "╗");
                }else if (map[row][col] ==  '═') {
                    g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    g.putString(col, row, "═");
                }else if (map[row][col] ==  '╝') {
                    g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    g.putString(col, row, "╝");
                }else if (map[row][col] ==  '║') {
                    g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    g.putString(col, row, "║");
                }else if (map[row][col] ==  Tiles.TREE.getSymbol()) {
                    g.setForegroundColor(new TextColor.RGB(32, 92, 42));   // main tree color
                    g.putString(col, row, "♣");
                }else if (map[row][col] ==  Tiles.RIVER.getSymbol()) {
                    g.setForegroundColor(new TextColor.RGB(0, 102, 204));
                    g.putString(col, row, "≈");
                }
                else if (map[row][col] ==  Tiles.GRASS.getSymbol()) {
                    g.setForegroundColor(new TextColor.RGB(34, 85, 34));      // dark grass
                    g.putString(col, row, ",");
                }else {
                    String c = "" + map[row][col];
                    g.setForegroundColor(new TextColor.RGB(192, 192, 192));
                    g.putString(col, row, c);
                }
            }
        }
    }
    public ArrayList<Enemy> enemy_gen(int floor) {
        return null;
    }
    public void enemySpawn(GameMap map, Player player) {
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public void start(Player player, Screen screen, int[] loc) throws Exception {
        int gold = 1000;
        //GameMap map = new Town();
        player.setP(loc);
       // map.enemySpawn(map, player); //spawn enemies

        boolean running = true;
        while (running) {
            screen.clear();
            TextGraphics g = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();

            this.render(g); //draw map

            g.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            g.putString(player.getP()[1], player.getP()[0], "\u00A7"); // spawn player

            /**for (Entity e: map.getEnemies()) {
                String[] eRGB = e.getColor().split(",");
                g.setForegroundColor(new TextColor.RGB(Integer.parseInt(eRGB[0]), Integer.parseInt(eRGB[1]), Integer.parseInt(eRGB[2])));
                g.putString(e.getP()[1], e.getP()[0], e.getSymbol()); // spawn enemies
            }*/

            g.setForegroundColor(TextColor.ANSI.RED);
            g.putString(0, size.getRows() - 1, String.format("HP:%d/%d  Gold:%d  Floor:%d  [WASD=move Q=quit]", player.getHp(), player.getMaxHp(), gold, 1));

            screen.refresh();

            KeyStroke key = screen.readInput();
            running = player.movement(key,this,running); //Player move
            if(player.getP()[0] == 33 && player.getP()[1] == 50) { //cave dungeon
                running = false;
                Dungeon dungeon = new Dungeon();
                dungeon.start(player, screen);
                break;
            }
            /**for (Enemy e : map.getEnemies()) { //Enemies move
                e.EnemyAi(map, player);
            }*/
        }
        screen.stopScreen();
    }
}
