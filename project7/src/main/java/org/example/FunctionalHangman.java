package org.example;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalHangman {
        private final Scanner scanner = new Scanner(System.in);
        private int currentState;
        private List<String> boardStates;
        private final String name;
        private int score;

        {
            try {
                boardStates = Files.readAllLines((Path.of("states.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        private List<String> words;

        {
            try {
                words = Files.readAllLines((Path.of("words.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private List<String[]> highScores;

        {
            try {
                highScores = Files.lines(Path.of("highScore.txt"))
                        .map(str->str.split(","))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private ArrayList<Character> guesses = new ArrayList<>();
        private String currentWord = "";
        private boolean gameOver = false;
        private boolean hasWon = true;


        private void checkHighScore(int score) {
            if(
                    highScores.size()!=0
                            && highScores.stream().anyMatch(s -> s[1].equals(name))

                            &&score >Integer.parseInt(highScores.stream().filter(s->s[1].equals(name)).collect(Collectors.toList()).get(0)[0])
            ){
                highScores.stream().filter(s->s[1].equals(name)).collect(Collectors.toList()).get(0)[0]=String.valueOf(score);
                try {
                    Files.write(Path.of("highScore.txt")
                            ,highScores.stream().map(a->a[0]+","+a[1]).collect(Collectors.toList()),
                            StandardOpenOption.CREATE
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(highScores.size()==0|| highScores.stream().noneMatch(s -> s[1].equals(name))){
                highScores.add(new String[]{String.valueOf(score),name});
                try {
                    Files.write(Path.of("highScore.txt")
                            ,highScores.stream().map(a->a[0]+","+a[1]).collect(Collectors.toList()),
                            StandardOpenOption.CREATE
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("OUR CURRENT HIGH SCORES");
            System.out.println("***********************");
            highScores.stream()
                    .map(Arrays::asList)
                    .sorted((s1,s2)-> {
                        int n1 = Integer.parseInt(s1.get(0));
                        int n2 = Integer.parseInt(s2.get(0));
                        return Integer.compare(n2,n1);
                    })
                    .limit(10)
                    .forEach(s->{
                        System.out.println(s.get(1) +  "   -----   "+ s.get(0)+"\n");
                    });


        }

        public void playAgain() {


            System.out.print("\nPlay Again? (y or n)-->");
            try {
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("y")) {

                    guesses = new ArrayList<>();
                    startGame();
                } else if (input.equals("n")) {
                    System.out.println("GOODBYE!");
                } else {
                    throw new Exception("Please select either y or n");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                playAgain();
            }


        }

        public void getNewWord() {
            int index = (int) (Math.random() * words.size() - 1);
            setCurrentWord(words.get(index));
        }

        public int printBoard(int state) {
            hasWon = true;
            int printedState=-1;
            if (state >= 0 && state < boardStates.size()) {
                System.out.println();
                System.out.println();

                Arrays.stream(boardStates.get(currentState).split(",")).forEach(System.out::println);
                if (state == 6) {
                    checkHighScore(score);
                    System.out.println("You Suck, " + name + "!  The Answer was " + currentWord);
                    gameOver = true;



                }
                printedState=state;


                Stream.of(currentWord.toLowerCase().split(""))
                        .forEach(c -> {

                            if (guesses.contains(c.charAt(0))) {
                                System.out.print(" " + c + " ");
                            } else {
                                System.out.print(" _ ");
                                hasWon = false;
                            }


                        });
                System.out.println();

                if (!hasWon && !gameOver) {
                    System.out.print("\n");
                    System.out.println("score:" + score);
                    System.out.print(name + ", Make Your Guess-->");
                } else if(hasWon){
                    System.out.println();
                    System.out.println("You Win!!!");
                    score += 500 * currentWord.length();
                    System.out.println("score:" + score);
                    checkHighScore(score);
                    gameOver = true;
                }



            }
            return printedState;

        }

        public char getGuess() {
            try {
                String input = scanner.nextLine().toLowerCase();
                if (input.length() != 1) {
                    throw new Exception("Invalid Entry, please enter a letter");
                }

                char guess = input.charAt(0);

                if (!Character.isLetter(guess)) {
                    throw new Exception("Invalid Entry, please enter a letter");
                }

                if (guesses.contains(guess)) {
                    throw new Exception("You've already guessed '" + guess + "'");
                }

                if (!currentWord.contains(String.valueOf(guess))) {
                    currentState++;
                } else {

                    for (char c : currentWord.toCharArray()) {
                        if (c == guess) {
                            score += 100 * currentWord.length();
                        }
                    }
                }
                score -= 100;
                return guess;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Make Your Guess-->");
                return getGuess();
            }

        }

        public void startGame() {
            System.out.println("Welcome to Version 2 of the culturally inappropriate game where we");
            System.out.println("hang a man if his spelling skills aren't up to snuff!");
            System.out.println("**********************HANGMAN************************");
            setCurrentState(0);
            getNewWord();
            score = 1000;
            gameOver = false;
            while (true) {
                printBoard(getCurrentState());

                if (gameOver) {
                    break;
                } else {
                    guesses.add(getGuess());
                }


            }

            playAgain();

        }

        public String getCurrentWord() {
            return currentWord;
        }

        public void setCurrentWord(String currentWord) {
            this.currentWord = currentWord;
        }

        public int getCurrentState() {
            return currentState;
        }

        public void setCurrentState(int currentState) {
            this.currentState = currentState;
        }

        public HangMan() {
            currentState = 0;

            System.out.print("Enter Your name-->");
            name = scanner.nextLine();


        }
        public HangMan(boolean debug) {
            currentState = 0;
            if(!debug){
                System.out.print("Enter Your name-->");
                name = scanner.nextLine();
            }else{
                name="Perry";
            }



        }

