package no.ruben;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String mode = args[0]; // Sets the first arg to be mode
        String host;
        Scanner s = new Scanner(System.in);

        if (mode.equals("-s")) { // If mode = -s, then start the server
            System.out.println("----------------------");
            System.out.println("### Server started ###");
            System.out.println("----------------------");
            HttpServer httpServer = new HttpServer(10800);
        } else if (mode.equals("-c")) {
            /*
                If mode = -c, and the length of args is 2, that means that
                host is the second arg. If not = 2, then ask user where to connect.
                Then create a ChatScreen, which takes the host as an argument
            */
            if (args.length == 2) {
                host = args[1];
            } else {
                System.out.println("Where do you want to connect?");
                host = s.nextLine();
            }
            ChatScreen cs = new ChatScreen(host);
            cs.run();
        }
    }
}