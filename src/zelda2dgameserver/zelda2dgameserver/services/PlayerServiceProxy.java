package zelda2dgameserver.services;

import zelda2dgameserver.loggers.LogMessage;
import zelda2dgameserver.loggers.Logger;
import zelda2dgameserver.models.Player;

import java.util.Optional;
import java.util.Set;

public class PlayerServiceProxy implements PlayerService {

    private PlayerService playerService;
    private Logger logger;

    private PlayerServiceProxy(PlayerService playerService) {
        this.playerService = playerService;
        logger = new Logger();
    }

    private static PlayerServiceProxy instance;

    public static PlayerServiceProxy getInstance() {
        if (instance == null) {
            instance = new PlayerServiceProxy(PlayerServiceImpl.getInstance());
        }

        return instance;
    }

    @Override
    public Set<Player> getOnlinePlayers(String currentPlayerName) {
        // Very fast calling method
        return playerService.getOnlinePlayers(currentPlayerName);
    }

    @Override
    public Set<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @Override
    public Optional<Player> login(String name) {
        Optional<Player> playerOptional = playerService.login(name);
        if (playerOptional.isPresent()) {
            logger.logMessage(new LogMessage(Logger.LVL_USER_ACTIVITY, "User with name " + name + " logged in."));
        } else {
            logger.logMessage(new LogMessage(Logger.LVL_ERROR, "User " + name + " could not login."));
        }

        return playerOptional;
    }

    @Override
    public void logout(String name) {
        logger.logMessage(new LogMessage(Logger.LVL_USER_ACTIVITY, "User " + name + " logged out."));
        playerService.logout(name);
    }

    @Override
    public void register(String name) {
        logger.logMessage(new LogMessage(Logger.LVL_USER_ACTIVITY, "Registering new user " + name + "."));
        playerService.register(name);
    }

    @Override
    public void update(Player player) {
        // Very fast calling method
        playerService.update(player);
    }
}
