package org.example;



import java.util.Locale;
        import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Game game = new Game();
        Human p = game.getPlayer();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println(game.toString());
            System.out.println("w/a/s/d to move");
            String input = scanner.next();

            switch (input.toLowerCase(Locale.ROOT)) {
                case "w":
                    if (game.getEntity(p.getX(), p.getY() - 1) != null)
                        break;
                    if (p.getY() > 0)
                        p.setY(p.getY() - 1);
                    break;
                case "a":
                    if (game.getEntity(p.getX() - 1, p.getY()) != null)
                        break;
                    if (p.getX() > 0)
                        p.setX(p.getX() - 1);
                    break;
                case "s":
                    if (game.getEntity(p.getX(), p.getY() + 1) != null)
                        break;
                    if (p.getY() < Game.HEIGHT - 1)
                        p.setY(p.getY() + 1);
                    break;
                case "d":
                    if (game.getEntity(p.getX() + 1, p.getY()) != null)
                        break;
                    if (p.getX() < Game.WIDTH - 1)
                        p.setX(p.getX() + 1);
                    break;
            }
            game.update();
        } while (game.isRunning());

        System.out.println("You died!");
        System.out.println((game.getWaves() - 1) + " waves completed!");
    }
}
