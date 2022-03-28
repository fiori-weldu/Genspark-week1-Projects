import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Matchers.*;


public class HangmanTest extends TestCase {
    /*
     ***I Have mixed the Jupiter dependencies with JUnit 4 so that each testing method will be required to start with 'test' for
     *   a better naming convention
     */
    @BeforeEach
    public void beforeEach(){
        Scanner sc = new Scanner(System.in);
    }

    @Test
    public void testPrintWordState() {
        List<Character> arraylist = new ArrayList<>();
        boolean expected = Hangman.printWordState("word",arraylist);
        assertFalse("The method should return false with the bad value given", expected);
    }
    @Test
    public void testGetPlayerGuess() {
        Scanner sc;
        List<Character> arraylist = new ArrayList<>();
        boolean expected = Hangman.printWordState("word",arraylist);
        assertFalse(expected);

    }
    @Test
    public void testPrintHangedMan() {
        //Added the mockito framework to mock the input data to any Integer given.
        assertEquals(5, Hangman.printHangedMan(anyInt()));
    }
    @Test
    public void GuessingHangmanGame() {
        //**** The passing test is a full hangman should be printed in the console ****
        assertEquals("",Hangman.GuessingHangmanGame(new Scanner(System.in),anyString(),anyList(),15));
    }


}