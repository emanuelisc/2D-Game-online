package zelda2dgameserver.services;

public class ServiceRegistry {

    public PlayerService playerService() {
        return PlayerServiceProxy.getInstance();
    }

    public TilesService tilesService() {
        return TilesService.getInstance();
    }

}
