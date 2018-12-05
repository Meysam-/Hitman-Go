import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by sana on 6/17/2015.
 */
public class GUI {
    static Map map;
    static JFrame frame;
    static JPanel gamePanel;
    static JPanel mainPanel;
    static JPanel optionPanel;
    JButton restart;
    JButton menu;
    BufferedImage restartImage;
    BufferedImage menuImage;
    static Clip musicClip , soundClip;
    public static BufferedImage mapImg;

    ImageIcon backgruound;
    static JLabel back;

    GUI(Map map){
        this.map = map;
        try {
            restartImage = ImageIO.read(StartMenu.class.getResource("/Images/restart.png/"));
            menuImage = ImageIO.read(StartMenu.class.getResource("/Images/menu.png/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPanel = new JPanel(null);
        mainPanel.setLocation(0 , 0);
        mainPanel.setSize(1310 , 720);
        optionPanel = new JPanel(null);
        optionPanel.setSize(1310 , 100);
        optionPanel.setLocation(0 , 0);
        optionPanel.setVisible(true);
        gamePanel = new JPanel(null);
        gamePanel.setSize(510,510);
        gamePanel.setLocation(200,100);
        gamePanel.setVisible(true);

        backgruound = new ImageIcon("src/Images/level1.jpg");
        back = new JLabel("",new ImageIcon(mapImg),JLabel.CENTER);
        back.setSize(500, 500);
        back.setLocation(20,20);
        back.setVisible(true);
//        frame.add(back);
//        mainPanel.add(back);
        gamePanel.add(back);

        restart = new JButton(new ImageIcon(restartImage));
        menu = new JButton(new ImageIcon(menuImage));
        restart.setLocation(0 , 0);
        menu.setLocation(150 , 0);
        restart.setSize(100 , 100);
        menu.setSize(100 , 100);
        restart.setVisible(true);
        menu.setVisible(true);
        optionPanel.add(restart);
        optionPanel.add(menu);

        mainPanel.add(gamePanel);
        mainPanel.add(optionPanel);
        mainPanel.setVisible(true);
        frame.add(mainPanel);

        addActionListenerToButton();

        for(Node[] val:map.nodes)
            for(Node value:val) {
                if (value != null) {
                    for (Person p: value.prop) {
                        p.setLocation(value.position.j * map.sizee + map.sizee / 4 , value.position.i * map.sizee + map.sizee / 4);
                        p.setSize(map.sizee / 2 , map.sizee / 2);
                        p.setVisible(true);
                        back.add(p);
                    }
                    for (Item i: value.items) {
                        i.setLocation(value.position.j * map.sizee + map.sizee / 4 , value.position.i * map.sizee + map.sizee / 4);
                        i.setSize(map.sizee / 2 , map.sizee / 2);
                        i.setVisible(true);
                        back.add(i);
                    }
                }
            }
        for(Node[] val:map.nodes)
            for(Node value:val)
                if (value != null)
                    back.add(value);

    }


    public void addActionListenerToButton(){
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    GUI.stopMusic();
                    mainPanel.setVisible(false);
//                    GUI.frame.repaint();
                    PauseMenu pauseMenu = new PauseMenu();
                    GUI.frame.repaint();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /////////////////////////////////////
                mainPanel.setVisible(false);
                mainPanel.removeAll();
                GUI.closeMusic();
                new Game(StartMenu.mainMapFile);
                ///////////////////////////////////////
            }
        });
    }

    public static void playMusic(File file){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioInputStream);
            musicClip.loop(10);
            musicClip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public static void stopMusic(){
        musicClip.stop();
    }

    public static void closeMusic(){
        musicClip.close();
    }

    public static void startMusic(){musicClip.start();}

    public static void playSound(File file){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);
            soundClip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public static void stopSound(){
        soundClip.stop();
    }

    public static void closeSound(){
        soundClip.close();
    }

