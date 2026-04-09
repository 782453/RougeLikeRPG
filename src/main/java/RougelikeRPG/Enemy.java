package RougelikeRPG;

public class Enemy extends Entity {
    public Enemy(String name, int hp, int att, int def) {
        super(name, hp, hp, att, def);
    }
    public void EnemyAi(GameMap map, Player player) {
        if(Math.sqrt(Math.pow(this.getP()[0]-player.getP()[0], 2) + Math.pow(this.getP()[1]-player.getP()[1], 2)) <= 15){
            this.setN(this.getP());
            if(this.getP()[0] != player.getP()[0] || this.getP()[1] != player.getP()[1]) {
                if(this.getP()[0] < player.getP()[0] && map.getTile(this.getP()[0] + 1, this.getP()[1]) != Tiles.WALL.getSymbol()) this.setNy(this.getP()[0] + 1);
                else if(this.getP()[0] > player.getP()[0] && map.getTile(this.getP()[0] - 1, this.getP()[1]) != Tiles.WALL.getSymbol()) this.setNy(this.getP()[0] - 1);
                else {
                    if(this.getP()[1] < player.getP()[1] && map.getTile(this.getP()[0], this.getP()[1] + 1) != Tiles.WALL.getSymbol()) this.setNx(this.getP()[1] + 1);
                    else if(this.getP()[1] > player.getP()[1] && map.getTile(this.getP()[0], this.getP()[1] - 1) != Tiles.WALL.getSymbol()) this.setNx(this.getP()[1] - 1);
                }
            }
            this.setP(this.getN());
        } else {
            do {
                this.setN(this.getP());
                if (Math.random() < 0.5 && this.getP()[0] - 1 >= 0) {
                    if (Math.random() < 0.5) this.setNy(this.getN()[0] - 1);
                    else if(this.getP()[0] + 1 < map.getMapHeight()) this.setNy(this.getN()[0] + 1);
                } else if(this.getP()[1] - 1 >= 0){
                    if (Math.random() < 0.5) this.setNx(this.getN()[1] - 1);
                    else if(this.getP()[1] + 1 < map.getMapWidth()) this.setNx(this.getN()[1] + 1);
                }
            }
            while(map.getTile(this.getN()[0], this.getN()[1]) == Tiles.WALL.getSymbol());
            this.setP(this.getN());
        }
    }
}
