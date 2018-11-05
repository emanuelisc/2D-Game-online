package zelda2dgameserver.services;

import zelda2dgameserver.database.ConnectionManager;
import zelda2dgameserver.models.TileType;
import zelda2dgameserver.models.Viewport;
import zelda2dgameserver.models.WorldTile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TilesService {

    private static List<TileType> tileTypes = new ArrayList<>();
    private static List<WorldTile> worldTiles = new ArrayList<>();

    public static void loadTiles() {
        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from tiles;");
            ResultSet rs = ps.executeQuery();

            tileTypes.clear();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("TileName");
                tileTypes.add(new TileType(id, name));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadWorldTiles() {
        Connection connection = ConnectionManager.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from world");
            ResultSet rs = ps.executeQuery();

            worldTiles.clear();

            while (rs.next()) {
                int ix = rs.getInt("IndX");
                int iy = rs.getInt("IndY");
                int tileId = rs.getInt("TileId");

                worldTiles.add(new WorldTile(ix, iy, tileId));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<TileType> getTileTypes() {
        return tileTypes;
    }

    public static List<WorldTile> getWorldTiles() {
        return worldTiles;
    }

    public static List<WorldTile> getWorldTiles(Viewport viewPort) {
        final int fromX = viewPort.getIndXFrom();
        final int toX = viewPort.getIndXTo();
        final int fromY = viewPort.getIndYFrom();
        final int toY = viewPort.getIndYTo();

        return worldTiles.stream()
                .filter(tile -> fromX <= tile.getIndX() &&
                                tile.getIndX() <= toX &&
                                fromY <= tile.getIndY() &&
                                tile.getIndY() <= toY)
                .collect(Collectors.toList());
    }

}
