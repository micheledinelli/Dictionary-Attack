import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DictionaryAttack {

    // Hard coded
    private final String PATH = "/Users/micheledinelli/Desktop/passw/dictionary.txt";
    private final String PW_PATH = "/Users/micheledinelli/Desktop/pwfile.txt";

    private Map<Integer,String> dictionary;
    private String digestToGuess;
    private boolean SUCCESS = false;

    public void obtainDigest() {
        
        Scanner filereader;
        
        try {
            File pwfile = new File(PW_PATH);
            filereader = new Scanner(pwfile);

            while (filereader.hasNextLine()) {
                this.digestToGuess = filereader.nextLine();
                break;
            }

            filereader.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtainDictionary() {
        
        Scanner filereader;
        
        try {
            File pwfile = new File(PATH);
            filereader = new Scanner(pwfile);
            dictionary = new HashMap<Integer, String>();
            
            int key = 0;
            while (filereader.hasNextLine()) {
                String word = filereader.nextLine();                
                dictionary.put(key, word);
                key++;
            }

            filereader.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The attack consists in 6 different attempts 
     * <ol>
     *  <li>digest of the word with and without first letter capitalized</li>
     *  <li>digest of the word_n where n is a number between 0-1000 with and without the first letter capitalized</li>
     *  <li>digest of the word.n where n is a numbere beteween 0-1000 with and without the first letter capitalized</li>
     * </ol>
     * 
     * Time Complexity: T(n) = O(dictionary.length * 1000) 
     * @throws NoSuchAlgorithmException
     */
    public void attack() throws NoSuchAlgorithmException {
        // The main idea is for each word in the dictionary we want to compute the digest and 
        // check if it matches with the password in the file
        
        Hashing hashing = new Hashing();
        String password = "";
        for(Integer key : dictionary.keySet()) {
            String word = dictionary.get(key);   
            String wordCapitalized = word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();

            String digest = hashing.hash(word);
            String digestCapitalized = hashing.hash(wordCapitalized);

            if(digest.equals(digestToGuess)) {
                this.SUCCESS = true;
                password = dictionary.get(key);
                break;
            } else if(digestCapitalized.equals(digestToGuess)) {
                this.SUCCESS = true;
                password = wordCapitalized;
                break;
            }
        }

        for(Integer key : dictionary.keySet()) {  
            if(!SUCCESS) {
                String word = dictionary.get(key);
                String wordCapitalized = word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();

                String wordModified = word;
                String wordModifiedCapitalized = wordCapitalized;

                for(int i = 0; i < 1000; i++) {
                    wordModified = word + i;
                    wordModifiedCapitalized = wordCapitalized + i;
                    System.out.println(wordModified);
                    System.out.println(wordModifiedCapitalized);
                    String digest = hashing.hash(wordModified);
                    String digestCapitalized = hashing.hash(wordModifiedCapitalized);
                    
                    if(digest.equals(digestToGuess)) {
                        this.SUCCESS = true;
                        password = wordModified;
                        break;
                    } else if(digestCapitalized.equals(digestToGuess)){
                        this.SUCCESS = true;
                        password = wordModifiedCapitalized;
                    }
                }
            } else {
                break;
            }
        }
        
        for(Integer key : dictionary.keySet()) {  
            if(!SUCCESS) {
                String word = dictionary.get(key) + "_";
                String wordCapitalized = word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();

                String wordModified = word;
                String wordModifiedCapitalized = wordCapitalized;

                for(int i = 0; i < 1000; i++) {
                    wordModified = word + i;
                    wordModifiedCapitalized = wordCapitalized + i;
                    
                    String digest = hashing.hash(wordModified);
                    String digestCapitalized = hashing.hash(wordModifiedCapitalized);
                    
                    if(digest.equals(digestToGuess)) {
                        this.SUCCESS = true;
                        password = wordModified;
                        break;
                    } else if(digestCapitalized.equals(digestToGuess)){
                        this.SUCCESS = true;
                        password = wordModifiedCapitalized;
                    }
                }
            } else {
                break;
            }
        }

        for(Integer key : dictionary.keySet()) {  
            if(!SUCCESS) {
                String word = dictionary.get(key) + ".";
                String wordCapitalized = word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();

                String wordModified = word;
                String wordModifiedCapitalized = wordCapitalized;

                for(int i = 0; i < 1000; i++) {
                    wordModified = word + i;
                    wordModifiedCapitalized = wordCapitalized + i;
                    
                    String digest = hashing.hash(wordModified);
                    String digestCapitalized = hashing.hash(wordModifiedCapitalized);
                    
                    if(digest.equals(digestToGuess)) {
                        this.SUCCESS = true;
                        password = wordModified;
                        break;
                    } else if(digestCapitalized.equals(digestToGuess)){
                        this.SUCCESS = true;
                        password = wordModifiedCapitalized;
                    }
                }
            } else {
                break;
            }
        }

        if(this.SUCCESS) {
            System.out.print("\033[H\033[2J");  
            System.out.flush();
            System.out.println("Did it! password cracked!!!");
            System.out.println("Your password is: " + password + "\n");
        } else { System.out.println("Good password mate..."); }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        DictionaryAttack dAttack = new DictionaryAttack();
        dAttack.obtainDictionary();
        dAttack.obtainDigest();

        long start = System.currentTimeMillis();
        
        /*Thread computing = new Thread(){
            @Override
            public void run() {
                int count = 0;
                while(true) {
                    
                    System.out.print(".");
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(count == 3) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.print("\033[H\033[2J");  
                        System.out.flush();
                        count = 0;
                    }
                }
            }
        };
        computing.start();
        */
        dAttack.attack();
        
        long end = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (end - start) + "ms \n");

        System.exit(0);
            
    }
}