import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Meisam on 3/30/2015.
 */
public class Game{
    public static Map map;
    static boolean won;
    static boolean hitmanTurn;
    Scanner input;
    static Hitman hitman;
    HashSet<String> possibleChoices;//hitman possible moves
    static List<Enemy> enemies;
    static boolean enemiesMove;

    Game(File file){
        input = new Scanner(System.in);
        possibleChoices = new HashSet<String>();
        enemies = new ArrayList<Enemy>();
        hitmanTurn = true;
        enemiesMove = false;
        won = false;
        map = new Map(file);
        GUI.playMusic(StartMenu.mainMusicFile);
        GUI gui = new GUI(map);
    }
    //it's the main engine of the game and plays it by it's elements
    void play() {
        System.out.println(map);
        while(!won && !hitman.isKilled) {
            ////***** Hitman Turn *****////
            while (hitmanTurn) {
                possibleChoices = hitman.possibleMoves();
                String choice;
                choice = input.next().toLowerCase();
                while (!possibleChoices.contains(choice)) {
                    CUI.commandError();
                    choice = input.next().toLowerCase();
                }
                if (!choice.equals("show")){
                    hitman.move(charToDir(choice.charAt(0)));
                    hitmanTurn = false;
                }
                else {
                    String parameter = input.next().toLowerCase();
                    if (parameter.equals("map"))
                        System.out.println(map);
                    else {
                        int i;
                        for (i = 48; ; i++) {
                            if (i == 58) i = 97;
                            if (i == 123) i = 65;
                            if (i == 91) break;
                            if (parameter.length() == 1 && parameter.charAt(0) == (char) i) {
                                if (map.getNode((char) i) != null)
                                    if (!map.getNode((char) i).toString().equals(""))
                                        System.out.println(map.contains((char)i));
                                    else
                                        System.out.println("Nothing in " + (char) i + "!");
                                else
                                    System.out.println("No such Node in map :|");
                                break;
                            }
                        }
                        if (i == 91 && parameter.length() > 1)
                            System.out.println("parameter not recognized!");
                    }
                }
            }
            cleanList();

            ////***** Enemies Turn *****////
//            for(Enemy enemy:enemies)
//                enemy.move();
//
//            ////***** Stone Turn *****////
//            for(Item item:map.nodes[hitman.position.i][hitman.position.j].items)
//                if(item instanceof Stone)
//                    item.use();
//
//            hitmanTurn = true;
        }
        if(won)
            System.out.println("\nCongratulations! You Won! :D");
        else
            System.out.println("\nGameOver!\n" + hitman.getKilledString());
    }
    //checks and clears the dead enemies in the list
    private void cleanList(){
        List<Enemy> enemiesWillKilled = new ArrayList<Enemy>();
        for(Enemy enemy:enemies)
            if(enemy.isKilled)
                enemiesWillKilled.add(enemy);
        for(Enemy enemy:enemiesWillKilled) {
            enemies.remove(enemy);
            GUI.gamePanel.remove(enemy);
        }
        System.gc();
    }
    public static Direction charToDir(char ch){
        switch (ch){
            case 'u':return Direction.Up;
            case 'd':return Direction.Down;
            case 'l':return Direction.Left;
            case 'r':return Direction.Right;
            case 's':return Direction.Subway;
            case 'h':return Direction.Hide;
            default:return Direction.Down;
        }
    }

    public static void play1(Node node) {

        Node tempNode = map.nodes[Game.hitman.position.i][Game.hitman.position.j];
        if(!enemiesMove) {
            if (isGun(node)) Node.gunTurn = true;
            if (isStone(node)) Node.stoneTurn = true;

            if (tempNode.right == node)
                Game.hitman.move(Direction.Right);
            else if (tempNode.left == node)
                Game.hitman.move(Direction.Left);
            else if (tempNode.up == node)
                Game.hitman.move(Direction.Up);
            else if (tempNode.down == node)
                Game.hitman.move(Direction.Down);
            else {
                Subway in = null, out = null;
//            for (Item item:tempNode.items)
//                if(item instanceof Subway){
//                    in = ((Subway)item);
//                    break;
//                }
                for (Item item : node.items)
                    if (item instanceof Subway) {
                        out = ((Subway) item);
                        break;
                    }

                if (out != null && map.nodes[out.outDoor.position.i][out.outDoor.position.j].prop.contains(hitman))
                    hitman.move(Direction.Subway);
            }
//        GUI.playSound(Game.hitman.moveSoundFile);

            SGTarget();

            if (won) {
                GUI.closeMusic();
                GUI.frame.remove(GUI.mainPanel);
//                GUI.mainPanel.setVisible(false);
                try {
                    MissionClearedMenu missionClearedMenu = new MissionClearedMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Test.levelCounter < StartMenu.mainMapFile.getName().charAt(5) - 48) {
                    Test.levelCounter++;
                    try {
                        GUI.mapImg = ImageIO.read(new File("src/Images/level" + (Test.levelCounter + 1) + ".jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static ArrayList<Node> hitmanAroundNodes(){
        ArrayList<Node> hadaf = new ArrayList<Node>();
        hadaf.add(map.nodes[hitman.position.i+1][hitman.position.j]);
        hadaf.add(map.nodes[hitman.position.i-1][hitman.position.j]);
        hadaf.add(map.nodes[hitman.position.i][hitman.position.j+1]);
        hadaf.add(map.nodes[hitman.position.i][hitman.position.j-1]);
        return hadaf;
    }

    private static boolean isGun(Node node){
        for(Item item:node.items){
            if(item instanceof Gun) return true;
        }
        return false;
    }

    private static boolean isStone (Node node){
        for(Item item:node.items){
            if(item instanceof Stone) return true;
        }
        return false;
    }

    public static void SGTarget(){
        for(Node[] nod:map.nodes)
            for(Node nodee:nod) {
                try {
                    if(nodee != null)
                        nodee.haImg = ImageIO.read(Node.class.getResource("/Images/0.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        for(Item item:map.nodes[Game.hitman.position.i][Game.hitman.position.j].items){
            if(item instanceof Gun || item instanceof Stone) {
                for (Node HaNode : hitmanAroundNodes()) {
                    try {
                        if(HaNode != null)
                            HaNode.haImg = ImageIO.read(Node.class.getResource("/Images/Ha.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
//        for(Item item:map.nodes[Game.hitman.position.i][Game.hitman.position.j].items)
//            if(item instanceof Stone)
//                item.use();

}
