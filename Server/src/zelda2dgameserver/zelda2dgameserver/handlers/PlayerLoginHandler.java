package zelda2dgameserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import zelda2dgameserver.Services.PlayerService;

import java.io.IOException;

public class PlayerLoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange http) throws IOException {

        if (http.getRequestMethod().toLowerCase().equals("post")) {
            byte[] input = new byte[128];
            int size = http.getRequestBody().read(input);
            if (size < input.length) {
                String name = new String(input, 0, size);
                boolean isLoggedIn = PlayerService.login(name);
                http.sendResponseHeaders(isLoggedIn ? 200 : 401, 0);
            }
        } else {
            http.sendResponseHeaders(400, 0);
        }

        http.getResponseBody().close();
    }
}
