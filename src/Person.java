import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meisam on 3/31/2015.
 */
public abstract class Person extends Thing{
    protected boolean isKilled;
    File deadMusicFile , moveSoundFile;

    Person(Point position ,Map map){
        super(position , map);
        this.map.nodes[position.i][position.j].prop.add(this);
        isKilled = false;
        deadMusicFile = new File("src/sound/dead.wav");
        moveSoundFile = new File("src/sound/move.wav");
    }
    //kills the person
    protected void killed(){
        this.isKilled = true;
        map.nodes[position.i][position.j].prop.remove(this);
        ////////////////////////////
        Game.enemies.remove(this);
        GUI.back.remove(this);
        GUI.playSound(this.deadMusicFile);
        ////////////////////////////
    }
    //walks the person in given direction if possible
    void walk(Direction dir) {
        if (map.nodes[position.i][position.j].getAdjaccentNode(dir) != null) {
//            Node tempNode = map.nodes[position.i][position.j];
//            this.setLocation(tempNode.position.j * map.sizee + map.sizee / 4,tempNode.position.i * map.sizee + map.sizee / 4);
//            Thread t = new Thread(new GUI.NodesHandeler(this , tempNode.position.j * map.sizee + map.sizee / 4 , tempNode.position.i * map.sizee + map.sizee / 4));
//            t.start();
            map.nodes[position.i][position.j].prop.remove(this);
            position = map.nodes[position.i][position.j].getAdjaccentNode(dir).position;
            map.nodes[position.i][position.j].prop.add(this);
            GUI.playSound(this.moveSoundFile);
        }
    }
    //return' the type of the person
    public String getName() {
        return name;
    }
    //returns the position of person
    public Point getPosition() {
        return position;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        Graphics2D gr = (Graphics2D) this.getGraphics();
//        if (gr != null) {
//            repaint();
//        }
    }
}
