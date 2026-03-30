import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Monster extends GameObject implements Movable {

    private Image img;
    private int dir=1; //ทิศการเดิน
    private boolean alive=true;

    public Monster(int x,int y){
        super(x,y,60,60);
        URL u=getClass().getResource("/assets/mons.png");
        img=new ImageIcon(u).getImage();
    } //โหลดรูป

    public boolean isAlive(){return alive;}
    public void die(){alive=false;} //เช็คสถานะ

    @Override
    public void update(){
        if (!alive) return; //เดินแค่ตอนยังอยู่
        x+=dir*2;
        if(x<0||x>740) dir*=-1; //ถ้าชนให้กลับทิศ
    }

    @Override
    public void draw(Graphics g){
        if(alive) g.drawImage(img,x,y,width,height,null);
    } //แสดงรูป

    @Override
    public void move() {
        update();
    }
}
