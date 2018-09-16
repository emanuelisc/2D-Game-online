import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    // TODO: paimti koordinates
    // TODO: siusti koordinates
    // TODO: loadinti world tiles

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new Handler());
        server.start();
        System.out.println("Server started");
    }

    static class Handler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            var response = "Hello, world";
            httpExchange.sendResponseHeaders(200, response.length());
            var out = httpExchange.getResponseBody();
            var ip = httpExchange.getRemoteAddress();
            out.write(response.getBytes());
            out.close();
            System.out.println("Sent response to " + ip);
        }
    }
}
