/*
This program plays the game UNO. The rules are the same as regular UNO.
There are +2, +4 (select color), wild (marked by ? cards which select color), skips and reverses.
 */

import org.junit.jupiter.api.Test; //Unit testing attempt

import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String args[]){
        ArrayList<UNOCard> indeck= initDeck(); //creates the initial deck
        ArrayList<UNOCard> deck= shuffleDeck(indeck); //shuffles deck
        ArrayList<UNOCard> discardPile = new ArrayList<UNOCard>();// discard pile

        ArrayList<UNOCard> playerHand = new ArrayList<UNOCard>(); //player hand
        ArrayList<UNOCard> cpuHand = new ArrayList<UNOCard>(); //cpu hand

        int rand;
        for(int i = 1;i<=7;i++){ //Start with 7 cards for both
            rand = (int)Math.random()*deck.size();
            playerHand.add(deck.get(rand));
            deck.remove(rand);
            rand = (int)(Math.random()*deck.size());
            cpuHand.add(deck.get(rand));
            deck.remove(rand);
        }
        UNOCard activeCard;
        activeCard = deck.get(0);
        deck.remove(0);

        int turns = 0; //Set vars
        boolean skipCPU=false, skipPlayer=false;
        boolean playerp2 = false, playerp4=false, cpup2=false, cpup4=false;
        Scanner cardIn = new Scanner(System.in);
        int response;
        if(activeCard.getSpecial().equals("+2")) //Checks if the top card is a +2 or +4
            playerp2=true;
        if(activeCard.getSpecial().equals("+4"))
            playerp4=true;
        while (playerHand.size()>0 || cpuHand.size()>0) { //Main game loop
            turns++;
            System.out.println("Your hand: ");
            printHand(playerHand);
            //System.out.println("The CPU's hand: ");
            //printHand(cpuHand);
            System.out.println("\nThe top card is: ");
            ArrayList<UNOCard> dispTop = new ArrayList<UNOCard>();
            dispTop.add(activeCard);
            printHand(dispTop); //Display top card
            dispTop.remove(0);
            if(!skipPlayer) { //if player is not skipped
                if (playerp2){ //take 2 cards
                    playerHand.add(deck.get(0));
                    deck.remove(0);
                    playerHand.add(deck.get(0));
                    deck.remove(0);
                    playerp2=false;
                    System.out.println("You drew 2 cards.");
                }
                else if (playerp4){ //take 4 cards
                    for(int i = 0;i<=4;i++) {
                        playerHand.add(deck.get(0));
                        deck.remove(0);
                    }
                    System.out.println("You drew 4 cards.");
                    playerp4=false;
                } else {
                    System.out.println("\nWhat card do you want to play. (Select 1-" + playerHand.size() + " or 0 to draw.)");
                    response = cardIn.nextInt();
                    while (response < 0 || response > playerHand.size() || (response != 0 && !playerHand.get(response - 1).compare(activeCard))) { //Check for invalid cases
                        if(response < 0 || response > playerHand.size())
                            System.out.println("That is not in the size of your deck. Try again:  ");
                        else
                            System.out.println("That card will not work. Try again: ");
                        response = cardIn.nextInt();
                    }
                    if (response == 0) { //draw a card
                        playerHand.add(deck.get(1));
                        deck.remove(0);
                    } else {
                        if (playerHand.get(response - 1).getSpecial().equals("Wild") || playerHand.get(response - 1).getSpecial().equals("+4")) { //if wild or +4, set color
                            System.out.println("What color do you want to switch it to (\u001B[31m1-red, \u001B[33m2-yellow, \u001B[32m3-green, \u001B[34m4-blue\u001B[0m): ");
                            Scanner colorIn = new Scanner(System.in);
                            int colorI = colorIn.nextInt();
                            String color = "";
                            switch (colorI) {
                                case 1 -> color = "Red";
                                case 2 -> color = "Yellow";
                                case 3 -> color = "Green";
                                case 4 -> color = "Blue";
                                default -> color = "Red";
                            }
                            playerHand.get(response - 1).setColor(color);
                        }
                        if (playerHand.get(response - 1).getSpecial().equals("+2")) //telling cpu to draw 2
                            cpup2 = true;
                        if (playerHand.get(response - 1).getSpecial().equals("+4")) //telling cpu to draw 4
                            cpup4 = true;
                        if (playerHand.get(response - 1).getSpecial().equals("Skip") || playerHand.get(response - 1).getSpecial().equals("Reverse")) //telling cpu to skip
                            skipCPU = true;

                        discardPile.add(playerHand.get(response - 1)); //adjust cards accordingly
                        activeCard = playerHand.get(response - 1);
                        playerHand.remove(response - 1);
                    }
                }
            } else { //if skipped
                skipPlayer = false;
                System.out.println("You got skipped.");
            }
            if(playerHand.size()==0){ //check win
                System.out.println("You won!\n");
                break;
            }
            if(playerHand.size()==1){ //uno moment
                System.out.println("UNO!\n");
            }

            System.out.print("\u001B[36m");
            if(!skipCPU) { //CPU if not sipped
                if (cpup2){ //cpu draws 2
                    cpuHand.add(deck.get(0));
                    deck.remove(0);
                    cpuHand.add(deck.get(0));
                    deck.remove(0);
                    cpup2=false;
                    System.out.println("The CPU drew 2 cards.\n");
                }
                else if (cpup4){ //cpu draws 4
                    for(int i = 0;i<=4;i++) {
                        cpuHand.add(deck.get(0));
                        deck.remove(0);
                    }
                    System.out.println("The CPU drew 4 cards.\n");
                    cpup4=false;
                } else { //cpu plays
                    for (int i = 0; i < cpuHand.size(); i++) { //cpu loops through cards to see what it can play
                        if (cpuHand.get(i).compare(activeCard)) {
                            discardPile.add(activeCard);
                            activeCard = cpuHand.get(i);
                            cpuHand.remove(i);
                            System.out.println("The CPU plays. It now has " + cpuHand.size() + " cards left.\n");
                            break;
                        } else if (i == cpuHand.size() - 1) { //draws a card of there are none to play
                            cpuHand.add(deck.get(0));
                            deck.remove(0);
                            System.out.println("The CPU drew a card. It now has " + cpuHand.size() + " cards left.\n");
                            break;
                        }
                    }
                    if (activeCard.getSpecial().equals("+2")) //telling cpu to draw 2
                        playerp2 = true;
                    if (activeCard.getSpecial().equals("+4")) //telling cpu to draw 4
                        playerp4 = true;
                    if (activeCard.getSpecial().equals("Skip") || activeCard.getSpecial().equals("Reverse")) //telling cpu to skip
                        skipPlayer = true;
                }
            }else
                skipCPU=false;
            System.out.print("\u001B[0m");
            if(cpuHand.size()==0){ //win cons
                System.out.println("The CPU won!");
                break;
            }
            if(cpuHand.size()==1){ //uno moment
                System.out.println("UNO!\n");
            }
            if(deck.size()<=1){ //Reshuffle discard into deck
                deck= shuffleDeck(discardPile);
                discardPile.clear();
            }

        }
        System.out.println("Thanks for playing! You played for "+turns+" turns.");

    }

    public static ArrayList<UNOCard> initDeck(){ //creates the uno deck with right cards

        ArrayList<UNOCard> deck= new ArrayList<UNOCard>();

        for(int t=1;t<=2;t++){
            for(int num=1; num<=9;num++){//add numbers
                UNOCard cardb = new UNOCard("Blue", num,"");
                deck.add(cardb);
                UNOCard cardr = new UNOCard("Red", num,"");
                deck.add(cardr);
                UNOCard cardg = new UNOCard("Green", num,"");
                deck.add(cardg);
                UNOCard cardy = new UNOCard("Yellow", num,"");
                deck.add(cardy);
            }
            for(int clr=1; clr<=4;clr++){//adds specials
                String color = "";
                switch (clr)
                {
                    case 1 -> color = "Red";
                    case 2 -> color = "Green";
                    case 3 -> color = "Blue";
                    case 4 -> color = "Yellow";
                }
                UNOCard cardr = new UNOCard(color, 0,"Reverse");
                deck.add(cardr);
                UNOCard cards = new UNOCard(color, 0,"Skip");
                deck.add(cards);
                UNOCard card2 = new UNOCard(color, 0,"+2");
                deck.add(card2);

            }
            if(t==2){//ads the 0 cards
                UNOCard cardb0 = new UNOCard("Blue", 0,"");
                deck.add(cardb0);
                UNOCard cardr0 = new UNOCard("Red", 0,"");
                deck.add(cardr0);
                UNOCard cardg0 = new UNOCard("Green", 0,"");
                deck.add(cardg0);
                UNOCard cardy0 = new UNOCard("Yellow", 0,"");
                deck.add(cardy0);
                for(int clr=1; clr<=4;clr++) {//adds specials
                    String color = "";
                    switch (clr) {
                        case 1 -> color = "Red";
                        case 2 -> color = "Green";
                        case 3 -> color = "Blue";
                        case 4 -> color = "Yellow";
                    }
                    UNOCard cardy1 = new UNOCard(color, 0,"Wild");
                    deck.add(cardy1);
                    UNOCard cardy = new UNOCard(color, 0,"+4");
                    deck.add(cardy);
                }
            }
        }
        return deck;
    }

    public static ArrayList<UNOCard> shuffleDeck(ArrayList<UNOCard> deck){ //shuffles the deck
        ArrayList<UNOCard> shuffled = new ArrayList<UNOCard>();
        int rand;
        while(deck.size()>0){
            rand = (int)(Math.random()*deck.size());
            shuffled.add(deck.get(rand));
            deck.remove(rand);
        }

        return shuffled;
    }

    public static void printHand(ArrayList<UNOCard> hand){ //displays the hand
        if(hand.size()>=10){ //prints the numbers for 10 and greater
            for(int i = 1; i<10;i++){
                System.out.print("  "+(i)+"   ");
            }
            for(int i = 10; i<=hand.size();i++){ //adjust for char size
                System.out.print("  "+(i)+"  ");
            }
            System.out.println();
        }else if (hand.size()>5) { //dont add numbers if hand is small
            for (int i = 1; i <= hand.size(); i++) {
                System.out.print("  " + (i) + "   ");
            }
            System.out.println();
        }
            for(UNOCard hnd: hand) { //top line
            String clr = hnd.getColor();
            switch (clr) {
                case "Red":
                    System.out.print("\u001B[31m" + "| ‾ ‾ ‾|");
                    break;
                case "Green":
                    System.out.print("\u001B[32m" + "| ‾ ‾ ‾|");
                    break;
                case "Blue":
                    System.out.print("\u001B[34m" + "| ‾ ‾ ‾|");
                    break;
                case "Yellow":
                    System.out.print("\u001B[33m" + "| ‾ ‾ ‾|");
            }
            System.out.print(" ");
        }
        System.out.print("\n");
        for(UNOCard hand2: hand) { // mid line with symbols
            String disp;
            String clr = hand2.getColor();
            if(hand2.getSpecial().equals("Wild")) {
                disp = "| ? |";
                //clr = "Purple";
            }
            else if(hand2.getSpecial().equals("+2"))
                disp = "|  +2  |";
            else if(hand2.getSpecial().equals("+4")) {
                disp = "|  +4  |";
                //clr="Purple";
            }
            else if(hand2.getSpecial().equals("Reverse"))
                disp = "|  \uD83D\uDD04   |";
            else if(hand2.getSpecial().equals("Skip"))
                disp = "|  \uD83D\uDEAB   |";
            else
                disp = "| " + hand2.getNumber() + " |";
            switch (clr) {
                case "Red":
                    System.out.print("\u001B[31m" + disp);
                    break;
                case "Green":
                    System.out.print("\u001B[32m" + disp);
                    break;
                case "Blue":
                    System.out.print("\u001B[34m" + disp);
                    break;
                case "Purple":
                    System.out.print("\u001B[36m" + disp);
                case "Yellow":
                    System.out.print("\u001B[33m" + disp);
            }
            System.out.print(" ");
        }
        System.out.print("\n");
        for(UNOCard hnd: hand) { //bottom line
            String clr = hnd.getColor();
            switch (clr) {
                case "Red":
                    System.out.print("\u001B[31m" + "|___|");
                    break;
                case "Green":
                    System.out.print("\u001B[32m" + "|___|");
                    break;
                case "Blue":
                    System.out.print("\u001B[34m" + "|___|");
                    break;
                case "Yellow":
                    System.out.print("\u001B[33m" + "|___|");
            }
            System.out.print("\u001B[0m"+" ");
        }
        System.out.println("\u001B[0m"); //reset color
    }

}
