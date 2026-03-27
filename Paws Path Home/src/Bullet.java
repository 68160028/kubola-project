import java.awt.*;

public class Bullet extends GameObject {
    private int speed;
    private boolean alive = true;

    public Bullet(int x,int y,boolean right){
        super(x,y,20,10);
        speed = right ? 8 : -8;
    }

    public boolean isAlive(){ return alive; }

    @Override
    public void update(){
        x += speed;
        if(x < -50 || x > 900) alive = false;
    }

    @Override
    public void draw(Graphics g){
        if(alive){
            g.setColor(Color.RED);
            g.fillRect(x,y,width,height);
        } //โหลดกระสุน
    }
}