import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by sana on 6/19/2015.
 */
public class PauseMenu {
    JButton restart , exit , mainMenu , resume;
    BufferedImage menuImage , restartImage , exitmage , mainMenuImage , resumeImage;
    static JPanel panel;
    JLabel label;
    final int width = 1310;
    final int height = 720;

    public PauseMenu() throws IOException {
        menuImage = ImageIO.read(StartMenu.class.getResource("/Images/menu.jpg/"));
        restartImage = ImageIO.read(StartMenu.class.getResource("/Images/NewGame.jpg/"));
        exitmage = ImageIO.read(StartMenu.class.getResource("/Images/NewGame.jpg/"));
        mainMenuImage = ImageIO.read(StartMenu.class.getResource("/Images/NewGame.jpg/"));
        resumeImage = ImageIO.read(StartMenu.class.getResource("/Images/NewGame.jpg/"));
        initializeMenu();
        addListenerToButtons();

        panel.setVisible(true);
        GUI.frame.add(panel);

//        GUI.playMusic(new File("src/sound/pause.wav"));
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
        resume = new JButton("reusme");

        restart.setSize(300 , 40);
        exit.setSize(300 , 40);
        mainMenu.setSize(300 , 40);
        resume.setSize(300 , 40);
        restart.setLocation(700 , 400);
        exit.setLocation(700 , 600);
        mainMenu.setLocation(700 , 500);
        resume.setLocation(700 , 300);
        restart.setVisible(true);
        exit.setVisible(true);
        mainMenu.setVisible(true);
        resume.setVisible(true);
        label.add(restart);
        label.add(exit);
        label.add(mainMenu);
        label.add(resume);
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
                panel.setVisible(false);
                GUI.mainPanel.removeAll();
                GUI.closeMusic();
                new Game(StartMenu.mainMapFile);
            }
        });

        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                GUI.mainPanel.setVisible(true);
                GUI.gamePanel.setVisible(true);
            }
        });

        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.frame.removeAll();
                GUI.frame.setVisible(false);
                GUI.closeMusic();
                panel.setVisible(false);
                try {
                    StartMenu menu = new StartMenu();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
