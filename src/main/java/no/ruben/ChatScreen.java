package no.ruben;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ChatScreen {

    String host;
    String lastMessage = "";
    String ownMessage = "";
    boolean firstRun = true; // Never used??
    Scanner s = new Scanner(System.in);

    public ChatScreen(String host) {
        this.host = host;
    }

    public void run() throws IOException, InterruptedException {
            System.out.println(lastMessage); // Prints what the last message was. First time its blank
            while(true) { // Runs listen to server and send message, until the loop breaks
                listenToServer();
                sendMessage();
            }
        }

        // Sets own message to be what the user inputs, then creates a new ChatClient with the
        // users message.
        private void sendMessage() throws IOException {
            System.out.println("### WRITE MESSAGE: ###");
            String message = s.nextLine();
            ownMessage = message;
            ChatClient cc = new ChatClient(host, 10800, message);
        }

        // In intervals of 1 second, the server is updated to check if new messages have
        // been sent
        public void listenToServer() throws IOException, InterruptedException {
            System.out.println("### LISTENING TO SERVER. PRESS [ENTER] TO WRITE MESSAGE ###");
            boolean blankLine = true;
            loop:
            while (true) {
                int available; // This int represents an estimate of how many bytes that can be read.
                while ((available = System.in.available()) == 0) {
                    ChatClient cc = new ChatClient(host, 10800, "retrieveLastMessage");
                    String incomingMessage = cc.getMessageBody();

                    if (
                        !lastMessage.equals(incomingMessage) &&
                        !ownMessage.equals(incomingMessage)
                    ) {
                        lastMessage = incomingMessage;
                        System.out.println(incomingMessage);
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