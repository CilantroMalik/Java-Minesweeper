public class Tile {
    private boolean flagged = false;
    private boolean revealed = false;
    private boolean hasMine = false;
    private int x, y, w, h;
    private int numAround = 0;
    private boolean wonGame = false;

    public Tile(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        if (Math.random() < (Math.random()*0.07 + 0.1)) hasMine = true;
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setW(int w) { this.w = w; }
    public void setH(int h) { this.h = h; }
    public int getNumAround() { return numAround; }
    public void setNumAround(int numAround) { this.numAround = numAround; }
    public boolean hasMine() { return hasMine; }
    public boolean isWon() { return wonGame; }
    public void setWon() { wonGame = true; }

    public boolean mouseIsOver(int mouseX, int mouseY) {
        return (mouseX > x && mouseX < x+w && mouseY > y && mouseY < y+h);
    }

    public boolean isFlagged() { return flagged; }

    public boolean isRevealed() { return revealed; }

    public void flag() {
        flagged = true;
    }

    // if this method returns false, the player loses because they revealed a tile that had a mine.
    public boolean reveal() {
        revealed = true;
        return !hasMine;
    }
}
