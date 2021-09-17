package no.ruben;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatClientTest {
    @Test
    void test200Response() throws IOException {
        ChatClient cc = new ChatClient("httpbin.org", 80, "");
        System.out.println(cc.getMessageBody());
        assertEquals(200, cc.getResponseCode());
    }
    @Test
    void testEchoResponseFromLocalServer() throws IOException {
        String message = "Halooo";
        ChatClient cc = new ChatClient("localhost", 10800, message);
        System.out.println(cc.getMessageBody());
        assertEquals(message, cc.getMessageBody());
    }
    @Test
    void testEchoResponseFromRemoteServer() throws IOException {
        String message = "Halooo";
        ChatClient cc = new ChatClient("84.212.217.38", 10800, message);
        System.out.println(cc.getMessageBody());
        assertEquals(message, cc.getMessageBody());
    }
}