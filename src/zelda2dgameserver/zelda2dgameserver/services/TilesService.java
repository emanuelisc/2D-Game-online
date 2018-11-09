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

    private List<TileType> tileTypes = new ArrayList<>();
    private List<WorldTile> worldTiles = new ArrayList<>();

    private static TilesService instance;

    static TilesService getInstance() {
        if (instance == null) {
            instance = new TilesService();
        }

        return instance;
    }

    public void loadTiles() {
        System.out.println("Loading tile types...");
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

            System.out.println("Tile types loaded.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadWorldTiles() {
        System.out.println("Loading world tiles...");
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

            System.out.println("World tiles loaded.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TileType> getTileTypes() {
        return tileTypes;
    }

    public List<WorldTile> getWorldTiles() {
        return worldTiles;
    }

    public List<WorldTile> getWorldTiles(Viewport viewPort) {
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
