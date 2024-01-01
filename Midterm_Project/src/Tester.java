import java.util.Random;
import java.util.Scanner;

public class Tester {
    public static void main(String args[]){
        System.out.println("You are in a life or death situation. You have to win this fight.");
        System.out.println("There are two monsters. You must kill them to win.\nYou have a 20% to crit and do 5-20 bonus damage. " +
                "\nYou may heal after you kill a monster. \nYou have 100 health and the monsters each have 100 health.");
        System.out.println("Good luck. You need to live.\n");

        Zombie zombie = new Zombie();
        Wolf wolf = new Wolf();
        Random rand = new Random();

        int playerHealth = 100; //75, worse than riot games balancing
        int bonusHealth = 0;
        boolean wolfStat = true;
        boolean zombieStat = true;
        while ((wolf.health > 0 || zombie.health > 0)){
            System.out.println("Would you like to attack the zombie (1) or wolf (2): ");
            Scanner getAttack = new Scanner(System.in);
            int monsterNum = getAttack.nextInt();
            if(monsterNum==1){
                if (zombie.takeDamage()<=0 && zombieStat){
                    bonusHealth = rand.ints(-1, 10).findFirst().getAsInt();
                    playerHealth+=bonusHealth;
                    System.out.println("You have killed the zombie. You have gained "+bonusHealth+" health.");
                    zombieStat = false;
                }else if (zombieStat){
                    System.out.println("The zombie has "+zombie.health+" health.");
                    playerHealth-=zombie.bite();
                    healthCheck(playerHealth);
                    System.out.println("The zombie strikes back. You now have "+playerHealth+" health.");
                }
            }
            else if(monsterNum==2){
                if (wolf.takeDamage()<=0 && wolfStat){
                    bonusHealth = rand.ints(-1, 10).findFirst().getAsInt();
                    playerHealth+=bonusHealth;
                    System.out.println("You have killed the wolf. You have gained "+bonusHealth+" health.");
                    wolfStat = false;
                }else if(wolfStat){
                    System.out.println("The wolf has " + wolf.health + " health.");
                    playerHealth-=wolf.scratch();
                    healthCheck(playerHealth);
                    System.out.println("The wolf strikes back. You now have "+playerHealth+" health.");
                }
            }
            else {
                System.out.println("Not valid bud.");
            }
            healthCheck(playerHealth);
        }
        System.out.println("Congrats, you live to see another day.");
    }
    public static void healthCheck(int health){
        if (health<=0){
            System.out.println("You died.");
            System.exit(1);
        }
    }
}
