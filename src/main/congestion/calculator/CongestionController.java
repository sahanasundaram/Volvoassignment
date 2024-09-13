
package main.congestion.calculator;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CongestionController {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws IOException {
        // Set up the HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/calculateTax", new TaxHandler());
        server.setExecutor(null); // Use default executor
        System.out.println("Server is running on port 8080...");
        server.start();
    }

    // HTTP Handler class for handling HTTP requests
    static class TaxHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");

                LocalDateTime[] dates = Arrays.stream(params)
                        .map(param -> LocalDateTime.parse(param.split("=")[1], FORMATTER))
                        .toArray(LocalDateTime[]::new);

                // Example vehicle, you can modify this to handle different vehicle types
                Vehicle vehicle = new MotorBike();
                CongestionTaxCalculator calculator = new CongestionTaxCalculator();

                // Calculate the tax
                int tax = calculator.calculateTax(vehicle, dates);

                // Prepare the response
                String response = "Total Tax: " + tax + " SEK";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}