    static class stoneMoveHandler implements ActionListener , Runnable {
        Stone stone;
        Direction direction;
        Timer timer;
        int i = 0;

        stoneMoveHandler(Stone stone, Direction direction) {
            this.stone = stone;
            this.direction = direction;
            timer = new Timer(10, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            moveReport(stone, direction);
            i++;
            if(i%2 == 0)
                stone.sizeOfPic += (i >= 30)? -1 : 1;
            if (i == 60) {
                timer.stop();
                GUI.playSound(stone.useSoundFile);
//                for (Enemy enemy:Game.enemies)
//                    enemyPicLoad(enemy);
                Node temp = map.nodes[Game.hitman.position.i][Game.hitman.position.j];
                designNodes(temp);
                GUI.gamePanel.remove(stone);
            }
        }

        @Override
        public void run() {
            timer.start();
        }

        public void moveReport(Stone stone, Direction direction) {
            switch (direction) {
                case Down:
                    stone.setLocation(stone.getX(), stone.getY() + 2);
                    break;
                case Up:
                    stone.setLocation(stone.getX(), stone.getY() - 2);
                    break;
                case Right:
                    stone.setLocation(stone.getX() + 2, stone.getY());
                    break;
                case Left:
                    stone.setLocation(stone.getX() - 2, stone.getY());
            }
        }
    }
    static class hitmanMoveHandler implements ActionListener , Runnable{
        Person person;
        Direction direction;
        Timer timer;
        int i = 0;

        hitmanMoveHandler(Person person , Direction direction){
            this.person = person;
            this.direction = direction;
            timer = new Timer(10,this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            moveReport(person, direction);
            deadEnemies(person);
            i++;
            if (i == 50) {
                timer.stop();
                GUI.closeSound();
                deadEnemies(person);
                Node temp = map.nodes[Game.hitman.position.i][Game.hitman.position.j];
                designNodes(temp);
                if (!Node.gunTurn) {
                    for (Enemy enemy : Game.enemies) {
//                    deadEnemies(enemy);
                        enemy.move();
                    }
                }
            }
        }

        @Override
        public void run() {
            Game.enemiesMove = true;
            timer.start();
        }

        public void moveReport(Person person , Direction direction) {
            switch (direction) {
                case Down:
                    person.setLocation(person.getX(), person.getY() + 2);
                    break;
                case Up:
                    person.setLocation(person.getX(), person.getY() - 2);
                    break;
                case Right:
                    person.setLocation(person.getX() + 2, person.getY());
                    break;
                case Left:
                    person.setLocation(person.getX() - 2, person.getY());
            }
        }

        public void deadEnemies(Person person){
            if (person instanceof Enemy && Game.enemies.contains(person)) {
                Graphics2D gr = (Graphics2D) person.getGraphics();
                if (gr != null)
                    person.repaint();
            }
//            else if (person instanceof Enemy && !Game.enemies.contains(person))
//                GUI.gamePanel.remove(person);
        }
    }

    static class SubwayMoveHandler implements ActionListener,Runnable{
        Timer timer;
        Thing thing;
        int destX,destY;
        int i;

        public SubwayMoveHandler(Thing thing, int destX, int destY){
            i = 0;
            timer = new Timer(10 , this);
            this.destX = destX;
            this.destY = destY;
            this.thing = thing;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            i++;
            thing.setLocation(thing.getX() + Integer.signum(destX - thing.getX()) , thing.getY() + Integer.signum(destY - thing.getY()));
            if (thing.getY() == destY && thing.getX() == destX){
                GUI.playSound(Subway.useSoundFile);
                timer.stop();
                fillNodes();
                for (Enemy enemy : Game.enemies)
                    enemy.move();
            }
        }

        @Override
        public void run() {
            timer.start();
        }
    }

    static class moveHandler implements ActionListener , Runnable{
        Person person;
        Direction direction;
        Timer timer;
        int i = 0;

