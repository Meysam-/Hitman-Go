import java.io.*;
import java.util.*;

/**
 * Created by Meisam on 3/29/2015.
 */
public class Map {
    final int sizee = 100;
    //public Node[] nodes;
    public Node[][] nodes;

    Map(File file){
        Node.hashCodeMaker = 0;
        Node.nameMaker = 48;
        CreateNodes(file);
    }
    //returns the containing of the given node name
    public String contains(char name){
        if(getNode(name) != null)
            return getNode(name).toString();
        else
            return "there is no such node :/";
    }
    //prints all map and all entities states
    public String toString(){
        String output = "";
            for(int i=0 ; i < nodes.length ; i++) {
                for (int j = 0; j < nodes[0].length ; j++)
                    if(nodes[i][j] == null)
                        output += "    ";
                    else
                        if(nodes[i][j].right == null)
                            output += nodes[i][j].getName1() + "   ";
                        else
                            output += nodes[i][j].getName1() + "---";
                output += '\n';

                for (int j = 0; j < nodes[0].length ; j++)
                    if(nodes[i][j] != null && nodes[i][j].down != null)
                        output += "|   ";
                    else
                        output += "    ";
                output += '\n';
            }
        for(Node[] node:nodes)
            for(Node nod:node)
                if(nod != null)
                    output += nod.toString();
        return output;
    }
    //build the nodes coonections
    private void CreateNodes(File file) {
        FileInputStream fin = null;
        BufferedInputStream bfin = null;
        List<Integer> iList = new ArrayList<Integer>();
        List<Integer> jList = new ArrayList<Integer>();
        try {
            fin = new FileInputStream(file);
            bfin = new BufferedInputStream(fin);
            Scanner input = new Scanner(bfin);
            input.nextLine();//gets the firs line
            input.next();//gets the "size" word
            Point arraySize = new Point(input.nextInt(),input.nextInt());
            nodes = new Node[arraySize.i+2][arraySize.j+2];
            for (int i = 0; i < arraySize.i+2; i++)
                for (int j = 0; j < arraySize.j+2; j++)
                    nodes[i][j] = null;
            int[][][] connections = new int[arraySize.i+2][arraySize.j+2][4];
            input.next();//gets the first #
            while(input.hasNext()){
                int i = input.nextInt(), j = input.nextInt();
                nodes[i][j] = new Node(this, i, j);
                connections[i][j][0] = input.nextInt();
                connections[i][j][1] = input.nextInt();
                connections[i][j][2] = input.nextInt();
                connections[i][j][3] = input.nextInt();
                String str = input.next();
                while(!str.equals("#")){
                    String type = str.substring(0,2);
                    if(type.equals("Hi"))
                        Game.hitman = new Hitman(new Point(i,j) ,this);
                    else if(type.equals("St"))
                        Game.enemies.add(new StaticEnemy(new Point(i,j),Game.charToDir(str.charAt(2)),this));
                    else if(type.equals("Dy"))
                        Game.enemies.add(new DynamicEnemy(new Point(i,j),Game.charToDir(str.charAt(2)),this));
                    else if(type.equals("Tu"))
                        Game.enemies.add(new TurningEnemy(new Point(i,j),Game.charToDir(str.charAt(2)),this));
                    else if(type.equals("Fl"))
                        Game.enemies.add(new FlushLightEnemy(new Point(i,j),Game.charToDir(str.charAt(2)),this));
                    else if(type.equals("Do"))
                        Game.enemies.add(new DoggyEnemy(new Point(i,j),Game.charToDir(str.charAt(2)),this));
                    else if(type.equals("Sn"))
                        new Stone(new Point(i,j),this);
                    else if(type.equals("Gn"))
                        new Gun(new Point(i,j),this);
                    else if(type.equals("Po"))
                        new Pot(new Point(i,j),this,Game.hitman);
                    else if(type.equals("Cm"))
                        new Camera(new Point(i,j),this);
                    else if(type.equals("Tr"))
                        new Target(new Point(i,j),this);
                    else if(type.equals("Sw")){
                        iList.add(i);
                        jList.add(j);
                        String ii = "", jj = "";
                        for (int k = 2; k < str.length(); k++) {
                            boolean temp = false;
                            if (str.charAt(k) == ',') {
                                temp = true;
                                k++;
                            }
                            if (!temp) ii += str.charAt(k);
                            else jj += str.charAt(k);
                        }
                        iList.add(Integer.parseInt(ii));
                        jList.add(Integer.parseInt(jj));
                    }

                    str = input.next();
                }
            }

            for(int o = 0; o < iList.size(); o += 2){
                boolean subway = true;
                for(Item item:nodes[iList.get(o)][jList.get(o)].items)
                    if(item instanceof Subway) subway = false;
                if(subway) {
                    Subway sw1 = new Subway(new Point(iList.get(o),jList.get(o)), this, Game.hitman);
                    System.out.println(iList.get(o+1) + "  " + jList.get(o+1));
                    Subway sw2 = new Subway(new Point(iList.get(o+1),jList.get(o+1)), this, Game.hitman);
                    Subway.SubwayBuilder(sw1,sw2);
                }
            }


            for (int i = 0 ; i < nodes.length ; i++)
                for (int j = 0 ; j < nodes[0].length ; j++)
                    if (nodes[i][j] != null) {
                        nodes[i][j].createNodeConnections(connections[i][j]);
                        nodes[i][j].picLoader();
                    }
        } catch (FileNotFoundException e) {
            System.out.println("file Not found!");
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                bfin.close();
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }
    //return the shortest path between 2 nodes
    public ArrayList<Node> shortestPath(Node startNode, Node endNode) {
        Queue<Integer> q = new LinkedList<Integer>();
        HashMap<Integer, Node> hashToNode = new HashMap<Integer, Node>();
        HashSet<Integer> visited = new HashSet<Integer>();
        HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();

        hashToNode.put(startNode.hashCode(), startNode);
        visited.add(startNode.hashCode());
        parent.put(startNode.hashCode(), startNode.hashCode());
        q.add(startNode.hashCode());

        while (! q.isEmpty()) {
            Integer hash = q.poll();
            Node node = hashToNode.get(hash);
            ArrayList<Node> children = node.getAdjacentNodes();

            for (Node child: children) {
                if (!visited.contains(child.hashCode())) {
                    visited.add(child.hashCode());
                    parent.put(child.hashCode(), node.hashCode());
                    hashToNode.put(child.hashCode(), child);
                    q.add(child.hashCode());
                }
                if ( child.hashCode() == endNode.hashCode()) {
                    q.clear();
                    break;
                }
            }
        }

        ArrayList<Node> path = new ArrayList<Node>();
        if (visited.contains(endNode.hashCode())) {
            Node finder = endNode;
            Node par = hashToNode.get(parent.get(finder.hashCode()));;
            do {
                path.add(0, finder);
                finder = par;
                par = hashToNode.get(parent.get(finder.hashCode()));
            } while (par.hashCode() != finder.hashCode());

        } else {
            path = null;
        }

        return path;
    }
    //access to nodes by it's name
    public Node getNode(char name){
        for (Node[] node:nodes)
            for(Node nod:node)
                if(nod.getName1() == name)
                    return nod;
        return null;
    }
}
