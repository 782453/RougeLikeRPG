package RougelikeRPG;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class DungeonMap implements GameMap {
    private Tiles[][] map;
    private static final int ROWS = 34;
    private static final int COLS = 100;
    private static final double FILL_CHANCE = 0.42;
    private static final int CA_ITERATIONS = 7;
    private static final int NEIGHBOR_THRESHOLD = 5;
    private static final int MIN_REGION_SIZE = 40;
    private static final String[][] enemy_type = {
            {"Rat", "20", "5", "0"},
            {"Bat", "25", "8", "0"},
            {"Goblin", "50", "12", "3"}
    };
    private ArrayList<Enemy> enemies = new ArrayList<>();
    public DungeonMap(int floor) {
        this.map = generate();
        this.enemies = enemy_gen(floor);
    }
    public static Tiles[][] generate() {
        Tiles[][] map = new Tiles[ROWS][COLS];
        int temp;
        for(int i = 0; i < ROWS; i++) {
            Arrays.fill(map[i], Tiles.WALL);}
        for(int i = 1; i < ROWS - 1; i++) {
            for(int j = 1; j < COLS - 1; j++) {
                map[i][j] = (Math.random() < FILL_CHANCE) ? Tiles.FLOOR : Tiles.WALL;
            }
        }
        Tiles[][] newMap = new Tiles[ROWS][COLS];
        int count = 0;
        while(count < CA_ITERATIONS) {
            for (int i = 0; i < ROWS; i++) {Arrays.fill(newMap[i], Tiles.WALL);}
            for (int i = 1; i < ROWS - 1; i++) {
                for (int j = 1; j < COLS - 1; j++) {
                    temp = 0;
                    if (map[i][j - 1] == Tiles.WALL) temp++;
                    if (map[i][j + 1] == Tiles.WALL) temp++;
                    if (map[i - 1][j] == Tiles.WALL) temp++;
                    if (map[i + 1][j] == Tiles.WALL) temp++;
                    if (map[i - 1][j - 1] == Tiles.WALL) temp++;
                    if (map[i - 1][j + 1] == Tiles.WALL) temp++;
                    if (map[i + 1][j - 1] == Tiles.WALL) temp++;
                    if (map[i + 1][j + 1] == Tiles.WALL) temp++;
                    if (temp >= NEIGHBOR_THRESHOLD) newMap[i][j] = Tiles.WALL;
                    else newMap[i][j] = Tiles.FLOOR;
                }
            }
            Tiles[][] tempMap = map;
            map = newMap;
            newMap = tempMap;
            count++;
        }
        map = removeSmallRegions(map);
        map = connectRegions(map);
        Tiles ladder_loc = Tiles.WALL;
        int x = 1,y = 1;
        while(ladder_loc != Tiles.FLOOR) {
            x = 1 + (int)(Math.random() * (COLS - 2));
            y = 1 + (int)(Math.random() * (ROWS - 2));
            ladder_loc = map[y][x];
        }
        map[y][x] = Tiles.LADDER;
        return map;
    }

    public static Tiles[][] removeSmallRegions(Tiles[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == Tiles.FLOOR && !visited[i][j]) {
                    ArrayList<int[]> region = new ArrayList<>();
                    ArrayDeque<int[]> queue = new ArrayDeque<>();
                    queue.add(new int[]{i, j});
                    visited[i][j] = true;
                    while (!queue.isEmpty()) {
                        int[] cell = queue.remove();
                        int r = cell[0];
                        int c = cell[1];
                        region.add(cell);
                        // up
                        if (r - 1 >= 0 && map[r - 1][c] == Tiles.FLOOR && !visited[r - 1][c]) {
                            visited[r - 1][c] = true;
                            queue.add(new int[]{r - 1, c});
                        }
                        // down
                        if (r + 1 < rows && map[r + 1][c] == Tiles.FLOOR && !visited[r + 1][c]) {
                            visited[r + 1][c] = true;
                            queue.add(new int[]{r + 1, c});
                        }
                        // left
                        if (c - 1 >= 0 && map[r][c - 1] == Tiles.FLOOR && !visited[r][c - 1]) {
                            visited[r][c - 1] = true;
                            queue.add(new int[]{r, c - 1});
                        }
                        // right
                        if (c + 1 < cols && map[r][c + 1] == Tiles.FLOOR && !visited[r][c + 1]) {
                            visited[r][c + 1] = true;
                            queue.add(new int[]{r, c + 1});
                        }
                    }
                    if (region.size() < MIN_REGION_SIZE) {
                        for (int[] cell : region) {
                            map[cell[0]][cell[1]] = Tiles.WALL;
                        }
                    }
                }
            }
        }
        return map;
    }
    public static Tiles[][] connectRegions(Tiles[][] map) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        ArrayList<ArrayList<int[]>> regions = new ArrayList<>();
        ArrayDeque<int[]> q = new ArrayDeque<>();

        // find all floor regions
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] ==  Tiles.FLOOR && !visited[i][j]) {
                    ArrayList<int[]> region = new ArrayList<>();
                    q.add(new int[]{i, j});
                    visited[i][j] = true;

                    while (!q.isEmpty()) {
                        int[] cell = q.remove();
                        int r = cell[0], c = cell[1];
                        region.add(cell);

                        if (r > 0 && map[r - 1][c] ==  Tiles.FLOOR && !visited[r - 1][c]) {
                            visited[r - 1][c] = true;
                            q.add(new int[]{r - 1, c});
                        }
                        if (r < map.length - 1 && map[r + 1][c] ==  Tiles.FLOOR && !visited[r + 1][c]) {
                            visited[r + 1][c] = true;
                            q.add(new int[]{r + 1, c});
                        }
                        if (c > 0 && map[r][c - 1] ==  Tiles.FLOOR && !visited[r][c - 1]) {
                            visited[r][c - 1] = true;
                            q.add(new int[]{r, c - 1});
                        }
                        if (c < map[0].length - 1 && map[r][c + 1] ==  Tiles.FLOOR && !visited[r][c + 1]) {
                            visited[r][c + 1] = true;
                            q.add(new int[]{r, c + 1});
                        }
                    }

                    regions.add(region);
                }
            }
        }

        if (regions.size() <= 1) return map;

        // find the biggest region
        int biggest = 0;
        for (int i = 1; i < regions.size(); i++) {
            if (regions.get(i).size() > regions.get(biggest).size()) {
                biggest = i;
            }
        }

        ArrayList<int[]> main = regions.get(biggest);

        // connect every other region to the closest tile in the biggest region
        for (int i = 0; i < regions.size(); i++) {
            if (i == biggest) continue;

            ArrayList<int[]> region = regions.get(i);

            // find region center
            double avgR = 0, avgC = 0;
            for (int[] cell : region) {
                avgR += cell[0];
                avgC += cell[1];
            }
            avgR /= region.size();
            avgC /= region.size();

            int[] center = region.get(0);
            double best = Double.MAX_VALUE;
            for (int[] cell : region) {
                double dr = cell[0] - avgR, dc = cell[1] - avgC;
                double dist = dr * dr + dc * dc;
                if (dist < best) {
                    best = dist;
                    center = cell;
                }
            }

            // find the closest tile in the biggest region to that center
            int[] target = main.get(0);
            int bestDist = Integer.MAX_VALUE;
            for (int[] cell : main) {
                int dr = cell[0] - center[0], dc = cell[1] - center[1];
                int dist = dr * dr + dc * dc;
                if (dist < bestDist) {
                    bestDist = dist;
                    target = cell;
                }
            }

            // dig simple L-shaped tunnel
            int r = center[0], c = center[1];
            while (c != target[1]) {
                map[r][c] =  Tiles.FLOOR;
                c += (c < target[1]) ? 1 : -1;
            }
            while (r != target[0]) {
                map[r][c] =  Tiles.FLOOR;
                r += (r < target[0]) ? 1 : -1;
            }
            map[r][c] =  Tiles.FLOOR;
        }

        return map;
    }
    public char getTile(int i, int j) {
        return map[i][j].getSymbol();
    }
    public int getMapWidth() {
        return map[0].length;
    }
    public int getMapHeight() {
        return map.length;
    }
    public int getFloorNeighborCount(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && this.map[row-1][col-1] == Tiles.FLOOR) count++;
        if (row - 1 >= 0 && this.map[row-1][col] == Tiles.FLOOR) count++;
        if (row - 1 >= 0 && col + 1 < COLS && this.map[row-1][col+1] == Tiles.FLOOR) count++;
        if ( col - 1 >= 0 && this.map[row][col-1] == Tiles.FLOOR) count++;
        if ( col + 1 < COLS && this.map[row][col+1] == Tiles.FLOOR) count++;
        if (row + 1 < ROWS && col - 1 >= 0 && this.map[row+1][col-1] == Tiles.FLOOR) count++;
        if (row + 1 < ROWS && this.map[row+1][col] == Tiles.FLOOR) count++;
        if (row + 1 < ROWS && col + 1 < COLS && this.map[row+1][col+1] == Tiles.FLOOR) count++;
        return count;
    }
    public void render(TextGraphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (map[row][col] == Tiles.WALL) {
                    int w_count = getFloorNeighborCount(row, col);
                    if (w_count == 0) {
                        g.setForegroundColor(new TextColor.RGB(18, 14, 10));
                    } else if (w_count == 1) {
                        g.setForegroundColor(new TextColor.RGB(40, 32, 22));
                    } else if (w_count <= 3) {
                        g.setForegroundColor(new TextColor.RGB(62, 50, 34));
                    } else if (w_count <= 5) {
                        g.setForegroundColor(new TextColor.RGB(85, 68, 48));
                    } else {
                        g.setForegroundColor(new TextColor.RGB(105, 85, 58));
                    }
                    g.putString(col, row, "█");
                } else if (map[row][col] ==  Tiles.LADDER) {
                    g.setForegroundColor(new TextColor.RGB(210, 120, 0));
                    g.putString(col, row, "≡");
                } else {
                    g.setForegroundColor(new TextColor.RGB(60, 60, 60));
                    g.putString(col, row, ".");
                }
            }
        }
    }
    public ArrayList<Enemy> enemy_gen(int floor) {
        if(floor < 1 || floor > 10) return null;
        int ratOdds = 5 + (int)(Math.random() * 3);
        double[] ratMulti = {1,0.9,0.65,0.6,0.4,0.25,0,0,0,0};
        int batOdds = 2 + (int)(Math.random() * 2);
        double[] batMulti = {0,1,1.8,1.4,1.4,1,1,0.6,0.4,0};
        int gobOdds =  2 + (int)(Math.random() * 2);
        double[] gobMulti = {0,0,0,1,1.4,1.8,2.2,2.6,3,0};
        int RATS = (int)Math.round(ratOdds*ratMulti[floor-1]);
        int BATS = (int)Math.round(batOdds*batMulti[floor-1]);
        int GOBLINS = (int)Math.round(gobOdds*gobMulti[floor-1]);
        for (int i = 0; i < RATS; i++) {
            Enemy rat = new Enemy(enemy_type[0][0],
                    Integer.parseInt(enemy_type[0][1]),
                    Integer.parseInt(enemy_type[0][2]),
                    Integer.parseInt(enemy_type[0][3]));
            rat.setSymbol('\u04A8');
            rat.setColor("140,140,140");
            enemies.add(rat);
        }
        for (int i = 0; i < BATS; i++) {
            Enemy bat = new Enemy(enemy_type[1][0],
                    Integer.parseInt(enemy_type[1][1]),
                    Integer.parseInt(enemy_type[1][2]),
                    Integer.parseInt(enemy_type[1][3]));
            bat.setSymbol('\u03C9');
            bat.setColor("180,100,255");
            enemies.add(bat);
        }
        for (int i = 0; i < GOBLINS; i++) {
            Enemy goblin = new Enemy(enemy_type[2][0],
                    Integer.parseInt(enemy_type[2][1]),
                    Integer.parseInt(enemy_type[2][2]),
                    Integer.parseInt(enemy_type[2][3]));
            goblin.setSymbol('\u03A6');
            goblin.setColor("80,200,80");
            enemies.add(goblin);
        }
        return enemies;
    }
    public void enemySpawn(GameMap map, Player player) {
        for (Entity e : enemies){
            e.setP(player.getP());
            while(Math.sqrt(Math.pow(e.getP()[0]-player.getP()[0], 2) + Math.pow(e.getP()[1]-player.getP()[1], 2)) <= 20) e.randomEntityLocation(map);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}


