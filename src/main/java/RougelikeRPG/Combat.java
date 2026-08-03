package RougelikeRPG;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class Combat {
    private static final int ROWS = 20;
    private static final int COLS = 47;
    private char[][] battle;
    public boolean combat(Screen screen, Enemy e, Player player) throws Exception {
        boolean running = true;
        int[] damage = {0,0};
        int[] buff = {0,0};
        int turns = 0;
        while (running) {
            screen.clear();
            TextGraphics g = screen.newTextGraphics();
            TerminalSize size = screen.getTerminalSize();

            this.render(g, player, e, damage, buff);
            damage = new int[]{0,0};
            if(turns == 0) buff = new int[]{0,0};
            else buff[0] = 0;//heal buff reset

            g.setForegroundColor(TextColor.ANSI.RED);
            g.putString(0, size.getRows() - 1, String.format("HP:%d/%d  %s:%d/100   [ESC=escape]", player.getHp(), player.getMaxHp(), player.getResourceType(), player.getResource()));

            screen.refresh();
            KeyStroke key = screen.readInput();//player turn
            while(key.getKeyType() != KeyType.Character || (key.getCharacter() != 'a' &&  key.getCharacter() != 'p'
                    &&  key.getCharacter() != 's' && key.getCharacter() != 'b' && key.getCharacter() != 'f')) key = screen.readInput();
            char c = key.getCharacter();
            switch (c) {
                case 'a':
                    damage[0] = Attack(player, e, 0, 0); //player attack
                    break;
                case 'p':
                    //TODO: figure potions
                    Potion(player);
                    break;
                case 's':
                    if(player.getResource() == 100) {
                        damage[0] = Special(player, e);
                    }
                    break;
                case 'b':
                    //TODO: manage resource buff for ROUGE
                    if(player.getResource() == 100) turns = 2;
                    if(player.getType().equals("Warrior")) buff[1] = Buff(player, e);
                    else buff[0] = Buff(player, e);
                    break;
                case 'f':
                    running = Flee();
                    break;
            }
            int resourceGain = 6 + (int)(Math.random() * 7);
            if(e.getHp()<=0) {
                player.incResource(resourceGain + 10 + (int)(Math.random() * 11));//enemy dies
                return true;
            } else player.incResource(resourceGain);
            if(turns > 0) {
                damage[1] = Attack(player, e, 1, buff[1]);//enemy attack
                turns--;
            }
            else damage[1] = Attack(player, e, 1, 0);//enemy attack
            if(player.getHp()<=0) return false;//player dies
        }
        return true;
    }
    public void render(TextGraphics g, Player p, Enemy e, int[] damage, int[] buff) {
        String pla = "║                                             ║";
        String ene = pla;
        String def= "    ";
        if(buff[1] != 0 && p.getType().equals("Warrior")) def = String.format("+%2d", buff[1]);
        if(buff[0] != 0 && p.getType().equals("Wizard")) pla = String.format("║  > Player consumed Mana to heal for %3d HP  ║",  buff[0]);
        else if(damage[0] != 0) pla = String.format("║  > Player attacked for %3d Damage           ║",  damage[0]);
        if(damage[1] != 0) ene = String.format("║  > Enemy attacked for %3d Damage            ║",  damage[1]);
        this.battle = new char[][]{
                "╔═════════════════════════════════════════════╗".toCharArray(),
                "║                ⚔  COMBAT  ⚔                 ║".toCharArray(),
                "╠═════════════════════════════════════════════╣".toCharArray(),
                String.format("║   %-7s           VS        %-7s       ║", p.getType(), e.getName()).toCharArray(),
                String.format("║   HP: %3d/%-3d                 HP: %3d/%-3d   ║", p.getHp(), p.getMaxHp(), e.getHp(), e.getMaxHp()).toCharArray(),
                String.format("║   ATT:%3d DEF: %-3d %s        ATT: %-3d      ║", p.getAtt(), p.getDef(), def, e.getAtt()).toCharArray(),
                String.format("║   %5s: %-3d/100              DEF: %-3d      ║", p.getResourceType(), p.getResource(), e.getDef()).toCharArray(),
                "║                                             ║".toCharArray(),
                "║      \\o/                      <:3 )~~~      ║".toCharArray(),
                "║       |                                     ║".toCharArray(),
                "║      / \\                                    ║".toCharArray(),
                "║                                             ║".toCharArray(),
                pla.toCharArray(),
                ene.toCharArray(),
                "╠═════════════════════════════════════════════╣".toCharArray(),
                e.getBattleLine().toCharArray(),
                "╠═════════════════════════════════════════════╣".toCharArray(),
                "║   [A] Attack   [P] Potion   [S] Special     ║".toCharArray(),
                "║   [B] Buff     [F] Flee                     ║".toCharArray(),
                "╚═════════════════════════════════════════════╝".toCharArray()};
        for (int row = 8; row < ROWS + 8; row++) {
            for (int col = 26; col < COLS + 26; col++) {
                g.putString(col, row, "" + this.battle[row - 8][col - 26]);
            }
        }
    }
    public int Attack(Player p, Enemy e, int i, int buff) {
        int damage = 0;
        if (i==0) {//player attack
            damage = p.getAtt() - e.getDef() + (int) (Math.random() * 6 + 1);
            if (damage <= 0) damage = 9;
            e.changeHp(-damage);
        } else {//enemy attack
            damage = e.getAtt() - (p.getDef() + buff) + (int) (Math.random() * 8 + 1);
            if (damage <= 0) damage = 6;
            p.changeHp(-damage);
        }
        return damage;
    }
    public void Potion(Player p) {

    }
    public int Special(Player p, Enemy e) {
        int damage = 0;
        if (p.getType().equals("Warrior")) {
            damage = 3 * Attack(p, e, 0, 0);
            e.changeHp(-damage);
        }
        else if (p.getType().equals("Rouge")) {
            int dmg = 0;
            for(int i = 0; i < 2; i++) {
                damage = Attack(p, e, 0, 0);
                dmg += damage;
            }
            damage = dmg;
        }
        else {
            damage = 5 * Attack(p, e, 0, 0);
            e.changeHp(-damage);
        }
        p.incResource(-999);
        return damage;
    }
    public int Buff(Player p, Enemy e) {
        int buff = 0;
        if (p.getType().equals("Warrior")) {
            buff = (int)(0.9 * p.getDef());
            buff = buff * p.getResource()/100;
            p.incResource(-999);
        }
        else if (p.getType().equals("Rouge")) {

        }
        else {
            buff = (int)(0.7 * p.getResource());
            if(p.getHp() + buff > 100)  buff = p.getMaxHp() - p.getHp();
            p.changeHp(buff);
            p.incResource(-999);
        }
        return buff;
    }
    public boolean Flee() {
        if(Math.random() <= 0.7) return false;
        else return true;
    }
}
