import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Meisam on 6/20/2015.
 */
public class FlushLightEnemy extends DynamicEnemy{
    public Direction flushLightDirection;
    public BufferedImage lImg,rImg;
//    private boolean picDirection;
    private int directionCounter = 0;
    FlushLightEnemy(Point position , Direction direction ,Map map) throws IOException{
        super(position,direction , map);
        this.flushLightDirection = flushLightDirection();
        lImg = ImageIO.read(DynamicEnemy.class.getResource("/Images/Fll" + this.getDirection(this.direction) + ".png/"));
        rImg = ImageIO.read(DynamicEnemy.class.getResource("/Images/Flr" + this.getDirection(this.direction) + ".png/"));
        name = "Flush Light Enemy";
//        picDirection = true;
    }
    //moves enemy in emergency and if he can kill hitman
    public void move(){
        lastDirection = direction;
        char lastPosition = map.nodes[this.position.i][this.position.j].getName1();
//        picDirection = !picDirection;
        directionCounter++;
        if(this.killHitman(flushLightDirection)) {
            CUI.enemyMoveReport(this, lastPosition);
            Thread t = new Thread(new GUI.moveHandler(this, flushLightDirection));
            t.start();
            killHitman();
        }

        else {
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
    }

    @Override
    protected void changeDirection() {
        super.changeDirection();
        flushLightDirection = flushLightDirection();
    }
    private Direction flushLightDirection(){
        switch (direction){
            case Up:
                return ((directionCounter)%2 == 0)? Direction.Left : Direction.Right;
            case Down:
                return ((directionCounter)%2 == 0)? Direction.Right : Direction.Left;
            case Left:
                return ((directionCounter)%2 == 0)? Direction.Up : Direction.Down;
            case Right:
                return ((directionCounter)%2 == 0)? Direction.Down : Direction.Up;
            default: return Direction.Down;
        }
    }
    //checkes if enemy can kill hitman and if yes kills it
    protected boolean killHitman(Direction flushLightDirection){
        if(map.nodes[this.position.i][this.position.j].getAdjaccentNode(this.flushLightDirection) != null) {
            walk(this.flushLightDirection);
            if (!super.killHitman()) {
                walk(oppositeDirection(flushLightDirection));
                return false;
            } else
                return true;
        }
        else return false;
    }

    protected Direction oppositeDirection(Direction dir){
        switch (dir){
            case Up: return Direction.Down;
            case Down: return Direction.Up;
            case Left: return Direction.Right;
            case Right: return Direction.Left;
            default: return Direction.Up;
        }
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
        return "Flush Light Enemy" + (emergency? "*" : "") + " (" + dir + ")";
    }

    @Override
    public void paint(Graphics g) {
        if(directionCounter%2 == 0)g.drawImage(rImg,0,0,map.sizee / 2,map.sizee / 2,null);
        else g.drawImage(lImg,0,0,map.sizee / 2,map.sizee / 2,null);
    }
}
