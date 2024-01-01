import java.util.Random;

public class Wolf extends Monster {
    public Wolf(){}
    public int scratch(){
        Random rand = new Random();
        return super.Attack(rand.ints(0, 10).findFirst().getAsInt());
    }
    public int takeDamage(){
        Random rand = new Random();
        return (super.takeDamage(rand.ints(0, 15).findFirst().getAsInt())+super.crit());
    }
}
