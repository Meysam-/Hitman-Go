import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Created by Meisam on 6/20/2015.
 */
public class DoggyEnemy extends StaticEnemy{
    DoggyEnemy(Point position , Direction direction ,Map map)throws IOException{
        super(position,direction , map);
        img = ImageIO.read(DynamicEnemy.class.getResource("/Images/Do" + this.getDirection(this.direction) + ".png/"));
        name = "Doggy Enemy";
    }
    public void move(){
        lastDirection = direction;
        char lastPosition = map.nodes[this.position.i][this.position.j].getName1();
        if(this.emergency) {
            emergencyOn(this.frontNode());
            walk(this.direction);
            changeDirection();
            CUI.enemyMoveReport(this, lastPosition);
            Thread t = new Thread(new GUI.moveHandler(this, direction));
            /////////////////
            t.start();
        }
        else {
            changeDirection();
            for(Person person:this.frontNode().prop)
                if(person instanceof Hitman) emergencyOn(this.frontNode());
        }
    }

    //change the static enemy's direction just in emergency case
    protected void changeDirection(){
        emergencyDestinationNode = map.nodes[Game.hitman.position.i][Game.hitman.position.j];
        if(this.emergency) {
            emergencyDirection();
        }
    }
    protected Node frontNode(){
        switch (direction) {
            case Up: return map.nodes[this.position.i][this.position.j].up.up;
            case Down: return map.nodes[this.position.i][this.position.j].down.down;
            case Left: return map.nodes[this.position.i][this.position.j].left.left;
            case Right: return map.nodes[this.position.i][this.position.j].right.right;
            default:  return null;
        }
    }
}
