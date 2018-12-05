import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by Meisam on 4/1/2015.
 */
public class Camera extends Item {
    //if hitman goes to it's node just attracts the around enemies
    Camera(Point position, Map map) throws IOException {
        super(position,map);
        name = "Camera";
        img = ImageIO.read(Camera.class.getResource("/Images/Cm.png/"));
        timer = new Timer(10 , this);
        useSoundFile = new File("src/sound/emergency.wav");
    }
    public void use() {
        attractAroundEnemies(map.nodes[this.position.i][this.position.j]);
        GUI.playSound(useSoundFile);
        CUI.cameraReport(this);
    }
}
