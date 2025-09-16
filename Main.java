import Game2048.*;

public class Main {
    public static void main(String[] args) {
        GameCore gc=new GameCore(4,6,null);
        GameUI gui=new GameUI(gc);
        gui.setVisible(true);
    }
}
