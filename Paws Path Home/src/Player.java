import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class Player extends GameObject implements Movable {

    private int dx = 0, dy = 0; //ความเร็วx,y
    private int gravity = 1; //แรงโน้มถ่วง
    private boolean jumping = false;
    private int invincible = 0; //สถานะอมตะ

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
        if (b) dx = -5;
        else if (dx < 0) dx = 0;
        right = !b;
    }

    public void setRight(boolean b) {
        if (b) dx = 5;
        else if (dx > 0) dx = 0;
        right = b;
    } //กำหนดทิศให้รูป

    public void jump() {
        if (!jumping) { //กันกระโดดกลางอากาศ
            dy = -15; //ให้ลอยสูงขึ้น
            jumping = true; //บอกสถานะว่าตอนนี้กระโดดอยู่
        }
    }

    public void jump(int power) {
        if (!jumping) {
            dy = -power;
            jumping = true;
        }
    } //กระโดดสูงjump()

    public void hit() {
        if (invincible <= 0) invincible = 60;
    } //ถ้าโดนตีให้เป็นอมตะ1วิ

    public boolean isInvincible() {
        return invincible > 0;
    } //เช็คสถานะอมตะ

    public boolean isRight() {
        return right;
    } //เช็คการทิศการหันของผู้เล่น

    public void checkPlatform(ArrayList<Platform> ps) { //เช็คสถานะยืนบนPlatform
        boolean on = false;
        for (Platform p : ps) { //ps=พื้นทั้งหมด
            Rectangle feet = new Rectangle(x, y + height, width, 5); //สร้างกล่องใต้เท้าตัวละคร
            if (feet.intersects(p.getBounds()) && dy >= 0) { //เช็คตอนเท้าชนPlatform + กำลังตก
                y = p.y - height; //y=ตำแหน่งบนผู้เล่น p.s=ตำแหน่งบนPlatform ให้ยืนบนพื้นพอดี
                dy = 0;
                jumping = false;
                on = true;
            }
        }
        if (!on && y > 500 - height) { //ถ้ายังไม่ตกให้ตกจนถึงPlatform
            y = 500 - height;
            dy = 0;
            jumping = false;
        }
    }

    public void keepInBounds(int w) {
        if (x < 0) { x = 0; dx = 0; }
        if (x > w - width) { x = w - width; dx = 0; }
    } //กันออกนอกจอ

    @Override
    public void draw(Graphics g) {
        g.drawImage(right ? imgR : imgL, x, y, width, height, null);
    } //ใช้รูปตามทิศที่หัน

    @Override
    public void move() {
        update();
    }

    @Override
    public void update() {
        x+=dx;
        y+=dy;
        dy+=gravity;
        if (invincible > 0) invincible--;
    }

}
