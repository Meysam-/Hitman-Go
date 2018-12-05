import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by Meisam on 4/1/2015.
 */
public abstract class Item extends Thing{
    public static File useSoundFile;

    Item(Point position , Map map){
        super(position , map);
        map.nodes[position.i][position.j].items.add(this);
    }
    //overrided in all sub classes
    public abstract void use();
    //sets the around emergency states on in 3*3 square
    public void attractAroundEnemies(Node center){
        Node[] aroundNodes = new Node[8];
        aroundNodes[0] = map.nodes[center.position.i+1][center.position.j];
        aroundNodes[1] = map.nodes[center.position.i+1][center.position.j+1];
        aroundNodes[2] = map.nodes[center.position.i+1][center.position.j-1];
        aroundNodes[3] = map.nodes[center.position.i-1][center.position.j];
        aroundNodes[4] = map.nodes[center.position.i-1][center.position.j+1];
        aroundNodes[5] = map.nodes[center.position.i-1][center.position.j-1];
        aroundNodes[6] = map.nodes[center.position.i][center.position.j+1];
        aroundNodes[7] = map.nodes[center.position.i][center.position.j-1];

        for(Node node:aroundNodes) {
            if (node != null)
                for (Person person : node.prop)
                    if (!(person instanceof Hitman))
                        ((Enemy) person).emergencyOn(center);
        }
    }
    //return name of items
    public String toString() {
        return name;
    }

    /////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        Graphics2D gr = (Graphics2D) this.getGraphics();
        if (gr != null) {
            repaint();
        }
    }
    ////////////////////////////////////
}
