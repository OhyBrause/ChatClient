package no.ruben;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatClientTest {
    @Test
    void testJunit() {
        ChatClient cc = new ChatClient();
        assertEquals(2,cc.i);
    }
}
