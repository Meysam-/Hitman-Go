import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Meisam on 3/30/2015.
 */
public class Test {
    static int levelCounter = 0;
    public static void main(String[] args) {
//        System.out.println("        Welcome To Hitman:Go Project!\n1st mission: here U R ;)\n");
//        Game game = new Game();
//        game.level1();
//        if(!game.hitman.isKilled){
//            System.out.println("\nhey 47 , mission 1 accomplished !\n2nd mission:\n");
//            game = new Game();
//            game.level2();
//        }
//        if(!game.hitman.ifsKilled){
//            System.out.println("\nExcellent Hitman , mission 2 accomplished ! what about 3rd one?\n3ed mission:\n");
//            game = new Game();
//
//            game.level3();
//        }
//        if(!game.hitman.isKilled)
//            System.out.println("\nYeeeeeeeeeesssss!!! All missions completed successfully! ");
//        new Game(new File("src/myMap.map")).play();

        GUI.frame = new JFrame();

        try {
            StartMenu startMenu = new StartMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
