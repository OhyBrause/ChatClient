package no.ruben;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class HttpServer {
    final HashMap<String, String> headerFields = new HashMap<>();

    public HttpServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        String message = "";

        while (true) {
            Socket clientSocket = serverSocket.accept();
            readLine(clientSocket);
            getHeaders(clientSocket);

            if(headerFields.get("Message") != null &&!headerFields.get("Message").equals("retrieveLastMessage")) System.out.println(headerFields.get("Message"));

            if (    getHeader("Message") == null ||
                    getHeader("Message").equals("retrieveLastMessage")) {

                String response = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length:" + message.length() + "\r\n" +
                        "Connection: Close\r\n" +
                        "\r\n" +
                        message;

                clientSocket.getOutputStream().write(response.getBytes());

            } else {
                message = getHeader("Message");
                String messageBody = getHeader("Message");

                String response = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length:" + messageBody.length() + "\r\n" +
                        "Connection: Close\r\n" +
                        "\r\n" +
                        messageBody;

                clientSocket.getOutputStream().write(response.getBytes());

            }
        }
    }

    private void getHeaders(Socket clientSocket) throws IOException {
        String headerLine;
        while (!(headerLine = readLine(clientSocket)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String key = headerLine.substring(0, colonPos);
            String value = headerLine.substring(colonPos+1).trim();
            headerFields.put(key, value);
            System.out.println(key + ":" + value);
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