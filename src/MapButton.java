import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by sana on 6/21/2015.
 */
public class MapButton extends Button {
    File mapFile , musicFile;
    BufferedImage buttonImg , mapImg;

    public MapButton(int i){
        try {
            buttonImg = ImageIO.read(new File("src/Images/ss" + (i + 1) + ".png"));
            mapImg = ImageIO.read(new File("src/Images/level" + (i + 1) + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mapFile = new File("src/level" + (i + 1) + ".map");
        this.musicFile = new File("src/sound/level" + (i + 1) + ".wav");
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
//        if (this.mapFile.getName().charAt(5) - 48 <= Test.levelCounter + 1) {
            GUI.mapImg = this.mapImg;
            StartMenu.panel.setVisible(false);
            StartMenu.mainMapFile = this.mapFile;
            StartMenu.mainMusicFile = this.musicFile;
            GUI.stopMusic();

            Game game = new Game(StartMenu.mainMapFile);
            StartMenu.panel.repaint();
            GUI.gamePanel.repaint();
            GUI.frame.remove(StartMenu.panel);
//        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buttonImg,0,0,100,100,null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Graphics2D gr = (Graphics2D) this.getGraphics();
        if (gr != null) {
            repaint();
        }
    }
}