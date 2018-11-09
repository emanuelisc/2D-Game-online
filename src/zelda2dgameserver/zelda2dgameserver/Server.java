package zelda2dgameserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import zelda2dgameserver.database.ConnectionManager;
import zelda2dgameserver.handlers.*;
import zelda2dgameserver.services.ServiceRegistry;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ServiceRegistry serviceRegistry = new ServiceRegistry();

    public static void main(String[] args) throws IOException {


        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new Handler());
        server.createContext("/login", new PlayerLoginHandler(serviceRegistry));
        server.createContext("/logout", new PlayerLogoutHandler(serviceRegistry));
//        server.createContext("/players", new PlayerDataHandler(objectMapper, serviceRegistry));
        server.createContext("/update", new PlayerUpdateHandler(objectMapper, serviceRegistry));
        server.createContext("/level", new TilesLoaderHandler(objectMapper, serviceRegistry));
        server.createContext("/editor", new LevelEditorHandler());
        server.start();
        System.out.println("Server started");

        serviceRegistry.tilesService().loadTiles();
        serviceRegistry.tilesService().loadWorldTiles();
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
