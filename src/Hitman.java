import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Meisam on 3/30/2015.
 */
public class Hitman extends Person{
    ImageIcon hitmanImg;
    private String killedString = "";//shows how hitman killed
    private Enemy killer;//the killed of the hitman
    public static boolean isHide;
    Hitman(Point position ,Map map) throws IOException {
        super(position,map);
        name = "Hitman";
        isHide = false;
        img = ImageIO.read(Hitman.class.getResource("/Images/Hi.png/"));
    }
    //return the possible hitman moves and puts show to set
    public HashSet<String> possibleMoves(){
        HashSet<String> ps = new HashSet<String>();
        String str = "Your choice(s):\n";
        if(map.nodes[this.position.i][this.position.j].up != null) {
            str += "\tUp\n";
            ps.add("up");
            ps.add("u");
        }
        if(map.nodes[this.position.i][this.position.j].down != null){
            str += "\tDown\n";
            ps.add("down");
            ps.add("d");
        }
        if(map.nodes[this.position.i][this.position.j].right != null){
            str += "\tRight\n";
            ps.add("right");
            ps.add("r");
        }
        if(map.nodes[this.position.i][this.position.j].left != null){
            str += "\tLeft\n";
            ps.add("left");
            ps.add("l");
        }
        if(map.nodes[this.position.i][this.position.j].items instanceof Subway){
            str += "\tSubway\n";
            ps.add("subway");
            ps.add("s");
        }
        if(map.nodes[this.position.i][this.position.j].items instanceof Pot && !isHide){
            str += "\tHide\n";
            ps.add("hide");
            ps.add("h");
        }

        CUI.whereToGo(str);
        ps.add("show");
        return ps;
    }
    //checks if killing enemies are possible and kills them
    public void killEnemies() {
        Iterator<Person> it = map.nodes[this.position.i][this.position.j].prop.iterator();
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
    //moves hitman to user entered direction
    public void move(Direction dir){
        char lastPosition = map.nodes[this.position.i][this.position.j].getName1();
        if(dir != Direction.Subway && dir != Direction.Hide) {
            walk(dir);
            isHide = false;
            CUI.hitmanMoveReport(this,lastPosition);
            ///////////////////////
            for (Item item : map.nodes[this.position.i][this.position.j].items)
                if(item instanceof Pot)item.use();
            Thread t = new Thread( new GUI.hitmanMoveHandler(this,dir));
            t.start();
            ///////////////////////
            killEnemies();
        }
        else
        for (Item item : map.nodes[this.position.i][this.position.j].items)
            if(!(item instanceof Stone))item.use();

        for (Item item : map.nodes[this.position.i][this.position.j].items)
            if (item instanceof Target || item instanceof Camera)
                item.use();

    }
    //kills the hitman
    protected void killed(){
        this.isKilled = true;
        map.nodes[this.position.i][this.position.j].prop.remove(this);
        killedString = "A " + killer.getName() + " killed you! :(";
    }
    //returns the hitman's state
    public String toString() {
        return "Hitman" + ((isHide)?"(Hid in pot)":"");
    }
    //returns the string that shows how hitman killed
    public String getKilledString() {
        return killedString;
    }
    //sets the type pf enemy that killed hitman
    public void setKiller(Enemy killer) {
        this.killer = killer;
    }
}
