import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sana on 6/18/2015.
 */
public class StartMenu{
    JButton start , exit;
    MapButton[] mapButtons;
    static File mainMusicFile , mainMapFile = new File("src/myMap.map");
    static JPanel panel;
    JLabel label;
    BufferedImage menuImage , startImage , exitImage;
    final static int width = 1310;
    final static int height = 800;

    public StartMenu() throws IOException {
        menuImage = ImageIO.read(StartMenu.class.getResource("/Images/menu.jpg/"));
        startImage = ImageIO.read(StartMenu.class.getResource("/Images/NewGame.jpg/"));
        exitImage = ImageIO.read(StartMenu.class.getResource("/Images/QuitGame.jpg"));
        initializeMenu();
        initializeMap();
        addActionListenerToButtons();
        GUI.playMusic(new File("src/sound/startmenu.wav"));
    }

    public void initializeMenu(){
        panel = new JPanel();
        GUI.frame = new JFrame();
        GUI.frame.setLayout(null);
        GUI.frame.setLocation(0, 0);
        GUI.frame.setResizable(false);
        GUI.frame.setTitle("HitmanGo");
        GUI.frame.setSize(width, height);
        GUI.frame.add(panel);
        GUI.frame.setVisible(true);
        GUI.frame.setDefaultCloseOperation(GUI.frame.EXIT_ON_CLOSE);
        panel.setVisible(true);
        label = new JLabel(new ImageIcon(menuImage));
        label.setLocation(0, 0);
        label.setSize(width, height);
        panel.setSize(width , height);
        start = new JButton(new ImageIcon(startImage));
        exit = new JButton(new ImageIcon(exitImage));
        mapButtons = new MapButton[4];
        start.setSize(300 , 40);
        exit.setSize(300 , 40);
        start.setLocation(950, 450);
        exit.setLocation(950, 550);
        label.add(start);
        label.add(exit);
        panel.add(label);
    }

    public void initializeMap(){
        for (int i = 0 ; i < mapButtons.length ; i++) {
            mapButtons[i] = new MapButton(i);
            mapButtons[i].setLocation((i < mapButtons.length / 2) ? 1000 : 1200, 150 * ((i < mapButtons.length / 2) ? i : (i - mapButtons.length / 2)) + 230);
            mapButtons[i].setSize(100 , 100);
        }
    }

    public void addActionListenerToButtons(){
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);
                exit.setVisible(false);
                for (int i = 0 ; i < mapButtons.length ; i++) {
                    mapButtons[i].setVisible(true);
                    label.add(mapButtons[i]);
                    mapButtons[i].repaint();
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
