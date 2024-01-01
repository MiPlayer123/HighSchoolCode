import java.util.Random;

public class Zombie extends Monster {
    public Zombie(){}
    public int takeDamage(){
        Random rand = new Random();
        return super.takeDamage(rand.ints(0, 50).findFirst().getAsInt()) +super.crit();
    }
    public int bite(){
        Random rand = new Random();
        return super.Attack(rand.ints(0, 40).findFirst().getAsInt());
    }
}
