package ktu.zelda.server.service;

import ktu.zelda.server.database.entities.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private List<Player> players = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getPlayer(String name) {
        Optional<Player> playerOptional = players.stream().filter(p -> p.getName().equals(name)).findFirst();

        return playerOptional.orElse(null);
    }

}
