package RougelikeRPG;

public enum Tiles {
    WALL('█'),
    FLOOR('.'),
    LADDER('\u2261'),
    GRASS(','),
    ROAD('░'),
    RIVER('≈'),
    SIDEWALK_u('▄'),
    SIDEWALK_d('▀'),
    TREE('♣');
    private final char symbol;
    Tiles(char symbol) {
        this.symbol = symbol;
    }
    public char getSymbol() {
        return symbol;
    }
}
