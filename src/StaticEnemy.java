import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Created by Meisam on 3/31/2015.
 */
public class StaticEnemy extends Enemy{

    StaticEnemy(Point position , Direction direction ,Map map) throws IOException {
        super(position, direction , map);
        name = "Static Enemy";
        img = ImageIO.read(StaticEnemy.class.getResource("/Images/St" + this.getDirection(this.direction) + ".png/"));
    }

    public void move(){
        lastDirection = direction;
            char lastPosition = map.nodes[this.position.i][this.position.j].getName1();
            if (this.killHitman()) {
                CUI.enemyMoveReport(this, lastPosition);
                Thread t = new Thread(new GUI.moveHandler(this, direction));
                t.start();
            } else if (this.emergency) {
                walk(this.direction);
                changeDirection();
                CUI.enemyMoveReport(this, lastPosition);
                Thread t = new Thread(new GUI.moveHandler(this, direction));
                t.start();
            } else
                changeDirection();
    }
    //change the static enemy's direction just in emergency case
    protected void changeDirection(){
        if(this.emergency) {
            emergencyDirection();
        }
    }
    //checkes if enemy can kill hitman and if yes kills it
    protected boolean killHitman(){
        if(map.nodes[this.position.i][this.position.j].getAdjaccentNode(this.direction) != null) {
            walk(this.direction);
            if (!super.killHitman()) {
                walk(oppositeDirection());
                return false;
            } else
                return true;
        }
        else return false;
    }
    //returns the state of the enemy
    public String toString() {
        return "Static Enemy" +  (emergency? "*" : "") + " (" + direction + ")";
    }
}
