package zelda2dgameserver.Services;

import zelda2dgameserver.database.ConnectionManager;
import zelda2dgameserver.database.models.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerService {

    private static Set<Player> onlinePlayers = new HashSet<>();

    private static Player findByName(String name) {
        for (Player player : onlinePlayers) {
            if (player.getName().equals(name))
                return player;
        }

        return null;
    }

    public static Set<Player> getOnlinePlayers(String currentPlayerName) {
        return onlinePlayers
                .stream()
                .filter(p -> !p.getName().equals(currentPlayerName))
                .collect(Collectors.toSet());
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

    public static Optional<Player> login(String name) {
        Player player = findByName(name);

        if (player != null) {
            return Optional.of(player);
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

                player = new Player(id, name, health, posX, posY);
                onlinePlayers.add(new Player(id, name, health, posX, posY));
                System.out.printf("Player %s joined.\n", player.getName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(player);
    }

    public static void logout(String name) {
        Player player = findByName(name);

        if (player == null) {
            return;
        }

        onlinePlayers.remove(player);
        System.out.printf("Player %s disconnected.", player.getName());

        Connection connection = ConnectionManager.getConnection();

        try {
             PreparedStatement preparedStatement = connection.prepareStatement("update players set Health = ?, PosX = ?, PosY = ? where Id = ?");
             preparedStatement.setInt(1, player.getHealth());
             preparedStatement.setFloat(2, player.getPosX());
             preparedStatement.setFloat(3, player.getPosY());
             preparedStatement.setInt(4, player.getId());

             preparedStatement.execute();
             preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void register(String name) {
        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into players (Name, Health, PosX, PosY) values (?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, 100);
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(Player player) {

    }

}
