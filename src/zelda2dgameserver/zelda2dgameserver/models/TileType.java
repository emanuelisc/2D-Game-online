package zelda2dgameserver.models;

public class TileType {
    private int id;
    private String name;

    public TileType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
