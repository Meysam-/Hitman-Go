import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Time;

/**
 * Created by Meisam on 4/5/2015.
 */
public abstract class Thing extends Comp {
    protected Point position;//position of the thing
    protected String name = "wrong!";//name of the thing
    Map map;
    BufferedImage img;
    Timer timer;

    Thing(Point position , Map map){
        this.position = position;
        this.map = map;
//        this.setBorderPainted(false);
//        this.setFocusPainted(false);
//        this.setContentAreaFilled(false);
    }

    ////////////////////////////////////
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img,0,0,map.sizee / 2,map.sizee / 2,null);
    }
    //////////////////////////////////////
}
