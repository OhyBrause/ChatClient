package no.ruben;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ChatScreen {

    String host = "localhost";
    String lastMessage = "";

    public void run() throws IOException, InterruptedException {
        System.out.println("----------------------");
        System.out.println("### Client started ###");
        System.out.println("----------------------");
        while (true){
            Scanner s = new Scanner(System.in);
            System.out.println("[-listen] to retrive messages or [-send] to send one");
            if (s.nextLine().equals("-listen")) {
                System.out.println("LISTENING TO SERVER. PRESS [ENTER] TO STOP.");
                System.out.println(lastMessage);

                boolean blankLine = true;
                loop:
                while (true) {
                    int available;
                    while ((available = System.in.available()) == 0) {

                        ChatClient cc = new ChatClient("localhost", 10800, "retrieveLastMessage");
                        if (!lastMessage.equals(cc.getMessageBody())) {
                            lastMessage = cc.getMessageBody();
                            System.out.println(lastMessage);
                        }
                        TimeUnit.SECONDS.sleep(1);
                    }

                    do {
                        if (System.in.read() == '\n') {
                            if (blankLine)
                                break loop;
                            blankLine = true;
                        } else {
                            blankLine = false;
                        }
                    } while (--available > 0);
                }

            }
        }
    }
}
