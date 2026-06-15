import processing.core.*;
import java.util.*;

public class WordSearchSketch extends PApplet {

    private Grid grid;

    private static final int CELL  = 34;
    private static final int MAR   = 15;
    private static final int HEAD  = 55;
    private static final int WLIST = 175;
    private static final int BTN_H = 36;
    private static final int MIN_W = 620;
    private static final int MIN_H = 520;

    private boolean windowSized = false;
    private int btnX, btnY, btn2X, btn2Y;
    private final int btnW = 130;
    private final int btnH = BTN_H;
    private int[] hColors;

    public static void main(String[] args) {
        PApplet.main("WordSearchSketch");
    }

    public void settings() {
        size(MIN_W, MIN_H);
    }

    public void setup() {
        try {
            grid = new Grid("word_search.txt");
        } catch (java.io.FileNotFoundException e) {
            println("word_search.txt not found — place it in the project root.");
            grid = new Grid();
        }
        surface.setTitle("Word Search");
        textFont(createFont("Monospaced", 14));
        hColors = new int[]{
            color(255,  80,  80, 150), color( 80, 200,  80, 150),
            color( 80, 130, 255, 150), color(255, 210,  40, 150),
            color(255, 140,  40, 150), color(190,  80, 255, 150),
            color( 40, 220, 220, 150), color(255, 140, 200, 150)
        };
    }

    public void draw() {
        resizeIfNeeded();
        background(245);
        drawHeader();
        drawGridBorders();
        drawHighlights();
        drawGridLetters();
        drawWordPanel();
        drawButton();
    }

    public void mousePressed() {
        if (mouseX >= btnX && mouseX <= btnX + btnW
                && mouseY >= btnY && mouseY <= btnY + btnH) {
            grid.searchAll();
        }
        if (mouseX >= btn2X && mouseX <= btn2X + btnW
                && mouseY >= btn2Y && mouseY <= btn2Y + btnH) {
            showAnswer();
        }
    }

    // -------------------------------------------------------------------------
    // Layout helpers — private; never accessible to Grid or the student
    // -------------------------------------------------------------------------

    private int   gridLeft() { return MAR; }
    private int   gridTop()  { return HEAD; }
    private int   gridW()    { return grid.width  > 0 ? grid.width  * CELL : 200; }
    private int   gridH()    { return grid.height > 0 ? grid.height * CELL : 200; }
    private float cellCX(int col) { return gridLeft() + col * CELL + CELL * 0.5f; }
    private float cellCY(int row) { return gridTop()  + row * CELL + CELL * 0.5f; }

    // -------------------------------------------------------------------------
    // Resize
    // -------------------------------------------------------------------------

    private void resizeIfNeeded() {
        if (!windowSized && grid.width > 0 && grid.height > 0) {
            int w = MAR + grid.width * CELL + MAR + WLIST;
            int h = HEAD + grid.height * CELL + MAR + BTN_H + MAR;
            surface.setSize(Math.max(w, MIN_W), Math.max(h, MIN_H));
            windowSized = true;
        }
    }

    // -------------------------------------------------------------------------
    // Draw passes (order matters: borders → highlights → letters → UI)
    // -------------------------------------------------------------------------

    private void drawHeader() {
        fill(30);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("Word Search", width * 0.5f, HEAD * 0.5f);
    }

    private void drawGridBorders() {
        if (grid.width <= 0 || grid.height <= 0) {
            fill(210);
            noStroke();
            rect(MAR, HEAD, 200, 200, 5);
            fill(120);
            textSize(12);
            textAlign(CENTER, CENTER);
            text("Grid not loaded yet", MAR + 100, HEAD + 100);
            return;
        }
        stroke(185);
        strokeWeight(1);
        noFill();
        for (int r = 0; r < grid.height; r++) {
            for (int c = 0; c < grid.width; c++) {
                rect(gridLeft() + c * CELL, gridTop() + r * CELL, CELL, CELL);
            }
        }
    }

    private void drawHighlights() {
        if (grid.results == null || grid.results.isEmpty()) return;
        if (grid.width <= 0 || grid.height <= 0) return;
        strokeWeight(CELL * 0.72f);
        strokeCap(ROUND);
        noFill();
        for (int i = 0; i < grid.results.size(); i++) {
            Coordinate[] r = grid.results.get(i);
            if (!inBounds(r)) continue;
            String word = i < grid.foundWords.size() ? grid.foundWords.get(i) : "";
            int colorIdx = grid.words.indexOf(word);
            stroke(hColors[colorIdx % hColors.length]);
            line(cellCX(r[0].col), cellCY(r[0].row),
                 cellCX(r[1].col), cellCY(r[1].row));
        }
        strokeCap(SQUARE);
        strokeWeight(1);
    }

