import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.Random;
import java.lang.Math;
import java.awt.Canvas;
import java.awt.Graphics;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

public class Treasure {
    public static class TreasureHunt{

        public TreasureHunt(int xtreas, int ytreas,int xBomb, int yBomb,int xBomb2, int yBomb2, int xBomb3, int yBomb3){
            xTreas = xtreas;
            yTreas = ytreas;
            xbomb = xBomb;
            ybomb = yBomb;
            xbomb2 = xBomb2;
            ybomb2= yBomb2;
            xbomb3 = xBomb3;
            ybomb3 = yBomb3;
        }

        public static void PrintBoard(String grid[][]){
            for (int i=0;i< grid.length;i++) {
                for (int j = 0; j < grid.length; j++) {
                    /*if (grid.length>=10 && ((i<=9 && j==0) || (j>0 && i>0) || (i==0 && j<10))){ //Formatting logic
                        System.out.print(grid[i][j]+"  ");
                    } else{
                        System.out.print(grid[i][j] + " ");
                    }*/
                    if (grid[i][j].length()==2){
                        System.out.print(grid[i][j] +" ");
                    } else{
                        System.out.print(grid[i][j] + "  ");
                    }
                }
                System.out.println("");
            }
        }
        public static boolean CheckTreasure(int xGuess,int yGuess){
            if (xTreas == xGuess && yTreas == yGuess){
                return true;
            }
            else{return false;}
        }
        public static boolean checkBombs(int xguess, int yguess){
            if ((xbomb == xguess && ybomb == yguess) || (xbomb2 == xguess && ybomb2 == yguess)||(xbomb3 == xguess && ybomb3 == yguess)){
                return true;
            }
            else{return false;}
        }
        public static int DistanceToTreasure(int xGuess,int yGuess){
            return Math.abs((xTreas-xGuess))+Math.abs((yTreas-yGuess));
        }
        public static int xTreas;
        public static int yTreas;
        public static int xbomb;
        public static int ybomb;
        public static int xbomb2;
        public static int ybomb2;
        public static int xbomb3;
        public static int ybomb3;
    }

    public static void main(String args[]){
        Scanner getSize = new Scanner(System.in);
        System.out.println("What size grid do you want? (Recommended: 8)");
        int gridSize;
        try {
            gridSize = getSize.nextInt();
        } catch (Exception e){
            System.out.println("That is not valid, setting the size to 8");
            gridSize = 8;
        }
        String grid[][];
        grid = new String[gridSize+1][gridSize+1];
        for (int i=0;i< gridSize+1;i++) { //Dynamic grid size
            for (int j = 0; j < gridSize+1; j++) {
                if(i==0){
                    grid[i][j]= String.valueOf(j);
                }
                else {
                    if (j == 0) {
                        grid[i][j] = String.valueOf(i);
                    } else {
                        grid[i][j] = "O";
                    }
                }
            }
        }
        Scanner getPlayer = new Scanner(System.in);
        System.out.println("Enter the number of players: ");
        int playerNum = getPlayer.nextInt();

        /* Graphic attempt
        JFrame window = new JFrame();
        window.setSize(480,480);
        window.setTitle("Treasure Hunt");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(false); // rest dont work
        */
        int guessX;
        int guessY;

        int tries=1;

        int getTreasureX = 0;
        int getTreasureY = 0;
        int getBomb[] = {0,0};
        int getBomb2[] = {0,0};
        int getBomb3[] = {0,0};

        //Seperare initalization for player 1 and 2
        if(playerNum==1) {
            getTreasureX = randgrid(gridSize);
            getTreasureY = randgrid(gridSize);
            getBomb = randbomb(getTreasureX, getTreasureY, gridSize);
            getBomb2 = randbomb(getTreasureX, getTreasureY, gridSize);
            getBomb3 = randbomb(getTreasureX, getTreasureY, gridSize);
        }else if(playerNum==2){
            Scanner coords = new Scanner(System.in);
            System.out.println("Player 1's turn. Player 2 look away.");
            System.out.println("Treasure X coordinate: ");
            getTreasureX = coords.nextInt();
            System.out.println("Treasure Y coordinate: ");
            getTreasureY = coords.nextInt();

            System.out.println("Bomb 1 X coordinate: ");
            getBomb[0] = coords.nextInt();
            System.out.println("Bomb 1 Y coordinate: ");
            getBomb[1] = coords.nextInt();
            System.out.println("Bomb 2 X coordinate: ");
            getBomb2[0] = coords.nextInt();
            System.out.println("Bomb 2 Y coordinate: ");
            getBomb2[1] = coords.nextInt();
            System.out.println("Bomb 3 X coordinate: ");
            getBomb3[0] = coords.nextInt();
            System.out.println("Bomb 3 Y coordinate: ");
            getBomb3[1] = coords.nextInt();

            System.out.println("Player 2's turn.");

        }else {
            System.out.println("Not valid player number.");
        }

        //System.out.println("TX: "+getTreasureX+" TY: "+getTreasureY+" B1X: "+getBomb[0]+" B1Y: "+getBomb[1]);

        TreasureHunt Hunt = new TreasureHunt(getTreasureX,getTreasureY,getBomb[0],getBomb[1],getBomb2[0],getBomb2[1],getBomb3[0],getBomb3[1]);

        while (true) {
            System.out.println("");
            Hunt.PrintBoard(grid);

            Scanner get = new Scanner(System.in);
            System.out.println("\nWhat is the X value: ");
            guessX = get.nextInt();
            System.out.println("What is the Y value: ");
            guessY = get.nextInt();

            if (Hunt.checkBombs(guessX, guessY)) {
                System.out.println("Unlucky, you found a bomb and died. Better luck next time.");
                System.exit(0);
            }

            if (Hunt.CheckTreasure(guessX, guessY)) {
                System.out.println("Congrats, you found the treasure! It took "+tries+" tries.");
                grid[getTreasureY][getTreasureX] = "T";
                Hunt.PrintBoard(grid);
                System.exit(0);
            }

            grid[guessY][guessX] = "X";
            tries++;

            System.out.println("You are "+Hunt.DistanceToTreasure(guessX,guessY)+" spots off.");
        }

    }
    public static int randgrid(int end){
        Random rand = new Random();
            return rand.ints(1, end).findFirst().getAsInt();
    }
    public static int[] randbomb(int xTreas, int yTreas, int end){
        int x = randgrid(end);
        int y = randgrid(end);
        while ((x==xTreas) && (y==yTreas)){
            x = randgrid(end);
            y = randgrid(end);
        }
        int[] retval = {x,y};
        return retval;
    }
/* No workey for da display
    public class box extends JPanel {
        public void paintComponent(Graphics g, int x, int y, int w, int h) {
            super.paintComponent(g);

            g.fillRect(x, y, w, h);
        }
    }

 */
}
