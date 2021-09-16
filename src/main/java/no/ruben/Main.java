package no.ruben;

import java.io.IOException;

public class Main {
    public static void main(String[] command) throws IOException, InterruptedException {
        String mode = "-client";
//        String mode = "-server";
//        while(true) {
            if (mode.equals("-server")) {
                System.out.println("----------------------");
                System.out.println("### Server started ###");
                System.out.println("----------------------");
                HttpServer httpServer = new HttpServer(10800);
            } else if (mode.equals("-client")) {
                ChatScreen cs = new ChatScreen();
                cs.run();
//            } else {
//                System.out.println("[-server] or [-client]. [-q] to quit");
//                mode = System.console().readLine();
//                if (mode.equals("-q")) System.exit(0);
//            }
        }
    }
}