import java.awt.*;

public class GameObject {
    protected int x, y, width, height;

    public GameObject(int x, int y, int w, int h){
        this.x = x; this.y = y;
        this.width = w; this.height = h;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public void update(){}
    public void draw(Graphics g){}
}