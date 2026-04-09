package RougelikeRPG;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;

public class Main {
    static Player player = new Player("TEST_PLAYER", 0);
    static int hp = 10, gold = 0, floor = 1;
    public static void main(String[] args) throws Exception {
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(100, 35)).createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        Dungeon dungeon = new Dungeon();
        dungeon.start(player, screen);
        //Town town = new Town();
        //town.start(player, screen);
    }
}
