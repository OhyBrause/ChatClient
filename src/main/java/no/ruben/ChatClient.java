package no.ruben;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ChatClient {
    int statusCode;
    HashMap<String, String> headerFields = new HashMap<>();
    private String messageBody;

    public String getMessageBody() {
        return messageBody;
    }

    public ChatClient (String host, int port, String message) throws IOException {
        Socket socket = new Socket(host, port);
        String request = (
            "GET / HTTP/1.1\r\n" +
            "Host: " + host + "\r\n" +
            "Connection: close\r\n" +
            "Message:" + message + "\r\n" +
            "\r\n"
        );
        socket.getOutputStream().write(request.getBytes());

        String statusLine = readLine(socket);
        this.statusCode = Integer.parseInt(statusLine.split(" ")[1]);

        collectHeaderFields(socket);
        this.messageBody = readBytes(socket, getContentLength());
    }

    private String readBytes(Socket socket, int contentLength) throws IOException {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            buffer.append((char)socket.getInputStream().read());
        }
        return buffer.toString();
    }

    private int getContentLength() {
        return Integer.parseInt(headerFields.get("Content-Length"));
    }

    private void collectHeaderFields(Socket socket) throws IOException {
        String headerLine;
        while (!(headerLine = readLine(socket)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String key = headerLine.substring(0, colonPos);
            String value = headerLine.substring(colonPos+1).trim();
            headerFields.put(key, value);
        }
    }

    public String readLine(Socket socket) throws IOException {
        StringBuilder response = new StringBuilder();
        int c;
        while((c = socket.getInputStream().read()) != '\r' && c != -1) {
            response.append((char) c);
        }
        socket.getInputStream().read();
        return response.toString();
    }

    public int getResponseCode() {
        return statusCode;
    }
}