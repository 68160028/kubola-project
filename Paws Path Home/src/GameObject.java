import java.awt.*;

public abstract class GameObject {
    protected int x, y, width, height;

    public GameObject(int x, int y, int w, int h){
        this.x = x; this.y = y;
        this.width = w; this.height = h;
    } //ตำแหน่ง + ขนาด

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    } //ตรวจการชน

    public abstract void update();
    public abstract void draw(Graphics g);
}
