package no.ruben;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {
    int statusCode;
    public ChatClient (String host, String requestTarget, int port, String message) throws IOException {
        Socket socket = new Socket(host, port);
        String request = (
            "GET " + requestTarget + " HTTP/1.1\r\n" +
            "Host: " + host + "\r\n" +
            "Connection: close\r\n" +
            "Message: " + message + "\r\n" +
            "\r\n"
        );
        socket.getOutputStream().write(request.getBytes());

        String statusLine = readLine(socket);
        this.statusCode = Integer.parseInt(statusLine.split(" ")[1]);
    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder response = new StringBuilder();
        int c;
        while((c = socket.getInputStream().read()) != '\r') {
            response.append((char) c);
        }
        socket.getInputStream().read();
        System.out.println(response);
        return response.toString();
    }

    public int getResponseCode() {
        return statusCode;
    }

    public String GetEchoResponseFromServer() {
        return null;
    }
}
