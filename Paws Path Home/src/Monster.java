import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Monster extends GameObject {

    private Image img;
    private int dir=1;
    private boolean alive=true;

    public Monster(int x,int y){
        super(x,y,60,60);
        URL u=getClass().getResource("/assets/mons.png");
        img=new ImageIcon(u).getImage();
    }

    public boolean isAlive(){return alive;}
    public void die(){alive=false;}

    @Override
    public void update(){
        if(!alive) return;
        x+=dir*2;
        if(x<0||x>740) dir*=-1;
    }

    @Override
    public void draw(Graphics g){
        if(alive) g.drawImage(img,x,y,width,height,null);
    }
}