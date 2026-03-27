import java.awt.*;

public class Platform extends GameObject {
    public Platform(int x,int y,int w,int h){
        super(x,y,w,h);
    }

    @Override
    public void draw(Graphics g){
        g.setColor(new Color(139,69,19));
        g.fillRect(x,y,width,height);
    }
}