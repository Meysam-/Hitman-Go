import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Created by Meisam on 4/1/2015.
 */
public class Pot extends Item {
    Hitman hitman;
    Pot(Point position, Map map ,Hitman hitman) throws IOException {
        super(position,map);
        name = "Pot";
        this.hitman = hitman;
        img = ImageIO.read(Pot.class.getResource("/Images/Po.png/"));
    }
    //hides the hitman in the pot :)
    public void use() {
        Hitman.isHide = true;
        CUI.hitmanHideReport(hitman);
    }
}
