import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class Player extends GameObject {

    private int dx = 0, dy = 0;
    private int gravity = 1;
    private boolean jumping = false;
    private int invincible = 0;

    private Image imgR, imgL;
    private boolean right = true;

    public Player(int x, int y) {
        super(x, y, 80, 80);
        imgR = load("/assets/cat R.png");
        imgL = load("/assets/cat L.png");
    }

    private Image load(String p) {
        URL u = getClass().getResource(p);
        return new ImageIcon(u).getImage();
    }

    public void setLeft(boolean b) {
        if (b) {
            if (x > 0) dx = -5;
            else dx = 0;
            right = false;
        } else if (dx < 0) dx = 0;
    }

    public void setRight(boolean b) {
        if (b) {
            dx = 5;
            right = true;
        } else if (dx > 0) dx = 0;
    }

    public void jump() {
        if (!jumping) {
            dy = -15;
            jumping = true;
        }
    }

    public void hit() {
        if (invincible <= 0) invincible = 60;
    }

    public boolean isInvincible() {
        return invincible > 0;
    }

    public boolean isRight() {
        return right;
    }

    @Override
    public void update() {
        x += dx;
        y += dy;
        dy += gravity;
        if (invincible > 0) invincible--;
    }

    public void checkPlatform(ArrayList<Platform> ps) {
        boolean on = false;
        for (Platform p : ps) {
            Rectangle feet = new Rectangle(x, y + height, width, 5);
            if (feet.intersects(p.getBounds()) && dy >= 0) {
                y = p.y - height;
                dy = 0;
                jumping = false;
                on = true;
            }
        }
        if (!on && y > 500 - height) {
            y = 500 - height;
            dy = 0;
            jumping = false;
        }
    }

    public void keepInBounds(int w) {
        if (x < 0) { x = 0; dx = 0; }
        if (x > w - width) { x = w - width; dx = 0; }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(right ? imgR : imgL, x, y, width, height, null);
    }
}