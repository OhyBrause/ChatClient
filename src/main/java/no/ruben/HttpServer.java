package no.ruben;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class HttpServer {
    final HashMap<String, String> headerFields = new HashMap<>();
    private int messagesSentToServer = 0;

    public HttpServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            readLine(clientSocket);

            if (getHeader("Message") != null) {
                messagesSentToServer++;
            } else System.out.println("Messages sent to server: " + messagesSentToServer);

            String headerLine;
            while (!(headerLine = readLine(clientSocket)).isBlank()) {
                int colonPos = headerLine.indexOf(':');
                String key = headerLine.substring(0, colonPos);
                String value = headerLine.substring(colonPos+1).trim();
                headerFields.put(key, value);
                System.out.println(key + ":" + value);
            }

            String messageBody = getHeader("Message");

            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length:" + messageBody.length() + "\r\n" +
                    "Connection: Close\r\n" +
                    "\r\n" +
                    messageBody;

            clientSocket.getOutputStream().write(response.getBytes());
            System.out.println(messageBody);
        }
    }

    static String readLine(Socket socket) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream in = socket.getInputStream();

        int c;
        while ((c = in.read()) != -1 && c != '\r') {
            result.append((char)c);
        }
        //noinspection ResultOfMethodCallIgnored
        in.read();
        return result.toString();
    }

    public String getHeader(String headerName) {
        return headerFields.get(headerName);
    }
}