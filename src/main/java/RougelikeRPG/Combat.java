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
    private static final int ROWS = 18;
    private static final int COLS = 47;
    private char[][] battle = {
            "╔═════════════════════════════════════════════╗".toCharArray(),
            "║                ⚔  COMBAT  ⚔                 ║".toCharArray(),
            "╠═════════════════════════════════════════════╣".toCharArray(),
            "║   [ Warrior ]          VS        [ Rat ]    ║".toCharArray(),
            "║   HP: 130/130              HP: 20/20        ║".toCharArray(),
            "║   ATT: 12  DEF: 8          ATT: 5  DEF: 0   ║".toCharArray(),
            "║   🔥 Wrath: 0/100                           ║".toCharArray(),
            "║                                             ║".toCharArray(),
            "║      \\o/                      <:3 )~~~      ║".toCharArray(),
            "║       |                                     ║".toCharArray(),
            "║      / \\                                    ║".toCharArray(),
            "║                                             ║".toCharArray(),
            "╠═════════════════════════════════════════════╣".toCharArray(),
            "║  > A Rat blocks your path!                  ║".toCharArray(),
            "╠═════════════════════════════════════════════╣".toCharArray(),
            "║   [A] Attack   [P] Potion   [B] Buff        ║".toCharArray(),
            "║   [F] Flee                                  ║".toCharArray(),
            "╚═════════════════════════════════════════════╝".toCharArray()};
    public boolean combat(Screen screen, Enemy e, Player player) throws Exception {
        boolean running = true;
        while (running) {
            screen.clear();
            TextGraphics g = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();

            this.render(g);

            g.setForegroundColor(TextColor.ANSI.RED);
            g.putString(0, size.getRows() - 1, String.format("HP:%d/%d  %s:%d/100   [ESC=escape]", player.getHp(), player.getMaxHp(), player.getResourceType(), player.getResource()));

            screen.refresh();
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Escape) {
                running = false;
                break;
            }
        }
        return true;
    }
    public void render(TextGraphics g) {
        for (int row = 8; row < ROWS + 8; row++) {
            for (int col = 26; col < COLS + 26; col++) {
                g.putString(col, row, "" + this.battle[row - 8][col - 26]);
            }
        }
    }
}
