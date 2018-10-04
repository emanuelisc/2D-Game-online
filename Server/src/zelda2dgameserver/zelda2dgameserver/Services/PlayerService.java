package zelda2dgameserver.Services;

import zelda2dgameserver.database.ConnectionManager;
import zelda2dgameserver.database.models.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PlayerService {

    private static Set<Player> onlinePlayers = new HashSet<>();

    public static Player findByName(String name) {
        for (Player player : onlinePlayers) {
            if (player.getName().equals(name))
                return player;
        }

        return null;
    }

    public static Set<Player> getAllPlayers() {
        Set<Player> players = new HashSet<>();

        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from zelda.players;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                int health = rs.getInt("Health");
                int posX = rs.getInt("PosX");
                int posY = rs.getInt("PosY");

                players.add(new Player(id, name, health, posX, posY));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    public static boolean login(String name) {

        if (findByName(name) != null) {
            return true;
        }

        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("select Id, Health, PosX, PosY from players where Name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("Id");
                int health = rs.getInt("Health");
                int posX = rs.getInt("PosX");
                int posY = rs.getInt("PosY");

                onlinePlayers.add(new Player(id, name, health, posX, posY));

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
