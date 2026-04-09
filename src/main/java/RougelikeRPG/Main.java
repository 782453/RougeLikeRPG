package RougelikeRPG;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Main {
    static Player player = new Player("TEST_PLAYER", 0);
    static String location = "town";
    public static void main(String[] args) throws Exception {
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(100, 35)).createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        Town town = new Town();
        town.start(player, screen, new int[]{16,0});
    }
}
