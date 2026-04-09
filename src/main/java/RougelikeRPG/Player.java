package RougelikeRPG;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class Player extends Entity {
    private static final String[][] Classes = {
            {"Warrior", "Dwarf", "Axe", "Wrath", "Block", "Rage", "130", "12", "8"},
            {"Rouge", "Elf", "Dagger", "Speed", "Dodge", "Focus", "100", "10", "5"},
            {"Wizard", "Human", "Staff", "Mana", "Invisibility", "Meditate","100", "18", "2"}
    };
    private String type;
    private String race;
    private String weapon;
    private String resource;
    private String def_skill;
    private String buff_skill;

    public Player(String name, int type) {
        super(name, Integer.parseInt(Classes[type][6]), Integer.parseInt(Classes[type][6]), Integer.parseInt(Classes[type][7]), Integer.parseInt(Classes[type][8]));
        this.type = Classes[type][0];
        this.race = Classes[type][1];
        this.weapon =  Classes[type][2];
        this.resource = Classes[type][3];
        this.def_skill = Classes[type][4];
        this.buff_skill = Classes[type][5];
    }
    public boolean movement(KeyStroke key, GameMap map, boolean running) {
        this.setN(this.getP());
        if (key.getKeyType() == KeyType.Character) {
            char c = key.getCharacter();
            if (c=='q') running = false;
            if (c=='w') this.setNy(this.getN()[0] - 1);
            if (c=='s') this.setNy(this.getN()[0] + 1);
            if (c=='a') this.setNx(this.getN()[1] - 1);
            if (c=='d') this.setNx(this.getN()[1] + 1);
        }
        if (this.getN()[0] >= 0 && this.getN()[0] < map.getMapHeight() && this.getN()[1] >= 0 && this.getN()[1] < map.getMapWidth() &&
                map.getTile(this.getN()[0], this.getN()[1]) !=  Tiles.WALL.getSymbol() &&
                map.getTile(this.getN()[0], this.getN()[1]) !=  Tiles.RIVER.getSymbol() &&
                map.getTile(this.getN()[0], this.getN()[1]) !=  Tiles.TREE.getSymbol() &&
                map.getTile(this.getN()[0], this.getN()[1]) !=  '║') {
            this.setP(this.getN());
        }
        return running;
    }
}
