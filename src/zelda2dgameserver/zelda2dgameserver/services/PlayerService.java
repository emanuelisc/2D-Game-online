package zelda2dgameserver.services;

import zelda2dgameserver.database.ConnectionManager;
import zelda2dgameserver.database.converters.DirectionEnumConverter;
import zelda2dgameserver.models.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerService {

    private Set<Player> onlinePlayers = new HashSet<>();

    private static PlayerService instance;

    private PlayerService() {}

    static PlayerService getInstance() {
        if (instance == null) {
            instance = new PlayerService();
        }

        return instance;
    }

    private Player findByName(String name) {
        for (Player player : onlinePlayers) {
            if (player.getName().equals(name))
                return player;
        }

        return null;
    }

    public Set<Player> getOnlinePlayers(String currentPlayerName) {
        return onlinePlayers
                .stream()
                .filter(p -> !p.getName().equals(currentPlayerName))
                .collect(Collectors.toSet());
    }

    public Set<Player> getAllPlayers() {
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
                int direction = rs.getInt("Direction");
                int lives = rs.getInt("Lives");

                players.add(new Player(id, name, health, posX, posY, DirectionEnumConverter.getDirection(direction), lives));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    public Optional<Player> login(String name) {
        Player player = findByName(name);

        if (player != null) {
            return Optional.of(player);
        }

        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("select Id, Health, PosX, PosY, Direction, Lives from players where Name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("Id");
                int health = rs.getInt("Health");
                int posX = rs.getInt("PosX");
                int posY = rs.getInt("PosY");
                int direction = rs.getInt("Direction");
                int lives = rs.getInt("Lives");

                player = new Player(id, name, health, posX, posY, DirectionEnumConverter.getDirection(direction), lives);
                onlinePlayers.add(player);

                System.out.printf("Player %s joined.\n", player.getName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(player);
    }

    public void logout(String name) {
        Player player = findByName(name);

        if (player == null) {
            return;
        }

        onlinePlayers.remove(player);
        System.out.printf("Player %s disconnected.\n", player.getName());

        Connection connection = ConnectionManager.getConnection();

        try {
             PreparedStatement preparedStatement = connection.prepareStatement("update players set Health = ?, PosX = ?, PosY = ?, Direction = ?, Lives = ? where Id = ?");
             preparedStatement.setInt(1, player.getHealth());
             preparedStatement.setInt(2, player.getPosX());
             preparedStatement.setInt(3, player.getPosY());
             preparedStatement.setInt(4, DirectionEnumConverter.getDirection(player.getDirection()));
            preparedStatement.setInt(5, player.getLives());
            preparedStatement.setInt(6, player.getId());

             preparedStatement.execute();
             preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void register(String name) {
        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into players (Name) values (?)");
            preparedStatement.setString(1, name);

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Player player) {
        Player serverPlayer = findByName(player.getName());

        if (serverPlayer != null) {
            serverPlayer.setPosX(player.getPosX());
            serverPlayer.setPosY(player.getPosY());
            serverPlayer.setHealth(player.getHealth());
            serverPlayer.setDirection(player.getDirection());
        }
    }

}
