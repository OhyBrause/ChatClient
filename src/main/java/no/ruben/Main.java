package no.ruben;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String mode = args[0];
        String host;
        Scanner s = new Scanner(System.in);

        if (mode.equals("-s")) {
            System.out.println("----------------------");
            System.out.println("### Server started ###");
            System.out.println("----------------------");
            HttpServer httpServer = new HttpServer(10800);
        } else if (mode.equals("-c")) {
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