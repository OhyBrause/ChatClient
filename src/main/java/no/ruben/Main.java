package no.ruben;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("----------------------");
        System.out.println("### Server started ###");
        System.out.println("----------------------");

        HttpServer httpServer = new HttpServer(10800);
    }
}