        moveHandler(Person person , Direction direction){
            this.person = person;
            this.direction = direction;
            timer = new Timer(10,this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            moveReport(person, direction);
            i++;
            if (i == 50) {
                timer.stop();
//                if (person instanceof Enemy) {
//                    Thread t = new Thread(new RotationHandler((Enemy) person));
//                    t.start();
//                }
                enemyPicLoad((Enemy)person);
                GUI.closeSound();
                Node temp = map.nodes[person.position.i][person.position.j];
                designNodes(temp);
                Game.enemiesMove = false;
            }
        }

        @Override
        public void run() {
            timer.start();
        }

        public void moveReport(Person person , Direction direction) {
            switch (direction) {
                case Down:
                    person.setLocation(person.getX(), person.getY() + 2);
                    break;
                case Up:
                    person.setLocation(person.getX(), person.getY() - 2);
                    break;
                case Right:
                    person.setLocation(person.getX() + 2, person.getY());
                    break;
                case Left:
                    person.setLocation(person.getX() - 2, person.getY());
            }
        }

        public void deadEnemies(Person person){
            if ((person instanceof Enemy && Game.enemies.contains(person))) {
                Graphics2D gr = (Graphics2D) person.getGraphics();
                if (gr != null)
                    person.repaint();
            }
//            else if (person instanceof Enemy && ! Game.enemies.contains(person))
//                GUI.gamePanel.remove(person);
        }
    }

    public static void designNodes(Node node){
        List<Thing> all = new ArrayList<Thing>();
        all.addAll(node.items);
        all.addAll(node.prop);
        switch (all.size()){
            case 1:
                Thread t1 = new Thread(new NodesHandeler(all.get(0) , node.position.j * map.sizee + map.sizee / 4 , node.position.i * map.sizee + map.sizee / 4));
                t1.start();
                break;
            case 2:
                Thread t2 = new Thread(new NodesHandeler(all.get(0) , node.position.j * map.sizee , node.position.i * map.sizee + map.sizee / 4));
                t2.start();
                Thread t3 = new Thread(new NodesHandeler(all.get(1) , node.position.j * map.sizee + map.sizee / 2 , node.position.i * map.sizee + map.sizee / 4));
                t3.start();
                break;
            case 3:
                Thread t4 = new Thread(new NodesHandeler(all.get(0) , node.position.j * map.sizee , node.position.i * map.sizee));
                t4.start();
                Thread t5 = new Thread(new NodesHandeler(all.get(1) , node.position.j * map.sizee , node.position.i * map.sizee + map.sizee / 2));
                t5.start();
                Thread t6 = new Thread(new NodesHandeler(all.get(2) , node.position.j * map.sizee + map.sizee / 2 , node.position.i * map.sizee + map.sizee / 4));
                t6.start();
                break;
            case 4:
                Thread t7 = new Thread(new NodesHandeler(all.get(0) , node.position.j * map.sizee , node.position.i * map.sizee));
                t7.start();
                Thread t8 = new Thread(new NodesHandeler(all.get(1) , node.position.j * map.sizee , node.position.i * map.sizee + map.sizee / 2));
                t8.start();
                Thread t9 = new Thread(new NodesHandeler(all.get(2) , node.position.j * map.sizee + map.sizee / 2 , node.position.i * map.sizee));
                t9.start();
                Thread t10 = new Thread(new NodesHandeler(all.get(3) , node.position.j * map.sizee + map.sizee / 2 , node.position.i * map.sizee + map.sizee / 2));
                t10.start();
        }
    }

    static class NodesHandeler implements ActionListener,Runnable{
        Timer timer;
        Thing thing;
        int destX,destY;
        int i;

        public NodesHandeler(Thing thing , int destX , int destY){
            i = 0;
            timer = new Timer(10 , this);
            this.destX = destX;
            this.destY = destY;
            this.thing = thing;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            i++;
            thing.setLocation(thing.getX() + Integer.signum(destX - thing.getX()) , thing.getY() + Integer.signum(destY - thing.getY()));
            if (i == map.sizee / 2){
                timer.stop();
            }
        }

