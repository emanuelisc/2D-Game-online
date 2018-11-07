package zelda2dgameserver.models;

import zelda2dgameserver.database.converters.PositionConverter;

public class Viewport {
    private int width;
    private int height;
    private int x;
    private int y;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIndXFrom() {
        return PositionConverter.PosToInd(x) - PositionConverter.PosToInd(width / 2);
    }

    public int getIndXTo() {
        return PositionConverter.PosToInd(x) + PositionConverter.PosToInd(width / 2);
    }

    public int getIndYFrom() {
        return PositionConverter.PosToInd(y) - PositionConverter.PosToInd(height / 2);
    }

    public int getIndYTo() {
        return PositionConverter.PosToInd(y) + PositionConverter.PosToInd(height / 2);
    }
}
