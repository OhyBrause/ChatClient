package no.ruben;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ChatScreen {

    String host;
    String lastMessage = "";
    String ownMessage = "";
    boolean firstRun = true;
    Scanner s = new Scanner(System.in);

    public ChatScreen(String host) {
        this.host = host;
    }

    public void run() throws IOException, InterruptedException {
        System.out.println("----------------------");
        System.out.println("### Client started ###");
        System.out.println("----------------------");
        listenToServer();
    }

    private void sendMessage(String message) throws IOException, InterruptedException {
        ChatClient cc = new ChatClient(host, 10800, message);
    }

    public void listenToServer() throws IOException, InterruptedException {
        System.out.println("### LISTENING TO SERVER. PRESS [ENTER] TO WRITE MESSAGE ###");
        while (true) {

            // Checking server for new messages every x seconds
            int serverRequestInterval = 1;
            ChatClient cc = new ChatClient("localhost", 10800, "retrieveLastMessage");

            if (firstRun) {
                System.out.println("first run");
//                firstRun = false;
            } else if (
                !lastMessage.equals(cc.getMessageBody()) &&
                !ownMessage.equals(cc.getMessageBody())
            ) {
                System.out.println("else if");
                lastMessage = cc.getMessageBody();
                System.out.println(lastMessage);
            }
            TimeUnit.SECONDS.sleep(serverRequestInterval);

            sendMessage(s.nextLine());
        }

    }
}