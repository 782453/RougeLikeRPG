package RougelikeRPG;

import java.util.Arrays;
public class Entity {
    private final String name;
    private int hp;
    private final int maxHP;
    private final int att;
    private final int def;
    private String symbol;
    private String color;
    private int[] P = new int[2];
    private int[] N = new int[2];
    public Entity(String name, int hp, int maxHP, int att, int def) {
        this.name = name;
        this.hp = hp;
        this.maxHP = maxHP;
        this.att = att;
        this.def = def;
        this.symbol = null;
        this.color = null;
        Arrays.fill(P, 0);
        Arrays.fill(N, 0);
    }
    public String getName() {
        return name;
    }
    public int getHp() {
        return hp;
    }
    public int getAtt() {
        return att;
    }
    public int getDef() {
        return def;
    }
    public int[] getP() {
        return P;
    }
    public int[] getN() {
        return N;
    }
    public void setNx(int Nx) {
        this.N[1] = Nx;
    }
    public void setNy(int Ny) {
        this.N[0] = Ny;
    }
    public void setP(int[] P) {
        this.P = new int[]{P[0], P[1]};
    }
    public void setN(int[] N) {
        this.N = new int[]{N[0], N[1]};
    }
    public void randomEntityLocation(GameMap map) {
        char loc = Tiles.WALL.getSymbol();
        while(loc == Tiles.WALL.getSymbol()) {
            setP(new int[]{1 + (int)(Math.random() * 32), 1 + (int)(Math.random() * 98)});
            loc = map.getTile(getP()[0], getP()[1]);
        }
    }
    public void setSymbol(char symbol) {
        this.symbol = ""+symbol;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getColor() {
        return color;
    }
    public int getMaxHp() {
        return maxHP;
    }
    @Override
    public boolean equals(Object o) {
        return this.P[0] == ((Entity)o).getP()[0] && this.P[1] == ((Entity)o).getP()[1];
    }
}
