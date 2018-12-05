import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Created by Meisam on 3/30/2015.
 */
public class DynamicEnemy extends Enemy{
    DynamicEnemy(Point position , Direction direction ,Map map) throws IOException {
        super(position,direction , map);
        name = "Dynamic Enemy";
        img = ImageIO.read(DynamicEnemy.class.getResource("/Images/Dy" + this.getDirection(this.direction) + ".png/"));
    }
    //reverses the direction of enemy
    protected void changeDirection(){
        if(!this.emergency) {
            if (frontNode() == null)
                direction = oppositeDirection();
        }
        else{
            emergencyDirection();
        }
    }

    //moves enemy in emergency and if he can kill hitman
    public void move(){
        lastDirection = direction;
            char lastPosition = map.nodes[this.position.i][this.position.j].getName1();
            changeDirection();
            walk(this.direction);
            CUI.enemyMoveReport(this, lastPosition);
            //////////////////
            Thread t = new Thread(new GUI.moveHandler(this, direction));
            /////////////////
            t.start();
            changeDirection();
            killHitman();
    }
    //returns the enemy state
    public String toString() {
        String dir = "";
        switch (direction){
            case Up:
                dir = "Up";
                break;
            case Down:
                dir = "Down";
                break;
            case Left:
                dir = "Left";
                break;
            case Right:
                dir = "Right";
                break;
        }
        return "Dynamic Enemy" + (emergency? "*" : "") + " (" + dir + ")";
    }
}
