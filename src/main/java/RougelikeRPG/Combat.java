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
public class Combat {
    public boolean combat(Screen screen, Enemy e, Player player) throws Exception{
        boolean running = true;
        while (running) {
            screen.clear();
            TextGraphics g = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();



            g.setForegroundColor(TextColor.ANSI.RED);
            g.putString(0, size.getRows() - 1, String.format("HP:%d/%d  Gold:%d  Floor:%d  [WASD=move Q=quit]", player.getHp(), player.getMaxHp(), 1000, 1));

            screen.refresh();
        }
        return false;
    }
}
