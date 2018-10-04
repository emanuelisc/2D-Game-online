package ktu.zelda.server.controller;

import ktu.zelda.server.database.entities.Player;
import ktu.zelda.server.database.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/players")
public class PlayersController {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayersController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping
    Iterable<Player> get() {
        return playerRepository.findAll();
    }

    @PostMapping("/login")
    public Player join(Player player) {

        Optional<Player> playerOptional = playerRepository.findByName(player.getName());

        if (playerOptional.isPresent()) {
            return playerOptional.get();
        }

        player.setHealth(100);
        player.setPosX(0);
        player.setPosY(0);

        playerRepository.save(player);

        return player;
    }

}
