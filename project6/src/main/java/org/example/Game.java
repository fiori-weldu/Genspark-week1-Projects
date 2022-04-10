package org.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

public class Game {

        public static final int WIDTH = 10;
        public static final int HEIGHT = 10;

        private static Random random = new Random();


        private ArrayList<boardObj> world;
        private Human player;
        private int wave;

        public Game() {
            world = new ArrayList<>();
            player = new Human(random.nextInt(WIDTH), random.nextInt(HEIGHT));
            world.add(player);
            wave = 0;
            nextWave();
        }


        public boardObj getEntity(int x, int y) {
            Optional<boardObj> entity = world.stream().filter(e -> e.getX() == x && e.getY() == y).findAny();
            if (!entity.isPresent())
                return null;
            return entity.get();
        }


        public void update() {

            world.stream().filter(x -> x instanceof Goblin).forEach(x -> findPath(x.getX(), x.getY(), player, x, new HashMap<>(), 0));

            Optional<boardObj> enemy = world.stream().filter(x -> x != player && x instanceof Goblin && x.getX() == player.getX() && x.getY() == player.getY()).findAny();
            if (enemy.isPresent()) {
                System.out.println("The fight is on...");
                Goblin goblin = (Goblin) enemy.get();
                goblin.setHealth(goblin.getHealth() - (float)Math.random() * 75.0f);
                System.out.println("You hit the goblin! " + goblin.getHealth() + " HP remaining.");

                if (goblin.getHealth() < 0) {
                    System.out.println("You killed the goblin!");
                    world.remove(goblin);

                    if (hasWon()) {
                        System.out.println("Congrats! You killed the goblins!");

                        nextWave();
                    }
                    return;
                }

                player.setHealth(player.getHealth() - (float)Math.random() * 50.0f);
                System.out.println("You've been hit! " + player.getHealth() + " HP remaining.");
            }
        }


        public boolean isRunning() {
            return player.getHealth() > 0;
        }


        public boolean hasWon() {
            return world.stream().filter(x -> x instanceof Goblin).findAny().isPresent();
        }

        public int getWaves() {
            return this.wave;
        }


        public void nextWave() {

            world.clear();
            world.add(player);
            wave++;

            for (int i = 0; i < wave; i++) {
                Goblin g = new Goblin(random.nextInt(WIDTH), random.nextInt(HEIGHT));

                while (getEntity(g.getX(), g.getY()) != null)
                    g = new Goblin(random.nextInt(WIDTH), random.nextInt(HEIGHT));
                world.add(g);
            }

            for (int i = 0; i < 10; i++) {
                Land g = new Land(random.nextInt(WIDTH), random.nextInt(HEIGHT));
                while (getEntity(g.getX(), g.getY()) != null)
                    g = new Land(random.nextInt(WIDTH), random.nextInt(HEIGHT));
                world.add(g);
            }
        }

        public Human getPlayer() {
            return this.player;
        }

        public int findPath(int x, int y, boardObj target, boardObj entity, HashMap<Integer, Integer> path, int distance) {


            if (path.containsKey(y * WIDTH + x)) {

                if (path.get(y * WIDTH + x) < distance)
                    return -1;

                path.remove(y * WIDTH + x);
            }

            path.put(y * WIDTH + x, distance);


            if (getEntity(x, y) == target)
                return distance;

            if (x < 0 || y < 0 || x == WIDTH || y == HEIGHT)
                return -1;

            if (getEntity(x, y) != null && getEntity(x, y) != entity)
                return -1;

            int lowest = -1;
            int current_length;
            if ((current_length = findPath(x + 1, y, target, entity, path, distance + 1)) != -1) {

                entity.setX(x + 1);
                entity.setY(y);
                lowest = current_length;
            }

            if ((current_length = findPath(x - 1, y, target, entity, path, distance + 1)) != -1 && distance < current_length) {

                entity.setX(x - 1);
                entity.setY(y);
                lowest = current_length;
            }

            if ((current_length = findPath(x, y + 1, target, entity, path, distance + 1)) != -1 && distance < current_length) {

                entity.setX(x);
                entity.setY(y + 1);
                lowest = current_length;
            }

            if ((current_length = findPath(x, y - 1, target, entity, path, distance + 1)) != -1 && distance < current_length) {

                entity.setX(x);
                entity.setY(y - 1);
                lowest = current_length;
            }

            return lowest;
        }


        @Override
        public String toString() {
            String output = "";
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {

                    if (getEntity(x, y) == null) {
                        output += "\u2022 ";
                        continue;
                    }
                    output += getEntity(x, y).toString() + " ";
                }
                output += "\n";
            }
            return output;
        }
    }

