import java.util.*;
import java.io.*;

public class PasswordManager {    
    
    // Hard coded
    private final String PATH = "/Users/micheledinelli/Desktop/pwfile.txt";
    private String password;

    /**
     * Dumb(p) logo on terminal
     */
    public void dumpTitleOnTerminal() {
        System.out.println("");

        System.out.println("      #########     ########        ##   ##               ##      ##");
        System.out.println("     ##            ##    ##        ##   ##               ##      ##");
        System.out.println("    ##            ########        #####                 ##      ##");     
        System.out.println("   ##            ## ##           ###  ##               ##      ##");       
        System.out.println("  ##            ##   ##         ##     ##      ###    ##      ##");
        System.out.println(" #########     ##     ##       ##       ##    ###    #########");
        
        System.out.println("");
    }

    /**
     * Acquire user password and write it to the location
     * specified by the global variable <em>PATH</em>
     */
    public void init() {
        
        Scanner scan;
        FileWriter myWriter;
        Hashing hashing;
        
        try {

            scan = new Scanner(System.in);
            myWriter = new FileWriter(PATH);
            hashing = new Hashing();

            System.out.println("Hello, you are gona be asked to choose a password :D");
            System.out.println("Just let me know some of your preferences...\n");
            System.out.print("Do you want your password to be shown ? [Y|N]: ");
            
            String answer = scan.nextLine();
            boolean show = answer.equals("Y") ? true : false;
            
            System.out.println("Ok insert your password:");

            if(show) {
                this.password = scan.nextLine();
            } else {
                // Init console to eventually hide input 
                Console console = System.console();
                char [] password = console.readPassword("Enter password: ");
                this.password = String.valueOf(password);
            }

            // Compute the hash of the plain password
            String digest = hashing.hash(this.password); 

            // Write on the file
            myWriter.write(digest);
            
            System.out.println("Hash of your password saved to: " + PATH);
            System.out.print("Do you want to see the hash ? [Y|N]: ");

            String answerShowHash = scan.nextLine();
            boolean showHash = answerShowHash.equals("Y") ? true : false;
            if(showHash){
                System.out.println(digest);
            } 

            myWriter.close();
            scan.close();

        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }               
        
    }

    public static void main(String[] args) {
        PasswordManager pm = new PasswordManager();
        //pm.dumpTitleOnTerminal();
        pm.init();
    }

}