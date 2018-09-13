package com.zelda.game.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonGenerator {
    public void generate() throws FileNotFoundException
    {
        // creating JSONObject
        JSONObject player1 = new JSONObject();
        player1.put("playerID", "1");
        player1.put("log_time", 25);

        Map address = new LinkedHashMap(4);
        address.put("up", 0);
        address.put("down", 1);
        address.put("left", 0);
        address.put("right", 0);
        // putting address to JSONObject
        player1.put("address", address);

        JSONObject player2 = new JSONObject();
        player2.put("playerID", "1");
        player2.put("log_time", 25);

        Map address2 = new LinkedHashMap(4);
        address2.put("up", 0);
        address2.put("down", 1);
        address2.put("left", 0);
        address2.put("right", 0);
        // putting address to JSONObject
        player2.put("address", address2);

        JSONArray array = new JSONArray();

        array.add(player1);
        array.add(player2);

        JSONObject data = new JSONObject();

        data.put("game", array);

        // writing JSON to file:"JSONExample.json" in cwd
        PrintWriter pw = new PrintWriter("JSONExample.json");
        pw.write(data.toJSONString());

        pw.flush();
        pw.close();


    }
}
