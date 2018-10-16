package zelda2dgameserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import zelda2dgameserver.database.ConnectionManager;
import zelda2dgameserver.handlers.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {


        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new Handler());
        server.createContext("/login", new PlayerLoginHandler());
        server.createContext("/logout", new PlayerLogoutHandler());
        server.createContext("/players", new PlayerDataHandler(objectMapper));
        server.createContext("/update", new PlayerUpdateHandler(objectMapper));
        server.createContext("/level", new TilesLoaderHandler());
        server.start();
        System.out.println("Server started");
    }

    static class Handler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "Hello, world";
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream out = httpExchange.getResponseBody();
            String ip = httpExchange.getRemoteAddress().getHostString();
            out.write(response.getBytes());
            out.close();
            System.out.println("Sent response to " + ip);

            ConnectionManager.getConnection();
        }
    }
}
