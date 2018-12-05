import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by sana on 6/22/2015.
 */
public class GameOverMenu {
    JButton restart , exit , mainMenu;
    BufferedImage menuImage;
    JPanel panel;
    JLabel label;
    final int width = 1310;
    final int height = 720;

    public GameOverMenu() throws IOException {
        menuImage = ImageIO.read(StartMenu.class.getResource("/Images/menu.jpg/"));
        initializeMenu();
        addListenerToButtons();

        panel.setVisible(true);
        GUI.frame.add(panel);

        GUI.playMusic(new File("src/sound/gameover.wav"));
    }

    public void initializeMenu(){
        panel = new JPanel();
        label = new JLabel(new ImageIcon(menuImage));
        label.setLocation(0 , 0);
        label.setSize(width, height);
        panel.setSize(width , height);
        restart = new JButton("restart");
        exit = new JButton("exit");
        mainMenu = new JButton("main menu");

        restart.setSize(300 , 40);
        exit.setSize(300 , 40);
        mainMenu.setSize(300 , 40);
        exit.setLocation(700 , 500);
        mainMenu.setLocation(700 , 400);
        restart.setLocation(700 , 300);
        restart.setVisible(true);
        exit.setVisible(true);
        mainMenu.setVisible(true);
        label.add(restart);
        label.add(exit);
        label.add(mainMenu);
        panel.add(label);
    }

    public void addListenerToButtons(){
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.closeMusic();
                panel.setVisible(false);
                new Game(StartMenu.mainMapFile);
            }
        });

        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.frame.removeAll();
                GUI.frame.setVisible(false);
                panel.setVisible(false);
                GUI.closeMusic();
                try {
                    StartMenu menu = new StartMenu();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
