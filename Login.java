import java.io.Console;
import java.util.Scanner;
public class Login {

    public void mainpage() {        
        Console console = System.console();
        Scanner input = new Scanner(System.in);
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        System.out.println("Welcome to My STudent Automated Registration System (MySTARS)");
        System.out.print("Enter your UserName:");
        String userName = input.nextLine();

        char[] passwordArray = console.readPassword("Enter your secret:");
        System.out.println("User name is"+userName);
        
        console.printf("Password entered was: %s%n", new String(passwordArray));
        input.close();

    }

    public static void main(String[] args) {
        new Login().mainpage();
    }
}
