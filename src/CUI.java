/**
 * Created by Meisam on 4/19/2015.
 */
public class CUI {
    public static void cameraReport(Camera camera){
        System.out.println("Hitman seen in Camera (" + camera.map.nodes[camera.position.i][camera.position.j].getName1() + ")");
    }
    public static void enemyMoveReport(Enemy enemy , char lastPosition){
        System.out.println(enemy.getName() + ": " + lastPosition + "->" + enemy.map.nodes[enemy.position.i][enemy.position.j].getName1());
        }
    public static void hitmanHideReport(Hitman hitman){
//        System.out.println("Hitman: Hid in " + hitman.map.nodes[hitman.position.i][hitman.position.j].getName1() + "'s Pot");
    }
    public static void hitmanMoveReport(Hitman hitman , char lastPosition){
        System.out.println("Hitman: " + lastPosition + "->" + hitman.map.nodes[hitman.position.i][hitman.position.j].getName1());
    }
    public static void emergencyReport(Enemy enemy){
        System.out.println(enemy);
    }
    public static void enemyKillReport(Enemy enemy){
        System.out.println(enemy.getName() + ": Killed by Hitman");
    }
    public static void turnReport(TurningEnemy turningEnemy){
        System.out.println(turningEnemy.name + "(" + turningEnemy.map.nodes[turningEnemy.position.i][turningEnemy.position.j].getName1() + ") turned " + turningEnemy.direction);
    }
    public static void trowStoneReport(char destination){
        System.out.println("Stone --> " + destination);
    }
    public static void whereToShoot(String s){
        System.out.println("Shoot to:" + s);
    }
    public  static void commandError(){
        System.out.println("ONE OF THE CHOICES!");
    }
    public static void whereToThrowStone(String s){
        System.out.println("Throw Stone to:" + s);
    }

    public static void whereToGo(String s){
        System.out.println(s);
    }
}
