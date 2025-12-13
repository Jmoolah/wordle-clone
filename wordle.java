
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
    
    public static HashMap<Character, Integer> correctLetterLocations = new HashMap<>();
    public static HashSet<Character> correctWordCharcters = new HashSet<>();
    public static StringBuilder correctWord = new StringBuilder();
    public static ArrayList<String> wordList = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);
    public static boolean isGuesswrong = true;
     public static byte guessCount=0;
   
    public static void main(String[] args) throws IOException {
        System.out.print("\u001B[2J"); // ANSI code: clear entire screen

        for (char c = 'A'; c <= 'Z'; c++) {
        //System.out.print(c + " ");
        unsedLetters.add(String.valueOf(c));
        letterColors.add(Character.toString(c));
        
        }

       //System.out.print(String.join(" ", letterColors));
        makeWordist(FILENAME);
        getACorrectWord();
        System.out.println(correctWord);
        
       int update=0;
        while (isGuesswrong && guessCount!=5) {
           printLetterColors(update);
           takeGuess();
           guessCount++;
          /* System.out.print(RESET+ GREEN + greenChars + RESET);
           System.out.print(RESET+ YELLOW+  yellowChars + RESET);
           System.out.print(RESET+  unsedLetters + RESET);*/
           System.out.println();
           update++;
        }
        if(isGuesswrong){
            System.out.println("Correct Word: "+ correctWord);
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


    //        \u001B[<n>B
    /* 
    System.out.print("\u001B[H"); // ANSI code: move cursor to top-left corner


    System.out.print("\u001B[2J"); // ANSI code: clear entire screen
    System.out.print("\u001B[H"); // move cursor back to top-left

    */
    public static void takeGuess() {
        
        boolean isGuessValid = false;
        String guessWhile ="";
        while(!isGuessValid){
            guessWhile ="";
            guessWhile += sc.next().toLowerCase();
            
            if(wordList.contains(guessWhile)){
                break;
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
                greenChars.add(String.valueOf(guessCharArr[c]));
                System.out.print(GREEN + guessCharArr[c]+RESET);
                //unsedLettrs.remove(String.valueOf(guessCharArr[c]));
               if( yellowChars.contains(String.valueOf(guessCharArr[c]))){
                    yellowChars.remove(String.valueOf(guessCharArr[c]));
               }
                
                
                
            }
            else if(correctWordCharcters.contains(guessCharArr[c])){
               System.out.print(YELLOW +guessCharArr[c]+RESET); 
               yellowChars.add(String.valueOf(guessCharArr[c]));
               unsedLetters.remove(String.valueOf(guessCharArr[c]));
            }
            else{
                System.out.print(guessCharArr[c]);
                greychars.add(String.valueOf(guessCharArr[c]));
                
            }
        }

       

    }

    public static void printLetterColors(int updateRange){
         System.out.print("\u001B[H"); // ANSI code: move cursor to top-left corner
         System.out.println("Unsed Letter:\n"+unsedLetters);
         System.out.print("Wrong Letters:\n"+ greychars);
         System.out.print("\033[" + updateRange +"B");  // moves cursor down 5 lines

    }  
}
