import java.awt.*;
import java.awt.event.*;

public class AnimationCanvas extends Canvas implements MouseMotionListener
{
    int[][] canvasGrid;
    Tile[][] tiles;
    private int mouseX = 0;
    private int mouseY = 0;
    boolean beginning = true;

    public AnimationCanvas(Tile[][] tiles)
    {
        addMouseMotionListener(this);
        this.tiles = tiles;
    }

    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {}

    public void paint(Graphics g)
    {
        int recWidth = (getHeight()) / canvasGrid[0].length;
        int recHeight = (getHeight()-20) / canvasGrid.length;

        g.setColor(Color.WHITE);
        g.fillRect(0,0,(recWidth * canvasGrid[0].length) ,(recHeight * canvasGrid[0].length));

        int fontSize = 20;
        g.setFont(new Font("DIN Pro", Font.PLAIN, fontSize));
        g.setColor(Color.BLACK);

        for(int r = 0; r < canvasGrid.length; r++)
        {
            for(int c = 0; c < canvasGrid[0].length; c++)
            {
                if (beginning) {
                    g.setColor(Color.WHITE);
                    g.drawString("e", 0, 0);
                    beginning = false;
                }
                g.setColor(Color.BLACK);
                if (tiles[r][c].isRevealed()) g.setColor(Color.GRAY);
                if (tiles[r][c].mouseIsOver(mouseX, mouseY)) g.setColor(Color.DARK_GRAY);
                if (tiles[r][c].isRevealed() && tiles[r][c].hasMine()) {
                    g.setColor(Color.RED);
                    if (tiles[r][c].isWon()) g.setColor(Color.GREEN);
                }
                g.fillRect((c * recWidth+1) , (r * recHeight+1)  , recWidth-2, recHeight-2);
                if (tiles[r][c].isFlagged()) {
                    g.setColor(Color.CYAN);
                    g.drawString("F", (c*recWidth - 5 + recWidth/2), (r*recHeight - 5 + recHeight/2));
                }
                if (tiles[r][c].isRevealed()) {
                    if (!tiles[r][c].hasMine()) {
                        g.setColor(Color.CYAN);
                        g.drawString(String.valueOf(tiles[r][c].getNumAround()), (c * recWidth - 5 + recWidth / 2), (r * recHeight - 5 + recHeight / 2));
                    }
                }
            }
        }
    }
}