        @Override
        public void run() {
            timer.start();
        }
    }

    public static void fillNodes(){
        for (int i = 0 ; i < map.nodes.length ; i++)
            for (int j = 0 ; j < map.nodes[i].length ; j++)
                if (map.nodes[i][j] != null)
                    GUI.designNodes(map.nodes[i][j]);
    }

    static class RotationHandler implements ActionListener , Runnable {
        Timer timer;
        Enemy enemy;
        int i;

        public RotationHandler(Enemy enemy) {
            timer = new Timer(10, this);
            this.enemy = enemy;
            i = 0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            i++;
            if (enemy.lastDirection == enemy.direction) {
                timer.stop();
                enemy.degree = 0;
                fillNodes();
            }
            if (enemy.direction.equals(oppositeDirection(enemy.lastDirection))) {
                enemy.degree = 1;
                if (i == 180) {
                    timer.stop();
                    enemy.degree = 0;
                    fillNodes();
                }
            }
            if (enemy.direction.equals(positiveDirection(enemy.lastDirection))) {
                enemy.degree = 1;
                if (i == 90) {
                    timer.stop();
                    enemy.degree = 0;
                    fillNodes();
                }
            }
            if (enemy.direction.equals(negativeDirection(enemy.lastDirection))) {
                enemy.degree = 1;
                if (i == 270) {
                    timer.stop();
                    enemy.degree = 0;
                    fillNodes();
                }
            }
        }

        @Override
        public void run() {
            timer.start();
        }

        public Direction oppositeDirection(Direction direction){
            switch (direction){
                case Right : return Direction.Left;
                case Left : return Direction.Right;
                case Down : return Direction.Up;
                default : return Direction.Down;
            }
        }

        public Direction positiveDirection(Direction direction){
            switch (direction){
                case Right : return Direction.Up;
                case Left : return Direction.Down;
                case Down : return Direction.Right;
                default : return Direction.Left;
            }
        }

        public Direction negativeDirection(Direction direction){
            switch (direction){
                case Right : return Direction.Down;
                case Left : return Direction.Up;
                case Down : return Direction.Left;
                default : return Direction.Right;
            }
        }
    }

    public static void enemyPicLoad(Enemy enemy) {
        try {
            if (enemy instanceof DynamicEnemy)
                enemy.img = ImageIO.read(DynamicEnemy.class.getResource("/Images/Dy" + enemy.getDirection(enemy.direction) + ((enemy.emergency) ? "e" : "") + ".png/"));
            else if (enemy instanceof DoggyEnemy)
                enemy.img = ImageIO.read(DoggyEnemy.class.getResource("/Images/Do" + enemy.getDirection(enemy.direction) + ((enemy.emergency) ? "e" : "") + ".png/"));
            else if (enemy instanceof StaticEnemy)
                enemy.img = ImageIO.read(StaticEnemy.class.getResource("/Images/St" + enemy.getDirection(enemy.direction) + ((enemy.emergency) ? "e" : "") + ".png/"));
            else if (enemy instanceof TurningEnemy)
                enemy.img = ImageIO.read(TurningEnemy.class.getResource("/Images/Tu" + enemy.getDirection(enemy.direction) + ((enemy.emergency) ? "e" : "") + ".png/"));
            else if (enemy instanceof FlushLightEnemy) {
                ((FlushLightEnemy) enemy).lImg = ImageIO.read(FlushLightEnemy.class.getResource("/Images/Fll" + enemy.getDirection(enemy.direction) + ((enemy.emergency) ? "e" : "") + ".png/"));
                ((FlushLightEnemy) enemy).rImg = ImageIO.read(FlushLightEnemy.class.getResource("/Images/Flr" + enemy.getDirection(enemy.direction) + ((enemy.emergency) ? "e" : "") + ".png/"));
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
