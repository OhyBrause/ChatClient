package no.ruben;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatClientTest {
    @Test
    void test200Response() throws IOException {
        ChatClient cc = new ChatClient("httpbin.org", "/html", 80, "");
        assertEquals(200, cc.getResponseCode());
    }
}
