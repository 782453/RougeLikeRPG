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
    private char[][] battle;
    public boolean combat(Screen screen, Enemy e, Player player) throws Exception {
        boolean running = true;
        while (running) {
            screen.clear();
            TextGraphics g = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();

            this.render(g, player, e);

            g.setForegroundColor(TextColor.ANSI.RED);
            g.putString(0, size.getRows() - 1, String.format("HP:%d/%d  %s:%d/100   [ESC=escape]", player.getHp(), player.getMaxHp(), player.getResourceType(), player.getResource()));

            screen.refresh();
            KeyStroke key = screen.readInput();//player turn
            while(key.getKeyType() != KeyType.Character || (key.getCharacter() != 'a' &&  key.getCharacter() != 'p'
                    && key.getCharacter() != 'b' && key.getCharacter() != 'f')) key = screen.readInput();
            char c = key.getCharacter();
            switch (c) {
                case 'a':
                    Attack(player, e, 0);
                    break;
                case 'p':
                    break;
                case 'b':
                    break;
                case 'f':
                    running = false;
                    break;
            }
            int resourceGain = 6 + (int)(Math.random() * 7);
            if(e.getHp()<=0) {
                player.incResource(resourceGain + 10 + (int)(Math.random() * 11));//enemy dies
                return true;
            } else player.incResource(resourceGain);
            Attack(player, e, 1);//enemy attack
            if(player.getHp()<=0) return false;//player dies
        }
        return true;
    }
    public void render(TextGraphics g, Player p, Enemy e) {
        this.battle = new char[][]{
                "╔═════════════════════════════════════════════╗".toCharArray(),
                "║                ⚔  COMBAT  ⚔                 ║".toCharArray(),
                "╠═════════════════════════════════════════════╣".toCharArray(),
                String.format("║   %-7s           VS        %-7s       ║", p.getType(), e.getName()).toCharArray(),
                String.format("║   HP: %3d/%-3d                 HP: %3d/%-3d   ║", p.getHp(), p.getMaxHp(), e.getHp(), e.getMaxHp()).toCharArray(),
                String.format("║   ATT:%3d DEF: %-3d            ATT: %-3d      ║", p.getAtt(), p.getDef(), e.getAtt()).toCharArray(),
                String.format("║   %5s: %-3d/100              DEF: %-3d      ║", p.getResourceType(), p.getResource(), e.getDef()).toCharArray(),
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
        for (int row = 8; row < ROWS + 8; row++) {
            for (int col = 26; col < COLS + 26; col++) {
                g.putString(col, row, "" + this.battle[row - 8][col - 26]);
            }
        }
    }
    public void Attack(Player p, Enemy e, int i) {
        if (i==0) {//player attack
            int damage = p.getAtt() - e.getDef() + (int) (Math.random() * 6);
            e.changeHp(-damage);
        } else {//enemy attack
            int damage = e.getAtt() - p.getDef() + (int) (Math.random() * 8);
            p.changeHp(-damage);
        }
    }
    public void Potion(Player p) {

    }
    public void Buff(Player p) {

    }
    public void Flee(Player p) {

    }
}
