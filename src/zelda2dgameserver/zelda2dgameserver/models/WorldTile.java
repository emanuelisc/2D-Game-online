package zelda2dgameserver.models;

public class WorldTile {
    private int indX;
    private int indY;
    private int tileId;

    public WorldTile(int indX, int indY, int tileId) {
        this.indX = indX;
        this.indY = indY;
        this.tileId = tileId;
    }

    public int getIndX() {
        return indX;
    }

    public void setIndX(int indX) {
        this.indX = indX;
    }

    public int getIndY() {
        return indY;
    }

    public void setIndY(int indY) {
        this.indY = indY;
    }

    public int getTileId() {
        return tileId;
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }
}
