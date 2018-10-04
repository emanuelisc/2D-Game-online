package zelda2dgameserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import zelda2dgameserver.Services.PlayerService;
import zelda2dgameserver.database.models.Player;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

/**
 * Zaidejo duomenu handleris.
 */
public class PlayerDataHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange http) throws IOException {
        String action = http.getRequestURI().toString().replace("/players/", "");
        String method = http.getRequestMethod().toLowerCase();
        ObjectMapper objectMapper = new ObjectMapper();

        switch (method) {
            case "get":
                if (action.length() == 0) {
                    Set<Player> players = PlayerService.getAllPlayers();
                    byte[] data = objectMapper.writeValueAsBytes(players);
                    OutputStream out = http.getResponseBody();

                    http.getResponseHeaders().add("Content-Type", "application/json");
                    http.sendResponseHeaders(200, data.length);
                    out.write(data);
                }

                break;
//            case "post":
//                break;
                default:
                    http.sendResponseHeaders(405, 0);
                    http.getResponseBody().close();
                    break;
        }

        http.getResponseBody().close();
    }
}
