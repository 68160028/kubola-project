import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class FishCoin extends GameObject {

    private Image img;
    private boolean collected=false;

    public FishCoin(int x,int y){
        super(x,y,40,40);
        URL u=getClass().getResource("/assets/fish coin.png");
        img=new ImageIcon(u).getImage();
    }

    public boolean isCollected(){return collected;}
    public void collect(){collected=true;}

    @Override
    public void update() {}

    @Override
    public void draw(Graphics g){
        if(!collected) g.drawImage(img,x,y,width,height,null);
    }
}