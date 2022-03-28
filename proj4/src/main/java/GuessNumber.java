import java.util.Scanner;

public class GuessNumber {
    public static  void  main(String args[]){
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello! What is your name");
        String userName=scan.nextLine();
        int count;

        boolean keepPlaying=true;
        try {
            while (keepPlaying) {
                System.out.println("Well " + userName + " ,I am thinking of a number between 1 and 20\n Take a guess.");
                int rand = (int) (Math.random() * 20) + 1;
                int x = scan.nextInt();
                count = 1;
                while (rand != x) {
                    if (count >= 6) {
                        System.out.println("Oops! you didn't guess the number even after I gave you 6 tries.");
                        break;
                    } else if (x < rand) {
                        System.out.println("your guess is too low");
                    } else System.out.println("your guess is too high");
                    count++;
                    System.out.println(" Enter your guess again");
                    x = scan.nextInt();
                }
                if (count > 6) {
                    System.out.println("I am sorry");
                } else {
                    System.out.println("Good job, " + userName + "! You guessed my number in " + count + " guesses!");
                }
                System.out.println("do you want to play again");
                String response = scan.next();
                if (response.startsWith("n") || response.startsWith("N")) {
                    keepPlaying = false;
                } else keepPlaying = true;
            }
        }
        catch(Exception e){
            System.out.println("Some thing goes wrong! Please use valid number");
        }
    }
}
