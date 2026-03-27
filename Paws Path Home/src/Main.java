import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame f=new JFrame("Paws Path Home");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new GamePanel());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}