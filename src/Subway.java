import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by Meisam on 4/1/2015.
 */
public class Subway extends Item {
    Subway outDoor;//the out door of the subway
    Hitman hitman;
    Subway(Point position, Map map,Hitman hitman) throws IOException {
        super(position,map);
        name = "Subway";
        this.hitman = hitman;
        img = ImageIO.read(Subway.class.getResource("/Images/Sw.png/"));
        useSoundFile = new File("src/sound/subway.wav");
    }
    //trancports the hitman in the subway
    public void use() {
        map.nodes[hitman.position.i][hitman.position.j].prop.remove(hitman);
        hitman.position = outDoor.position;
        map.nodes[outDoor.position.i][outDoor.position.j].prop.add(hitman);
        CUI.hitmanMoveReport(hitman,map.nodes[this.position.i][this.position.j].getName1());
        Thread t = new Thread(new GUI.SubwayMoveHandler(hitman,map.nodes[outDoor.position.i][outDoor.position.j].position.j * map.sizee + map.sizee / 4 ,map.nodes[outDoor.position.i][outDoor.position.j].position.i * map.sizee + map.sizee / 4 ));
        t.start();
        hitman.killEnemies();
    }
    //buils 2 subways and connect them to each other
    public static void SubwayBuilder(Subway a,Subway b){
        a.outDoor = b;
        b.outDoor = a;
    }
    //returns the subway out position
    public String toString() {
        return super.toString() + " To " + map.nodes[outDoor.position.i][outDoor.position.j].getName1();
    }
}
