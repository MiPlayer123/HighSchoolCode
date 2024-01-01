import javax.swing.*;
import java.util.Random;

public class UNOCard {

    public String color;
    public int number;
    public String special;
    public UNOCard() //For random init (not used)
    {
        Random rand = new Random();
        int colorNum = rand.nextInt(5) + 1;
        switch (colorNum)
        {
            case 1 -> color = "Red";
            case 2 -> color = "Green";
            case 3 -> color = "Blue";
            case 4 -> color = "Yellow";
            case 5 -> color = "Wild";
        }
        if (colorNum != 5) // If not a wild card
            number = rand.nextInt(12) + 1;
        else
            number = rand.nextInt(2) + 13;
        switch (number)
        {
            case 10 -> special = "Reverse";
            case 11 -> special = "Skip";
            case 12 -> special = "+2";
            case 13 -> special = "Wild";
            case 14 -> special = "+4";
            default -> special = "";
        }
    }
    public UNOCard(String clr, int num, String s) //For manual init (used for card decks)
    {
        color = clr;
        number = num;
        special = s;
    }

    public boolean compare(UNOCard other){ //compares the cards
        if((this.color.equals(other.color) || this.number==other.number) && this.special.equals("") && other.special.equals("")){
            return true; //same color or number with no special
        }
        else if(this.color.equals(other.color) || (this.special.equals("+4") || this.special.equals("Wild") || (this.special.equals(other.special)&&!this.special.equals("")))){
            return true; //same color
        }
        return false;
    }

    public void setColor(String color){
        if (special.equals("Wild") || special.equals("+4")){
            this.color=color;
        }
    }

    public String toString(){
        if(special.equals(""))
            return color+" "+number;
        else
            return color+" "+special;
    }

    public String getColor(){
        return color;
    }

    public String getSpecial(){
        return special;
    }

    public int getNumber(){
        return number;
    }
}
