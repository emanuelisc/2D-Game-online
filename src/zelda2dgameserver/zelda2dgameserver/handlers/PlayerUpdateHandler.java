package zelda2dgameserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import zelda2dgameserver.services.PlayerService;
import zelda2dgameserver.models.Player;

import java.io.IOException;
import java.util.Set;

public class PlayerUpdateHandler implements HttpHandler {

    private final ObjectMapper objectMapper;

    public PlayerUpdateHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpExchange http) throws IOException {
        if (!http.getRequestMethod().equals("POST")) {
            http.sendResponseHeaders(405, 0);
            http.getResponseBody().close();
            return;
        }

        byte[] data = new byte[1024];
        int size = http.getRequestBody().read(data);
        String json = new String(data, 0, size);
        Player player = objectMapper.readValue(json, Player.class);

        PlayerService.update(player);
        Set<Player> onlinePlayers = PlayerService.getOnlinePlayers(player.getName());

        //System.out.println(player);

        String responseJson = objectMapper.writeValueAsString(onlinePlayers);
        http.getResponseHeaders().add("Content-Length", String.valueOf(responseJson.length()));
        http.sendResponseHeaders(200, responseJson.length());
        http.getResponseBody().write(responseJson.getBytes());

        http.getResponseBody().close();
    }
}
