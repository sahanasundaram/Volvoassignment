package main.congestion.calculator;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try {
            // Set up the HTTP server
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            // Attach the context and define the handler for /calculateTax
            server.createContext("/calculateTax", new CongestionController.TaxHandler());

            server.setExecutor(null); // Use default executor

            System.out.println("Server is running on port 8080...");
            server.start();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