    private void drawGridLetters() {
        if (grid.width <= 0 || grid.height <= 0 || grid.grid == null) return;
        fill(30);
        textSize(14);
        textAlign(CENTER, CENTER);
        noStroke();
        for (int r = 0; r < grid.height; r++) {
            if (r >= grid.grid.length || grid.grid[r] == null) continue;
            for (int c = 0; c < grid.width; c++) {
                if (c >= grid.grid[r].length) continue;
                char ch = grid.grid[r][c];
                if (ch != '\0') text(ch, cellCX(c), cellCY(r));
            }
        }
    }

    private void drawWordPanel() {
        if (grid.words == null || grid.words.isEmpty()) return;
        int px = MAR + gridW() + MAR;
        int py = HEAD;
        int pw = WLIST - MAR;
        int ph = gridH();

        fill(225);
        noStroke();
        rect(px, py, pw, ph, 5);

        Set<String> found = new HashSet<>(grid.foundWords);

        fill(40);
        textSize(12);
        textAlign(LEFT, TOP);
        text("Words to find:", px + 7, py + 7);
        for (int i = 0; i < grid.words.size(); i++) {
            String w = grid.words.get(i);
            fill(found.contains(w) ? color(50, 160, 50) : color(30));
            text(w, px + 7, py + 26 + i * 19);
        }
    }

    private void drawButton() {
        if (grid.width > 0 && grid.height > 0) {
            btnX  = gridLeft();
            btn2X = btnX + btnW + MAR;
            btnY  = HEAD + grid.height * CELL + (MAR / 2);
        } else {
            btnX  = MAR;
            btn2X = btnX + btnW + MAR;
            btnY  = HEAD + 215;
        }
        btn2Y = btnY;

        boolean h1 = mouseX >= btnX  && mouseX <= btnX  + btnW && mouseY >= btnY && mouseY <= btnY + btnH;
        boolean h2 = mouseX >= btn2X && mouseX <= btn2X + btnW && mouseY >= btnY && mouseY <= btnY + btnH;

        noStroke();
        fill(h1 ? color(50, 120, 220) : color(70, 140, 240));
        rect(btnX, btnY, btnW, btnH, 5);
        fill(h2 ? color(200, 60, 60) : color(220, 80, 80));
        rect(btn2X, btn2Y, btnW, btnH, 5);

        fill(255);
        textSize(13);
        textAlign(CENTER, CENTER);
        text("Search All",  btnX  + btnW * 0.5f, btnY + btnH * 0.5f);
        text("Show Answer", btn2X + btnW * 0.5f, btnY + btnH * 0.5f);
    }

    // -------------------------------------------------------------------------
    // Hardcoded answer — independent of student's search() implementation
    // -------------------------------------------------------------------------

    private void showAnswer() {
        grid.results = new ArrayList<>(Arrays.asList(
            new Coordinate[]{ new Coordinate(4,  4), new Coordinate(4, 10) },
            new Coordinate[]{ new Coordinate(5,  7), new Coordinate(5,  2) },
            new Coordinate[]{ new Coordinate(1,  8), new Coordinate(7, 14) },
            new Coordinate[]{ new Coordinate(12, 9), new Coordinate(6,  9) },
            new Coordinate[]{ new Coordinate(8, 14), new Coordinate(14,14) },
            new Coordinate[]{ new Coordinate(10, 6), new Coordinate(4,  0) },
            new Coordinate[]{ new Coordinate(7,  8), new Coordinate(13, 2) },
            new Coordinate[]{ new Coordinate(11, 2), new Coordinate(5,  8) }
        ));
        grid.foundWords = new ArrayList<>(Arrays.asList(
            "HARVEST", "PLANET", "SHELTER", "JASMINE",
            "DIAMOND", "PASSAGE", "TORPEDO", "CRYSTAL"
        ));
    }

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    private boolean inBounds(Coordinate[] r) {
        return r != null && r.length == 2
            && r[0] != null && r[1] != null
            && r[0].row >= 0 && r[0].row < grid.height
            && r[0].col >= 0 && r[0].col < grid.width
            && r[1].row >= 0 && r[1].row < grid.height
            && r[1].col >= 0 && r[1].col < grid.width;
    }

    // -------------------------------------------------------------------------
    // Internal data class — not part of the student assignment
    // -------------------------------------------------------------------------

    private static class Found {
        String word;
        int startRow, startCol, endRow, endCol;
        Found(String word, int startRow, int startCol, int endRow, int endCol) {
            this.word = word;
            this.startRow = startRow; this.startCol = startCol;
            this.endRow   = endRow;   this.endCol   = endCol;
        }
    }
}
