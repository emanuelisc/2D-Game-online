package ktu.zelda.server.database.repositories;

import ktu.zelda.server.database.entities.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    Optional<Player> findByName(String name);

}
