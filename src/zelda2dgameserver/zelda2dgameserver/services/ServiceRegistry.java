package zelda2dgameserver.services;

public class ServiceRegistry {

    public PlayerService playerService() {
        return PlayerService.getInstance();
    }

    public TilesService tilesService() {
        return TilesService.getInstance();
    }

}
