package RougelikeRPG;

public enum Tiles {
    WALL('#'),
    FLOOR('.'),
    LADDER('\u2261'),
    COBBLE(','),
    ROAD('░'),
    TREE('♣');
    private final char symbol;
    Tiles(char symbol) {
        this.symbol = symbol;
    }
    public char getSymbol() {
        return symbol;
    }
}
