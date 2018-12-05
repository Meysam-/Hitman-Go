/**
 * Created by Meisam on 5/22/2015.
 */
public class Point {
    int i, j;
    Point(){
        this(0,0);
    }
    Point(int num){
        this(num,num);
    }
    Point(int i, int j){
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return "( " + this.i + " , " + this.j + " )";
    }
}
