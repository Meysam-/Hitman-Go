import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by Meisam on 4/1/2015.
 */
public class Target extends Item {
    Game game;
    Target(Point position, Map map) throws IOException {
        super(position,map);
        this.game = game;
        name = "Target";
        img = ImageIO.read(Target.class.getResource("/Images/target.png/"));
        useSoundFile = new File("src/sound/target.wav");
    }
    //sets the game state to won if hitman reaches this!
    public void use() {
        Game.won = true;
        GUI.playSound(useSoundFile);
    }
}
