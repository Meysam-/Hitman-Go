import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Meisam on 3/29/2015.
 */
public class Node extends Button{
    Node right,left,up,down;//the neighbor nodes
    public static int hashCodeMaker = 0;
    public static int nameMaker = 48;
    private int hashCode = -1;//node's hash code
    private char name;
    public Point position;//the state in 2D array
    Set<Person> prop;//the properties of the node
    Set<Item> items;//the items of the node
    Map map;
    Timer timer;

    BufferedImage haImg;

    static boolean gunTurn = false,stoneTurn = false;

    Node(Map map,int i ,int j){
        addMouseListener(this);
        hashCode = hashCodeMaker++;
        if(nameMaker == 58) nameMaker = 97;
        if(nameMaker == 123) nameMaker = 65;
        name = (char)nameMaker++;
        items = new HashSet<Item>();
        prop = new HashSet<Person>();
        position = new Point(i,j);
        this.map = map;
        timer = new Timer(20,this);
        timer.start();

        this.setSize(map.sizee,map.sizee);
        this.setLocation(j*map.sizee,i*map.sizee);
        this.setVisible(true);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
    }

    public void picLoader(){
        ///////////////////////////////////////
        String str = "0";
        if(this.right != null) str += "r";
        if(this.up != null) str += "u";
        if(this.left != null) str += "l";
        if(this.down != null) str += "d";
        str += ".png/";
        try {
            img = ImageIO.read(Node.class.getResource("/Images/" + str));
            adImg = ImageIO.read(Node.class.getResource("/Images/ad" + str));
            mainImg = img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //////////////////////////////////////////
    }

    public void createNodeConnections(int[] con){
        this.right = (con[0] == 1)? map.nodes[position.i][position.j+1] : null;
        this.left = (con[1] == 1)? map.nodes[position.i][position.j-1] : null;
        this.up = (con[2] == 1)? map.nodes[position.i-1][position.j] : null;
        this.down = (con[3] == 1)? map.nodes[position.i+1][position.j] : null;
    }
    //return nodes that can be asscessed in 1 move
    public ArrayList<Node> getAdjacentNodes() {
        ArrayList<Node> adj = new ArrayList<Node>();
        if (up != null)
            adj.add(up);
        if (right != null)
            adj.add(right);
        if (down != null)
            adj.add(down);
        if (left != null)
            adj.add(left);
        return adj;
    }
    //returns the node in direction given if exist or null
    public Node getAdjaccentNode(Direction dir){
        switch (dir){
            case Up: return up;
            case Down: return down;
            case Left: return left;
            case Right: return right;
            default: return null;
        }
    }
    //return the hash code of the node
    public int hashCode() {
        return hashCode;
    }
    //returns the name of the node
    public char getName1() {
        return name;
    }
    //return the node state
    public String toString() {
        String str = "";
        if(!this.prop.isEmpty() || this.items != null)
            str += "\n" + this.getName1() + ":";
        if(!this.prop.isEmpty()){
            Iterator it = this.prop.iterator();
            while(it.hasNext())
                str += "\n\t" + it.next().toString();
        }
        if(this.items != null)
            for(Item item: items)
            str += "\n\t" +  item.toString();

        return str;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //////////////////////////////////////
        for (Node[] nodes:map.nodes)
            for (Node node:nodes)
                if (node != null)
                    if (Game.hitman.isKilled)
                        node.mainImg = node.img;
                    else {
                        if (map.nodes[Game.hitman.position.i][Game.hitman.position.j].getAdjacentNodes().contains(node))
                            node.mainImg = node.adImg;
                        else {
                            node.mainImg = node.img;
                            for(Item item:map.nodes[Game.hitman.position.i][Game.hitman.position.j].items)
                                if(item instanceof Subway){
                                    map.nodes[((Subway)item).outDoor.position.i][((Subway)item).outDoor.position.j].mainImg = map.nodes[((Subway)item).outDoor.position.i][((Subway)item).outDoor.position.j].adImg;
                                }
                        }
                    }
        //////////////////////////////////////
        g.drawImage(mainImg,0,0,map.sizee,map.sizee,null);
        g.drawImage(haImg,map.sizee/4,map.sizee/4,map.sizee/2,map.sizee/2,null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Graphics2D gr = (Graphics2D) this.getGraphics();
        if (gr != null) {
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Node tempNode = map.nodes[Game.hitman.position.i][Game.hitman.position.j];
        if (stoneTurn) {
            for (Item item : tempNode.items)
                if(item instanceof Stone) {
                    if(Game.hitmanAroundNodes().contains(this)) {
                        ((Stone) item).useStone(this);
                        stoneTurn = false;
                        break;
                    }
                }
        }
        else if (gunTurn) {
            for (Item item : tempNode.items)
                if(item instanceof Gun) {
                    if(Game.hitmanAroundNodes().contains(this)) {
                        ((Gun) item).useGun(this);
                        for (Enemy enemy : Game.enemies) {
                            enemy.move();
                        }
                        gunTurn = false;
                        break;
                    }
                }
        }
        else if (/*tempNode.getAdjacentNodes().contains(this) &&*/ !Game.hitman.isKilled) {
            Game.play1(this);
        }
    }
}
