package zelda2dgameserver.services;

import zelda2dgameserver.models.Player;

import java.util.Optional;
import java.util.Set;

public interface PlayerService {
    Set<Player> getOnlinePlayers(String currentPlayerName);

    Set<Player> getAllPlayers();

    Optional<Player> login(String name);

    void logout(String name);

    void register(String name);

    void update(Player player);
}
