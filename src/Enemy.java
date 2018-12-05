import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Meisam on 4/1/2015.
 */
public abstract class Enemy extends Person{
    protected Direction direction;//the direction of the enemy
    protected Direction lastDirection;
    protected boolean emergency;//shows the emergeny condition
    protected Node emergencyDestinationNode;//the next node that enemy show go in emergeny condition
    File emergencySoundFile;
    int degree=0;

    Enemy(Point position,Direction direction ,Map map){
        super(position,map);
        this.direction = direction;
        emergencyDestinationNode = null;
        emergency = false;
        emergencySoundFile = new File("src/sound/emergency.wav");
    }
    //overrided in all sub classes
    protected abstract void changeDirection();
    //overrided in all sub classes
    public abstract void move();
    //thens the emergenci of enemy on
    public void emergencyOn(Node target){
        GUI.playSound(emergencySoundFile);
        emergency = true;
        emergencyDestinationNode = target;
        changeDirection();
        CUI.emergencyReport(this);
    }

    //returns the enemy's front node
    protected Node frontNode(){
        switch (direction) {
            case Up: return map.nodes[this.position.i][this.position.j].up;
            case Down: return map.nodes[this.position.i][this.position.j].down;
            case Left: return map.nodes[this.position.i][this.position.j].left;
            case Right: return map.nodes[this.position.i][this.position.j].right;
            default:  return null;
        }
    }
    //changes the direction of enemy in emergency
    protected void emergencyDirection(){
        ArrayList<Node> path =  map.shortestPath(map.nodes[this.position.i][this.position.j], emergencyDestinationNode);
        Node nextNode = null;
        if(!path.isEmpty() && map.nodes[this.position.i][this.position.j] != path.get(0)){
            nextNode = path.get(0);
            if(map.nodes[this.position.i][this.position.j].up == nextNode) direction = Direction.Up;
            else if(map.nodes[this.position.i][this.position.j].down == nextNode) direction = Direction.Down;
            else if(map.nodes[this.position.i][this.position.j].right == nextNode) direction = Direction.Right;
            else if(map.nodes[this.position.i][this.position.j].left == nextNode) direction = Direction.Left;
            else;
        }
        else
            this.emergency = false;
    }
    //checks if enemy can kill hitman, and if yes kills it
    protected boolean killHitman(){
        Iterator<Person> it = map.nodes[this.position.i][this.position.j].prop.iterator();
        Person temp = null;
        while(it.hasNext()){
            temp = it.next();
            if(temp instanceof Hitman)
                break;
        }
        if(temp instanceof Hitman && !((Hitman) temp).isHide){
            ((Hitman) temp).setKiller(this);
            temp.killed();
            GUI.stopMusic();
            GUI.gamePanel.remove(temp);
            GUI.frame.repaint();
            try {
                GUI.mainPanel.setVisible(false);
                GUI.mainPanel.removeAll();
                GameOverMenu gameOverMenu = new GameOverMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        else return false;
    }
    //return the opposite direction of enemy
    protected Direction oppositeDirection(){
        switch (direction){
            case Up: return Direction.Down;
            case Down: return Direction.Up;
            case Left: return Direction.Right;
            case Right: return Direction.Left;
            default: return Direction.Up;
        }
    }

    protected char getDirection(Direction direction){
        if (direction == Direction.Left) return 'l';
        else if (direction == Direction.Down) return 'd';
        else if (direction == Direction.Right) return 'r';
        else return 'u';
    }

//    @Override
//    public void paint(Graphics g) {
//        Graphics2D g2d = (Graphics2D)g;
////        AffineTransform affineTransform = g2d.getTransform();
////        affineTransform.rotate(Math.toRadians(degree));
////        ((Graphics2D) g).setTransform(affineTransform);
////        g.drawImage(img,0,0,map.sizee / 2,map.sizee / 2,null);
//
//
//
//
//
//
////        AffineTransform transform = new AffineTransform();
////        transform.rotate(Math.toRadians(degree), this.getX() + map.sizee / 4 , this.getY() + map.sizee / 4);
////        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
////        g2d.drawImage(op.filter(img, null), null,map.sizee / 2 , map.sizee / 2);
//
//
//
//
//
////        AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(degree), this.getX() + map.sizee / 4 , this.getY() + map.sizee / 4);
////        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
////        g2d.drawImage(op.filter(img, null), null,map.sizee / 2 , map.sizee / 2);
//
//
//        AffineTransform at=new AffineTransform();
//        //at.rotate(Math.toRadians(degree), this.position.j * map.sizee + map.sizee / 2 , this.position.i * map.sizee + map.sizee / 2);
//        at.rotate(Math.toRadians(degree), map.sizee  ,  map.sizee );
//        AffineTransformOp rotateOp=new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
//        img =rotateOp.filter(img,null);
//        g.drawImage(img,0,0,map.sizee / 2,map.sizee / 2,null);
//    }
}
