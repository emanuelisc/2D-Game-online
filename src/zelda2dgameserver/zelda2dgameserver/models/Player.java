package zelda2dgameserver.models;

import zelda2dgameserver.enums.Direction;

public class Player {
    private int id;
    private String name;

    private int health;
    private int posX;
    private int posY;

    private Direction direction;

    private int lives;

    public Player() { }

    public Player(int id, String name, int health, int posX, int posY, Direction direction, int lives) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;
        this.lives = lives;
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

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", health=" + health +
                ", posX=" + posX +
                ", posY=" + posY +
                ", direction=" + direction +
                '}';
    }
}