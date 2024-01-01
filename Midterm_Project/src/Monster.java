import java.util.Random;

public class Monster{
    public int health;
    public int critVal;
    public Monster(){
        this.health=100;
    }

    public int Attack(int damage){
        return damage;
    }

    public int takeDamage(int damage){
       return this.health-=damage;

    }

    public int crit(){
        Random rand = new Random();
        int percent = rand.ints(0, 50).findFirst().getAsInt();
        if (percent>=40){
            this.critVal= rand.ints(5, 20).findFirst().getAsInt();
            System.out.println("You crit for "+this.critVal+" bonus damage.");
            return critVal;
        }
        else {
            return 0;
        }
    }

}