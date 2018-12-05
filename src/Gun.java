import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Meisam on 4/1/2015.
 */
public class Gun extends Item {

    String shotDirection;
    Gun(Point position, Map map) throws IOException {
        super(position,map);
        name = "Gun";
        img = ImageIO.read(Gun.class.getResource("/Images/gun.png/"));
        useSoundFile = new File("src/sound/gun.wav");
    }

    public void useGun(Node node){
        if (map.nodes[this.position.i][this.position.j+1] == node)
            shotDirection = "right";
        else if (map.nodes[this.position.i][this.position.j-1] == node)
            shotDirection = "left";
        else if (map.nodes[this.position.i-1][this.position.j] == node)
            shotDirection = "up";
        else if (map.nodes[this.position.i+1][this.position.j] == node)
            shotDirection = "down";
        use();
    }
    //returns the possible choicese for shooting the gun
    private HashSet<String> possibleChoices(){
        HashSet<String> possibleChoices = new HashSet<String>();
        String str = "";
        possibleChoices.add("right");
        possibleChoices.add("r");
        str += "\n\tRight";
        possibleChoices.add("left");
        possibleChoices.add("l");
        str += "\n\tLeft";
        possibleChoices.add("up");
        possibleChoices.add("u");
        str += "\n\tUp";
        possibleChoices.add("down");
        possibleChoices.add("d");
        str += "\n\tDown";
        CUI.whereToShoot(str);
        return possibleChoices;
    }
    //kills all possible enemies
    private void killEnemies(Node node){
        Iterator<Person> it = node.prop.iterator();
        List<Person> willKilled = new ArrayList<Person>();
        Person temp;
        while(it.hasNext()){
            temp = it.next();
            if(!(temp instanceof Hitman)) {
                willKilled.add(temp);
                CUI.enemyKillReport((Enemy)temp);
            }
        }
        for (Person kill : willKilled)
            kill.killed();
    }
    //shots the gun to user entered direction
    public void use() {
        Scanner input = new Scanner(System.in);
        String choice = shotDirection;
        HashSet<String> possibleChoices = possibleChoices();
//        choice = input.next().toLowerCase();
        while(!possibleChoices.contains(choice)) {
            System.out.println("ONE OF THE CHOICES!");
            choice = input.next().toLowerCase();
        }
        if(choice.equals("left")){
            System.out.println("Gun shot to left!");
            for(int j = 0 ;j < position.j ; j++)
                if(map.nodes[position.i][j] != null)
                    killEnemies(map.nodes[position.i][j]);
        }
        else if(choice.equals("right")){
            System.out.println("Gun shot to right!");
            for(int j = position.j + 1 ;j < map.nodes[0].length ; j++)
                if(map.nodes[position.i][j] != null)
                    killEnemies(map.nodes[position.i][j]);
        }
        else if(choice.equals("up")){
            System.out.println("Gun shot to up!");
            for(int i = 0 ;i < position.i ; i++)
                if(map.nodes[i][position.j] != null)
                    killEnemies(map.nodes[i][position.j]);
        }
        else if(choice.equals("down")){
            System.out.println("Gun shot to down!");
            for(int i = position.i + 1 ;i < map.nodes.length ; i++)
                if(map.nodes[i][position.j] != null)
                    killEnemies(map.nodes[i][position.j]);
        }
        map.nodes[position.i][position.j].items.remove(this);
        Game.SGTarget();
        GUI.playSound(useSoundFile);
        GUI.gamePanel.remove(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Graphics2D gr = (Graphics2D) this.getGraphics();
        if (gr != null) {
            repaint();
        }
    }
}
