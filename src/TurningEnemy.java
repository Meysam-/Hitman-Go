import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Created by Meisam on 3/31/2015.
 */
public class TurningEnemy extends StaticEnemy{

    TurningEnemy(Point position , Direction direction ,Map map) throws IOException {
        super(position,direction , map);
        name = "Turning Enemy";
        img = ImageIO.read(TurningEnemy.class.getResource("/Images/Tu" + this.getDirection(this.direction) + ".png/"));
    }
    //sets the enemy's direction in emergency on non emergency condition
    protected void changeDirection() {
        if(this.emergency)
            emergencyDirection();
        else
            direction = oppositeDirection();
    }
    //moves the enemy if he kan kill hitman and if condition is emergency
    public void move(){
        super.move();
        CUI.turnReport(this);
    }
    //returns the enemy's state
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
        return "Turning Enemy" +  (emergency? "*" : "") + " (" + dir + ")";
    }
}
