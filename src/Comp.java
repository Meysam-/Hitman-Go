import javafx.scene.control.TextArea;

import javax.imageio.ImageIO;
import javax.swing.*;import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;import java.lang.IllegalArgumentException;import java.lang.Override;import java.lang.String;

/**
 * Created by Meisam on 5/13/2015.
 */
public abstract class Comp extends JLabel implements MouseMotionListener,MouseListener,ActionListener {
    BufferedImage img;
    String name;
    public Comp(){}

    public Comp(String name) {
        this.name = name;
    }

//    public void setImg(String address) {
//        try {
//            this.img = ImageIO.read(SourceComp.class.getClassLoader().getResource(address));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (IllegalArgumentException e){
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
}
