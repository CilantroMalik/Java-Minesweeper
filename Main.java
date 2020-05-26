import javax.swing.JFrame;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) {
        Main main = new Main();
    }

    int GRID_HEIGHT = 10;
    int GRID_WIDTH = 10;
    JFrame window;
    Scanner keyboardInput = new Scanner(System.in);
    AnimationCanvas canvas;
    int[][] grid = new int[GRID_HEIGHT][GRID_WIDTH];
    KeyHandler listener = new KeyHandler(this);
    char gameOver = '-';


    //at vale of 0, empty space
    public Main()
    {
        Scanner in = new Scanner(System.in);

        // create 2D array of tiles with mines randomly placed
        Tile[][] tiles = new Tile[GRID_WIDTH][GRID_HEIGHT];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(0, 0, 0, 0);
            }
        }

        window = new JFrame("Grid-Based Game");
        canvas = new AnimationCanvas(tiles);
        canvas.addKeyListener(listener);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(750,750);
        window.add(canvas);
        window.setVisible(true);
        canvas.canvasGrid = grid;

        // setup the tiles: give them their position information and the number of mines around them
        int w = (canvas.getHeight()) / canvas.canvasGrid[0].length;
        int h = (canvas.getHeight()-20) / canvas.canvasGrid.length;
        for (int i = 0; i < canvas.tiles.length; i++) {
            for (int j = 0; j < canvas.tiles[0].length; j++) {
                canvas.tiles[i][j].setX(j*w + 1);
                canvas.tiles[i][j].setY(i*h + 1);
                canvas.tiles[i][j].setW(w);
                canvas.tiles[i][j].setH(h);
            }
        }

        // find number of mines around each tile
        for (int i = 0; i < canvas.tiles.length; i++) {
            for (int j = 0; j < canvas.tiles[0].length; j++) {
                int numAround = 0;
                // TODO implement numAround functionality
                // check each adjacent cell and if it has a mine, add to the accumulator
                // then assign the tile's numAround value to the accumulator's value
                for (int k = i-1; k <= i+1; k++) {
                    for (int l = j-1; l <= j+1; l++) {
                        if (k >= 0 && k < canvas.tiles.length && l>= 0 && l < canvas.tiles[0].length)
                            if (canvas.tiles[k][l].hasMine()) numAround++;
                    }
                }
                tiles[i][j].setNumAround(numAround);
            }
        }

        /*
         * Main loop of the game below
         */
        while (true) {
            boolean win = true;
            for (int i = 0; i < canvas.tiles.length; i++) {
                for (int j = 0; j < canvas.tiles[0].length; j++) {
                    // if the tile has a mine, but is not flagged, you haven't won yet.
                    // alternatively, if the tile doesn't have a mine, but is not revealed, you haven't won yet.
                    if ((canvas.tiles[i][j].hasMine() && !canvas.tiles[i][j].isFlagged())
                            || !(canvas.tiles[i][j].hasMine() || canvas.tiles[i][j].isRevealed())) win = false;
                }
            }
            if (win) gameOver = 'w';
            if (gameOver != '-') {
                for (int i = 0; i < canvas.tiles.length; i++) {
                    for (int j = 0; j < canvas.tiles[0].length; j++) {
                        canvas.tiles[i][j].reveal();
                        if (gameOver == 'w') canvas.tiles[i][j].setWon();
                    }
                }
                break;
            }
            canvas.repaint();
        }

    }

    public void revealTiles(int i, int j) {
        if (!canvas.tiles[i][j].reveal()) {
            gameOver = 'l';
        } else {
            if (canvas.tiles[i][j].getNumAround() == 0) {
                for (int k = i-1; k < i+1; k++) {
                    for (int l = j-1; l < j+1; l++) {
                        if (k >= 0 && k < canvas.tiles.length && l >= 0 && l < canvas.tiles[0].length)
                            if (k != i || l != j) revealTiles(k, l);
                    }
                }
            }
        }
    }

    public void spacePressed(int mouseX, int mouseY) {
        System.out.println("MouseX: " + mouseX + " MouseY: " + mouseY);
        for (int i = 0; i < canvas.tiles.length; i++) {
            for (int j = 0; j < canvas.tiles[0].length; j++) {
                if (canvas.tiles[i][j].mouseIsOver(mouseX, mouseY))
                    revealTiles(i, j);
            }
        }
    }

    public void fPressed(int mouseX, int mouseY) {
        System.out.println("MouseX: " + mouseX + " MouseY: " + mouseY);
        for (int i = 0; i < canvas.tiles.length; i++) {
            for (int j = 0; j < canvas.tiles[0].length; j++) {
                if (canvas.tiles[i][j].mouseIsOver(mouseX, mouseY)) canvas.tiles[i][j].flag();
            }
        }
    }
}