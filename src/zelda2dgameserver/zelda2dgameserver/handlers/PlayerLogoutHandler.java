package zelda2dgameserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import zelda2dgameserver.services.ServiceRegistry;

import java.io.IOException;

public class PlayerLogoutHandler implements HttpHandler {

    private final ServiceRegistry serviceRegistry;

    public PlayerLogoutHandler(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void handle(HttpExchange http) throws IOException {
        if (http.getRequestMethod().equals("POST")) {
            int contentLength = Integer.parseInt(http.getRequestHeaders().getFirst("Content-Length"));
            byte[] data = new byte[contentLength];
            int size = http.getRequestBody().read(data);
            String name = new String(data, 0, size);
            serviceRegistry.playerService().logout(name);
            http.getResponseHeaders().add("Content-Length", "0");
            http.sendResponseHeaders(200, 0);
        } else {
            http.sendResponseHeaders(405, 0);
        }

        http.getResponseBody().close();
    }
}
