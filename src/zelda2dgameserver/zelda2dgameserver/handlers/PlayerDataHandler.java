package zelda2dgameserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import zelda2dgameserver.services.PlayerService;
import zelda2dgameserver.models.Player;
import zelda2dgameserver.services.ServiceRegistry;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

/**
 * Zaidejo duomenu handleris.
 */
public class PlayerDataHandler implements HttpHandler {

    private final ObjectMapper objectMapper;
    private final ServiceRegistry serviceRegistry;

    public PlayerDataHandler(ObjectMapper objectMapper, ServiceRegistry serviceRegistry) {
        this.objectMapper = objectMapper;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void handle(HttpExchange http) throws IOException {
        String action = http.getRequestURI().toString().replace("/players", "");
        String method = http.getRequestMethod().toLowerCase();

        switch (method) {
            case "get":
                if (action.length() == 0) {
                    Set<Player> players = serviceRegistry.playerService().getAllPlayers();
                    byte[] data = objectMapper.writeValueAsBytes(players);
                    OutputStream out = http.getResponseBody();

                    http.getResponseHeaders().add("Content-Type", "application/json");
                    http.sendResponseHeaders(200, data.length);
                    out.write(data);
                }

                break;

            case "post":
                byte[] bytes = new byte[1024];
                int size = http.getRequestBody().read(bytes);
                String name = new String(bytes, 0, size);
                PlayerService.register(name);
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
