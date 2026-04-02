import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Boss extends GameObject implements Movable {

    private Image img;
    private int hp=10; //ค่าหัวใจจริง
    private int maxHP=10; //ค่าหัวใจmax
    private int speed=3;

    public Boss(int x,int y){
        super(x,y,120,120);
        URL u=getClass().getResource("/assets/mush.png");
        img=new ImageIcon(u).getImage();
    } //โหลดรูป

    public void hit(){ if(hp>0) hp--; } //การโจมตีลดหัวใจ
    public boolean isDead(){ return hp<=0; } //เช็คการตายของBoss

    @Override
    public void update(){
        x+=speed;
    } //อัพเดทขยับตำแหน่ง

    public void update(int w){
        x+=speed;
        if (x<=0 || x>=w - width) speed*=-1;
    } //เช็คการชนขอบจอ และให้เดินวนกลับ

    @Override
    public void draw(Graphics g){
        g.drawImage(img,x,y,width,height,null);

        // HP BAR
        g.setColor(Color.GRAY);
        g.fillRect(x,y-15,120,10);

        g.setColor(Color.RED);
        int hpW = (int)((hp/(double)maxHP)*120);
        g.fillRect(x,y-15,hpW,10);
    }

    @Override
    public void move() {
        update();
    }
}
