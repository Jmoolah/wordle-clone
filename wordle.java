
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class wordle {

    public static final String FILENAME = "wordle.txt";
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public static HashSet<String> greychars = new HashSet<>();
    public static HashSet<String> yellowChars = new HashSet<>();
    public static HashSet<String> greenChars = new HashSet<>();
    public static HashSet<String> unsedLetters = new HashSet<>();
    public static ArrayList<String> letterColors = new ArrayList<>();
    public static ArrayList<String> guessList = new ArrayList<>();

    public static HashMap<Character, Integer> correctLetterLocations = new HashMap<>();
    public static HashSet<Character> correctWordCharcters = new HashSet<>();
    public static StringBuilder correctWord = new StringBuilder();
    public static ArrayList<String> wordList = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);
    public static boolean isGuesswrong = true;
    public static byte guessCount = 0;

    public static void main(String[] args) throws IOException {
        System.out.print("\u001B[2J"); // ANSI code: clear entire screen

        for (char c = 'A'; c <= 'Z'; c++) {
            //System.out.print(c + " ");
            unsedLetters.add(String.valueOf(c));
            letterColors.add(Character.toString(c));

        }

        makeWordist(FILENAME);
        getACorrectWord();

        int update = 1;
        while (isGuesswrong && guessCount != 5) {
            printLetterColors(update);
            String guess = takeGuess();
            checkGuessAndcorrectwordAreEqual(guess);
            char[] userGuessChararr = makeUserCharArrs(guess);
            char[] correctWordCharArr = makeCorrectWordCharArrs(correctWord.toString());
            addColorToGuessWord();
            checkGuessAndcorrectwordAreEqual(guess);
            compareLetters(userGuessChararr, correctWordCharArr);

            guessCount++;
            update++;
        }
        if (isGuesswrong) {
            System.out.println("\nCorrect Word: " + correctWord);
        }
        System.out.println(RESET);
    }

    public static void makeWordist(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line);
            }
        }
    }

    public static void getACorrectWord() throws IOException {
        int size = wordList.size();
        int randomWord = (int) (Math.random() * size); // 0 to 100
        if (correctWord.isEmpty()) {
            correctWord.append(wordList.get(randomWord).toUpperCase());
            int i = 0;
            for (char c : correctWord.toString().toCharArray()) {
                correctWordCharcters.add(c);
                correctLetterLocations.put(c, i);
                i++;
            }

        } else if (!correctWord.isEmpty()) {
            correctWord.setLength(0);
            correctWord.append(randomWord);
            int i = 0;
            for (char c : correctWord.toString().toCharArray()) {
                correctWordCharcters.add(c);
                correctLetterLocations.put(c, i);
                i++;
            }
        }

    }

    public static String takeGuess() {

        boolean isGuessValid = false;
        String guessWhile = "";
        while (!isGuessValid) {
            guessWhile = "";
            guessWhile += sc.next().toLowerCase();

            if (wordList.contains(guessWhile)) {
                guessList.add(guessWhile);
                break;

            } else {
                System.out.print("\033[F");   // move cursor UP one line
                System.out.print("\033[2K");  // clear that line
            }

        }
        return guessWhile.toUpperCase();

    }

    public static void printLetterColors(int updateRange) {
        System.out.print("\u001B[H"); // ANSI code: move cursor to top-left corner
        // Move cursor to start of line and clear it
        System.out.print("\r\033[2K");

        System.out.println("Unused Letter:\n" + "\r\033[2K" + unsedLetters);
        System.out.print("Wrong Letters:\n" + greychars);
        System.out.print("\033[" + updateRange + "B\033[0G");

    }

    public static char[] makeUserCharArrs(String userGuess) {
        char[] guessCharArr = userGuess.toCharArray();
        return guessCharArr;
    }

    public static char[] makeCorrectWordCharArrs(String Correctword) {
        char[] CorrectwordCharArr = Correctword.toCharArray();
        return CorrectwordCharArr;
    }

    public static void addColorToGuessWord() {
        System.out.print("\033[F");   // move cursor UP one line
        System.out.print("\033[2K");  // clear that line
    }

    public static void checkGuessAndcorrectwordAreEqual(String guess) {
        if (guess.equals(correctWord.toString())) {
            System.out.print(GREEN + correctWord);
            isGuesswrong = false;
        }
    }

    public static void compareLetters(char[] guess, char[] correctWord) {
        boolean usedYellow = false;

        for (int i = 0; i < 5; i++) {
            char g = guess[i];
            char c = correctWord[i];
            String s = String.valueOf(g);

            if (g == c) {
                greenChars.add(s);
                yellowChars.remove(s);
                System.out.print(GREEN + g + RESET);

            } else if (!usedYellow && correctWordCharcters.contains(c)) {
                yellowChars.add(s);
                System.out.print(YELLOW + g + RESET);
                usedYellow = true;

            } else {
                greychars.add(s);
                System.out.print(g);
            }

            unsedLetters.remove(s);
        }
    }

}
