
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
    
    public static HashSet<Character> greychars = new HashSet<>();
    public static HashSet<Character> yellowChars = new HashSet<>();
    public static HashSet<Character> greenChars = new HashSet<>();
    public static HashMap<Character, Integer> correctLetterLocations = new HashMap<>();
    public static HashSet<Character> correctWordCharcters = new HashSet<>();
    public static StringBuilder correctWord = new StringBuilder();
    public static ArrayList<String> wordList = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);
    public static boolean isGuesswrong = true;
    
    public static void main(String[] args) throws IOException {
        for (char c = 'A'; c <= 'Z'; c++) {
        System.out.print(c + " ");
        }

        makeWordist(FILENAME);
        getCorrectWord();
        System.out.println(correctWord);
        
       
        while (isGuesswrong) {
           takeGuess();
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

    public static void getCorrectWord() throws IOException {
        int size = wordList.size();
        int randomWord = (int) (Math.random() * size); // 0 to 100
        if (correctWord.isEmpty()) {
            correctWord.append(wordList.get(randomWord).toUpperCase());
            int i =0;
            for (char c : correctWord.toString().toCharArray()) {
                correctWordCharcters.add(c);
                correctLetterLocations.put(c,i);
                i++;
            }

        } else if (!correctWord.isEmpty()) {
            correctWord.setLength(0);
            correctWord.append(randomWord);
            int i =0;
            for (char c : correctWord.toString().toCharArray()) {
                correctWordCharcters.add(c);
                correctLetterLocations.put(c,i);
                i++;
            }
        }

    }
    //sits in while loop
    public static void takeGuess() {
        
        boolean isGuessValid = false;
        String guessWhile ="";
        while(!isGuessValid){
            guessWhile ="";
            guessWhile += sc.next().toLowerCase();
            
            if(wordList.contains(guessWhile)){
                isGuessValid=true;
            }
            else{
                System.out.print("\033[F");   // move cursor UP one line
                System.out.print("\033[2K");  // clear that line
            }
            
        }

        String guess = guessWhile.toUpperCase();
        char[] correctWordCharArr = correctWord.toString().toCharArray();
        
        char[] guessCharArr = guess.toCharArray();

        System.out.print("\033[F");   // move cursor UP one line
        System.out.print("\033[2K");  // clear that line
        

        if(guess.equals(correctWord.toString())){
            System.out.print(GREEN + correctWord);
            isGuesswrong=false;
            return;
        }

        for (int c = 0; c < 5; c++) {
            if(guessCharArr[c]==correctWordCharArr[c]){
                System.out.print(GREEN + guessCharArr[c]+RESET);
            }
            else if(correctWordCharcters.contains(guessCharArr[c])){
               System.out.print(YELLOW +guessCharArr[c]+RESET); 
            }
            else{
                System.out.print(guessCharArr[c]);
            }
        }
        System.out.print("\n");
    }
}
