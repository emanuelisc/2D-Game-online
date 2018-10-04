package zelda2dgameserver.database.models;

public class Player {
    private int id;
    private String name;

    private int health;
    private float posX;
    private float posY;

    public Player() { }

    public Player(int id, String name, int health, float posX, float posY) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.posX = posX;
        this.posY = posY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }
}
