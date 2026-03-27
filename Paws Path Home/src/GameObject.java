import java.awt.*;

public abstract class GameObject {
    protected int x, y, width, height;

    public GameObject(int x, int y, int w, int h){
        this.x = x; this.y = y;
        this.width = w; this.height = h;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public abstract void update();
    public abstract void draw(Graphics g);
}