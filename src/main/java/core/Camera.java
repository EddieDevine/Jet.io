package core;

public class Camera {
    private int x, y;
    private Player player;

    public Camera(Player player) {
        this.player = player;
        this.x = player.getX();
        this.y = player.getY();
    }

    public void update() {
        this.x = player.getX() - 400; // Center player horizontally
        this.y = player.getY() - 300; // Center player vertically
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
