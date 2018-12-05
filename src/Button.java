import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Meisam on 5/18/2015.
 */
public abstract class Button extends JButton implements MouseMotionListener,MouseListener,ActionListener {
    BufferedImage img,adImg ,mainImg;
    String name;
    public Button(){}

    public Button(String name) {
        this.name = name;
    }

//    public void setImg(String address) {
//        try {
//            this.img = ImageIO.read(SourceComp.class.getClassLoader().getResource(address));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
