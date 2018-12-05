import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Meisam on 4/1/2015.
 */
public class Stone extends Item {
    String throwDirection;
    int sizeOfPic;
    Stone(Point position, Map map) throws IOException {
        super(position,map);
        name = "Stone";
        sizeOfPic = map.sizee/4;
        img = ImageIO.read(Stone.class.getResource("/Images/stone.png/"));
        useSoundFile = new File("src/sound/stone.wav");
    }

    public void useStone(Node node){
        if (map.nodes[this.position.i][this.position.j+1] == node)
            throwDirection = "right";
        else if (map.nodes[this.position.i][this.position.j-1] == node)
            throwDirection = "left";
        else if (map.nodes[this.position.i-1][this.position.j] == node)
            throwDirection = "up";
        else if (map.nodes[this.position.i+1][this.position.j] == node)
            throwDirection = "down";
        use();
    }
    //returns the possible direction for throwing the stone
    private HashSet<String> possibleChoices(){
        HashSet<String> possibleChoices = new HashSet<String>();
        String str = "";
            if (position.j + 1 != map.nodes.length && map.nodes[position.i][position.j + 1] != null) {
                possibleChoices.add("right");
                str += "\n\tRight";
            }
            if (position.j != 0 && map.nodes[position.i][position.j - 1] != null) {
                possibleChoices.add("left");
                str += "\n\tLeft";
            }
            if (position.i + 1 != map.nodes[0].length && map.nodes[position.i + 1][position.j] != null) {
                possibleChoices.add("down");
                str += "\n\tDown";
            }
            if (position.i != 0 && map.nodes[position.i - 1][position.j] != null) {
                possibleChoices.add("up");
                str += "\n\tUp";
            }
        CUI.whereToThrowStone(str);
        return possibleChoices;
    }
    //throws the stone and attracts the around enemies in 9*9 squar
    public void use() {
        Scanner input = new Scanner(System.in);
        Node destination = null;
        String choice = throwDirection;
        System.out.println("the out : " + throwDirection + "  " + choice);
        HashSet<String> possibleChoices = possibleChoices();
//        choice = input.next().toLowerCase();
        while(!possibleChoices.contains(choice)) {
            CUI.commandError();
            choice = input.next().toLowerCase();
        }
        if(choice.equals("left"))
            destination = map.nodes[position.i][position.j-1];
        else if(choice.equals("right"))
            destination = map.nodes[position.i][position.j+1];
        else if(choice.equals("down"))
            destination = map.nodes[position.i+1][position.j];
        else if(choice.equals("up"))
            destination = map.nodes[position.i-1][position.j];
        else;

        CUI.trowStoneReport(destination.getName1());
        Thread t = new Thread(new GUI.stoneMoveHandler(this,Game.charToDir(choice.charAt(0))));
        t.start();
        attractAroundEnemies(destination);
        map.nodes[position.i][position.j].items.remove(this);
        Game.SGTarget();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img,map.sizee/8,map.sizee/8,sizeOfPic,sizeOfPic,null);
    }
}
