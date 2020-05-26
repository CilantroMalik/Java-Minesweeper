import java.awt.event.*;

public class KeyHandler extends KeyAdapter {
    private Main main;

    public KeyHandler(Main main) {
        this.main = main;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("space pressed");
            main.spacePressed(main.canvas.getMouseX(), main.canvas.getMouseY());
        }
        else if (e.getKeyCode() == KeyEvent.VK_F) {
            System.out.println("f pressed");
            main.fPressed(main.canvas.getMouseX(), main.canvas.getMouseY());
        }
    }
}
