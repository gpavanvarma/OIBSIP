
import java.util.Random;
import java.util.Scanner;

public class Day8 {
   public static void main(String[] args) {

       int userguess;
       int countguess = 0;
       int comguess;
       int score = 100;
       boolean guess = false;//for while loop
       Scanner ok = new Scanner(System.in);
       Random ra = new Random();
       comguess =ra.nextInt(100)+1;
       while(!guess){
           System.out.println("Enter the  guess Number");
           userguess = ok.nextInt();
           countguess++;
           if(userguess>=1 && userguess<=100){
               if(userguess==comguess){
                   System.out.println("Congratulations! You guessed it! in the " +countguess+" time "+", Thanks for playing!");
                   System.out.println("Your final score is: " + score);
                   break;
               }
               else if(userguess>comguess){
                   System.out.println("Your number too high");
                   score -= 5;
               }
               else{
                   System.out.println("Your number too low");
                   score -= 5;
               }
           }
           else{
               System.out.println("Enter the number between 1 and 100");
           }
       }//end of while
   }//end of main
}
