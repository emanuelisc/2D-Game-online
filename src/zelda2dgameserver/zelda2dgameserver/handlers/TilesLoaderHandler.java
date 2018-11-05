package zelda2dgameserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import zelda2dgameserver.models.WorldTile;
import zelda2dgameserver.services.TilesService;

import java.io.IOException;
import java.util.List;

public class TilesLoaderHandler implements HttpHandler {
    private final ObjectMapper objectMapper;

    public TilesLoaderHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpExchange http) throws IOException {
        if (http.getRequestMethod().equals("GET")) {
            List<WorldTile> worldTiles = TilesService.getWorldTiles();
            String result = objectMapper.writeValueAsString(worldTiles);

            http.sendResponseHeaders(200, result.length());
            http.getResponseHeaders().add("Content-Type", "application/json");
            http.getResponseBody().write(result.getBytes());
        } else {
            http.sendResponseHeaders(405, 0);
        }

        http.getRequestBody().close();
        http.getResponseBody().close();
        // todo: gauti requesta, atsiusti tiles kokiu nors duomenu tipu. Parsiustas mapas, veliau butu uzkraunamass
    }
}
