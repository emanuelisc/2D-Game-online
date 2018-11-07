package zelda2dgameserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

public class LevelEditorHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange http) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("editor.html");
        byte[] buffer = new byte[1024 * 512];
        int read = 0;

        http.sendResponseHeaders(200, 0);

        while (read != -1) {
            read = is.read(buffer);
            http.getResponseBody().write(buffer, 0, read);
        }

        http.getResponseBody().close();
    }
}
