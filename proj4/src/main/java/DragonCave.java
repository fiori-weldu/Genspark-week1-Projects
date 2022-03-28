import java.util.Scanner;

public class DragonCave {
    public static void main(String[] args){
        int x= (int)(Math.random()*2)+1;
        Scanner scan= new Scanner(System.in);
        System.out.println("You are in a land full of dragons. In front of you,\n" +
                "you see two caves. In one cave, the dragon is friendly\n" +
                "and will share his treasure with you. The other dragon\n" +
                "is greedy and hungry and will eat you on sight.\n" +
                "Which cave will you go into? (1 or 2)");
        try {
            int choice = scan.nextInt();
            if (choice == x) {
                System.out.println("You approach the cave...\n" +
                        "It is dark and spooky...\n" +
                        "A large dragon jumps out in front of you! He opens his jaws and...\n" +
                        "Gobbles you down in one bite!");
            }
            else if (choice > 2 || choice < 1) {
                System.out.println("Oops! enter valid number! \n" +
                        "Enter 1 or 2 only!");
            }
            else {
                System.out.println("You approached the cave\nA friendly dragon appears...\nHe shares his treasure with you\nCongratulations you got your treasure!");
            }
        }
        catch (Exception e){
            System.out.println("Oops! enter valid number! \n" +
                    "Enter 1 or 2 only!");

        }
    }
}
