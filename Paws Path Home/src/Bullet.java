import java.awt.*;

public class Bullet extends GameObject {
    private int speed;
    private boolean alive = true; //สถานะ

    public Bullet(int x,int y,boolean right){
        super(x,y,20,10);
        speed = right ? 8 : -8;
    } //ทิศการยิง

    public boolean isAlive(){ return alive; } //เช็คสถานะกระสุน

    @Override
    public void update(){
        x += speed;
        if(x < -50 || x > 900) alive = false;
    } //ลบกระสุนออกถ้าหลุดจอ

    @Override
    public void draw(Graphics g){
        if(alive){ //แสดงรูปถ้ายังอยู่ในจอ
            g.setColor(Color.RED);
            g.fillRect(x,y,width,height);
        }
    }
}
