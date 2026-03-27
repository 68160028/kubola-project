import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private Player player;
    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<FishCoin> coins = new ArrayList<>();
    private ArrayList<Monster> monsters = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Boss boss;

    private boolean left=false, right=false;
    private boolean canShoot=true;

    private int level=1, lives=3, score=0;
    private boolean menu=true, gameOver=false, win=false;

    private Image bgHome,bg1,bg2,bg3,bg4,heart;
    private Rectangle startBtn = new Rectangle(300,300,200,60);
    private Rectangle retryBtn = new Rectangle(300,280,200,60);
    private Rectangle menuBtn  = new Rectangle(300,360,200,60);

    public GamePanel(){
        setPreferredSize(new Dimension(800,600));
        setFocusable(true);
        loadImg();
        startLevel();

        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(menu && startBtn.contains(e.getPoint())) menu=false;
                if(gameOver || win){
                    if(retryBtn.contains(e.getPoint())) reset();
                    if(menuBtn.contains(e.getPoint())) { menu=true; reset(); }
                }
            }
        });

        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(menu || gameOver || win) return;

                if(e.getKeyCode()==KeyEvent.VK_A) left=true;
                if(e.getKeyCode()==KeyEvent.VK_D) right=true;
                if(e.getKeyCode()==KeyEvent.VK_SPACE) player.jump();
                if(e.getKeyCode()==KeyEvent.VK_UP) player.jump(20);

                if(e.getKeyCode()==KeyEvent.VK_K && canShoot && score>0){
                    bullets.add(new Bullet(player.x + player.width/2, player.y + player.height/2, player.isRight()));
                    score--;
                    canShoot=false;
                }
            }
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_A) left=false;
                if(e.getKeyCode()==KeyEvent.VK_D) right=false;
                if(e.getKeyCode()==KeyEvent.VK_K) canShoot=true;
            }
        });

        new Thread(this).start();
    }

    private Image load(String p){ return new ImageIcon(getClass().getResource(p)).getImage(); }

    private void loadImg(){
        bgHome=load("/assets/PPH bg.png");
        bg1=load("/assets/PPH bg lv1.png");
        bg2=load("/assets/PPH bg lv2.png");
        bg3=load("/assets/PPH bg lv3.png");
        bg4=load("/assets/PPH bg lv4.png");
        heart=load("/assets/heart.png");
    }

    private void startLevel(){
        player = new Player(0, 300); // เริ่มที่ริมซ้ายสุด
        platforms.clear(); coins.clear(); monsters.clear(); bullets.clear(); boss=null;

        platforms.add(new Platform(0,500,800,20));
        platforms.add(new Platform(150,400,120,20));
        platforms.add(new Platform(350,350,120,20));
        platforms.add(new Platform(550,300,120,20));
        platforms.add(new Platform(250,250,120,20));
        platforms.add(new Platform(450,200,120,20));

        int coinCount = 6 + (int)(Math.random()*5);
        for(int i=0;i<coinCount;i++){
            int x = 100 + (int)(Math.random()*600);
            int y = 200 + (int)(Math.random()*250);
            coins.add(new FishCoin(x,y));
        }

        if(level==2) monsters.add(new Monster(500,420));
        if(level==3){
            monsters.add(new Monster(300,420));
            monsters.add(new Monster(500,420));
            monsters.add(new Monster(650,420));
        }
        if(level==4) boss = new Boss(300,350);
    }

    private void reset(){ level=1; lives=3; score=0; gameOver=false; win=false; startLevel(); }

    public void run(){
        while(true){ update(); repaint(); try{Thread.sleep(16);}catch(Exception e){} }
    }

    public void update(){
        if(menu || gameOver || win) return;

        if(left) player.setLeft(true); else player.setLeft(false);
        if(right) player.setRight(true); else player.setRight(false);

        //ใช้polymorphismกับmovable
        ArrayList<Movable> movables = new ArrayList<>();
        movables.add(player);
        for (Monster m : monsters) movables.add(m);

        for (Movable m : movables) {
            m.move();
        }

        player.keepInBounds(getWidth());
        player.checkPlatform(platforms);


        for(int i=0;i<bullets.size();i++){
            Bullet b = bullets.get(i);
            b.update();
            if(!b.isAlive()){ bullets.remove(i); i--; }
        }

        for(FishCoin c:coins){
            if(!c.isCollected() && player.getBounds().intersects(c.getBounds())){
                c.collect(); score++;
            }
        }

        for(Monster m:monsters){
            m.update();
            if(m.isAlive() && player.getBounds().intersects(m.getBounds())){
                if(player.getBounds().y + player.height - 10 < m.y){ m.die(); score++; }
                else if(!player.isInvincible()){
                    player.hit(); lives--;
                    if(player.x < m.x) player.x -= 50; else player.x += 50;
                }
            }
        }

        if(level==4 && boss != null){
            boss.update(getWidth());
            for(int i=0;i<bullets.size();i++){
                Bullet b = bullets.get(i);
                if(b.getBounds().intersects(boss.getBounds())){ boss.hit(); bullets.remove(i); i--; }
            }
            if(player.getBounds().intersects(boss.getBounds()) && !player.isInvincible()){
                player.hit(); lives--;
                if(player.x < boss.x) player.x -= 50; else player.x += 50;
            }
            if(boss.isDead()) win=true;
        }

        if(lives<=0) gameOver=true;

        if(player.x >= getWidth() - player.width - 5){
            if(level<4){ level++; startLevel(); }
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(menu){ g.drawImage(bgHome,0,0,getWidth(),getHeight(),null); drawButton(g,startBtn,"START"); return; }

        Image bg = switch(level){ case 1->bg1; case 2->bg2; case 3->bg3; default->bg4; };
        g.drawImage(bg,0,0,getWidth(),getHeight(),null);

        for(Platform p:platforms) p.draw(g);
        for(FishCoin c:coins) c.draw(g);
        for(Monster m:monsters) m.draw(g);
        for(Bullet b:bullets) b.draw(g);
        if(level==4 && boss!=null) boss.draw(g);
        player.draw(g);

        for(int i=0;i<lives;i++) g.drawImage(heart,20+i*35,20,30,30,null);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial",Font.BOLD,18));
        g.drawString("Fish: "+score,20,70);
        g.drawString("Level: "+level,20,100);

        if(gameOver){
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER",250,200);
            drawButton(g,retryBtn,"RESTART");
            drawButton(g,menuBtn,"MENU");
        }
        if(win){
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.setColor(Color.GREEN);
            g.drawString("YOU WIN!",250,200);
            drawButton(g,retryBtn,"RESTART");
            drawButton(g,menuBtn,"MENU");
        }
    }

    private void drawButton(Graphics g, Rectangle r, String text){
        g.setColor(new Color(50,50,50,200));
        g.fillRoundRect(r.x,r.y,r.width,r.height,20,20);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,20));
        FontMetrics fm = g.getFontMetrics();
        int tx = r.x + (r.width - fm.stringWidth(text))/2;
        int ty = r.y + (r.height + fm.getAscent())/2 - 5;
        g.drawString(text,tx,ty);
    }
}