package no.ruben;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ChatScreen {

    String host;
    String lastMessage = "";
    String ownMessage = "";
    boolean firstRun = true;

    public ChatScreen(String host) {
        this.host = host;
    }
    Scanner s = new Scanner(System.in);

    public void run() throws IOException, InterruptedException {
        System.out.println("----------------------");
        System.out.println("### Client started ###");
        System.out.println("----------------------");

            System.out.println(lastMessage);
            while(true) {
                listenToServer();
//                sendMessage();
            }
        }

    private void sendMessage() throws IOException, InterruptedException {
        System.out.println("### WRITE MESSAGE: ###");
        String message = s.nextLine();
        ownMessage = message;
        ChatClient cc = new ChatClient(host, 10800, message);
    }

    public void listenToServer() throws IOException, InterruptedException {
        System.out.println("### LISTENING TO SERVER. PRESS [ENTER] TO WRITE MESSAGE ###");
        boolean blankLine = true;
        loop:
        while (true) {
            int available;
            while ((available = System.in.available()) == 0) {

                ChatClient cc = new ChatClient("localhost", 10800, "retrieveLastMessage");

                if (!lastMessage.equals(cc.getMessageBody()) &&
                        !(ownMessage.equals(cc.getMessageBody())) &&
                !firstRun) {
                    lastMessage = cc.getMessageBody();
                    System.out.println(lastMessage);
                    firstRun = false;
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