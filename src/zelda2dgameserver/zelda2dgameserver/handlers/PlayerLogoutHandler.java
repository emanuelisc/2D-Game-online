package zelda2dgameserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import zelda2dgameserver.services.PlayerService;

import java.io.IOException;

public class PlayerLogoutHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange http) throws IOException {
        if (http.getRequestMethod().equals("POST")) {
            int contentLength = Integer.parseInt(http.getRequestHeaders().getFirst("Content-Length"));
            byte[] data = new byte[contentLength];
            int size = http.getRequestBody().read(data);
            String name = new String(data, 0, size);
            PlayerService.logout(name);
            http.getResponseHeaders().add("Content-Length", "0");
            http.sendResponseHeaders(200, 0);
        } else {
            http.sendResponseHeaders(405, 0);
        }

        http.getResponseBody().close();
    }
}
