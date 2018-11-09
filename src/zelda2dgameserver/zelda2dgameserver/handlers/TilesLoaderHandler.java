package zelda2dgameserver.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import zelda2dgameserver.models.Viewport;
import zelda2dgameserver.models.WorldTile;
import zelda2dgameserver.services.ServiceRegistry;

import java.io.IOException;
import java.util.List;

public class TilesLoaderHandler implements HttpHandler {
    private final ObjectMapper objectMapper;
    private final ServiceRegistry serviceRegistry;

    public TilesLoaderHandler(ObjectMapper objectMapper, ServiceRegistry serviceRegistry) {
        this.objectMapper = objectMapper;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void handle(HttpExchange http) throws IOException {
        if (http.getRequestMethod().equals("POST")) {
            byte[] buffer = new byte[1024];
            int readSize = http.getRequestBody().read(buffer);
            String data = new String(buffer, 0, readSize);

            Viewport viewport = objectMapper.readValue(data, Viewport.class);

            List<WorldTile> tiles = serviceRegistry.tilesService().getWorldTiles(viewport);
            String result = objectMapper.writeValueAsString(tiles);

            http.sendResponseHeaders(200, result.length());
            http.getResponseBody().write(result.getBytes());
        }

        http.getRequestBody().close();
        http.getResponseBody().close();
    }
}
