package zelda2dgameserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import zelda2dgameserver.Services.PlayerService;
import zelda2dgameserver.database.models.Player;

import java.io.IOException;
import java.util.Optional;

public class PlayerLoginHandler implements HttpHandler {
        @Override
    public void handle(HttpExchange http) throws IOException {

        if (http.getRequestMethod().equals("POST")) {
            int contentLength = Integer.parseInt(http.getRequestHeaders().getFirst("Content-Length"));
            byte[] input = new byte[contentLength];
            int size = http.getRequestBody().read(input);
            String username = new String(input, 0, size);

            Optional<Player> player = PlayerService.login(username);

            if (player.isPresent()) {
                byte[] payload = new ObjectMapper().writeValueAsBytes(player.get());
                http.sendResponseHeaders(200, payload.length);
                http.getResponseBody().write(payload);
            } else {
                http.sendResponseHeaders(404, 0);
            }

        } else {
            http.sendResponseHeaders(405, 0);
        }

        http.getResponseBody().close();
    }
}
