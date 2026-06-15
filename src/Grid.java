import java.util.*;
import java.io.*;

public class Grid {

    public int      width  = 0;
    public int      height = 0;
    public char[][] grid   = null;
    public List<String>       words      = new ArrayList<>();
    public List<Coordinate[]> results    = new ArrayList<>();
    public List<String>       foundWords = new ArrayList<>();

    public Grid() {}

    public Grid(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        readDimensions(sc);
        readGrid(sc);
        readWords(sc);
        sc.close();
    }

    // TODO (student) — do this FIRST.
    // The scanner is pointing at the first line of the file: "width,height".
    // Read that line, parse the two numbers, and store them in this.width and this.height.
    public void readDimensions(Scanner sc) {
    }

    // TODO (student) — do this SECOND.
    // The scanner is now pointing at the first row of the grid.
    // Read the next this.height lines and store each character in grid[row][col].
    public void readGrid(Scanner sc) {
    }

    // TODO (student) — do this THIRD.
    // The scanner is now pointing at the first word.
    // Read every remaining line and add each one to the words list.
    public void readWords(Scanner sc) {
    }

    // TODO (student) — do this FOURTH.
    // grid[row][col] is the first letter. Check if the rest of word continues to the right.
    // Make sure to account for going out of bounds. If your search will take you out of bounds, return null.
    // Return a Coordinate for the last letter's position, or null if the word is not there.
    public Coordinate searchRight(int row, int col, String word) {
        return null;
    }

    // TODO (student) — do this FIFTH.
    // grid[row][col] is the first letter. Check if the rest of word continues downward.
    // Return a Coordinate for the last letter's position, or null if the word is not there.
    public Coordinate searchDown(int row, int col, String word) {
        return null;
    }

    // TODO (student) — do this SIXTH.
    // grid[row][col] is the first letter. Check if the rest of word continues to the left.
    // Return a Coordinate for the last letter's position, or null if the word is not there.
    public Coordinate searchLeft(int row, int col, String word) {
        return null;
    }

    // TODO (student) — do this SEVENTH.
    // grid[row][col] is the first letter. Check if the rest of word continues upward.
    // Return a Coordinate for the last letter's position, or null if the word is not there.
    public Coordinate searchUp(int row, int col, String word) {
        return null;
    }

    // TODO (student) — do this EIGHTH.
    // grid[row][col] is the first letter. Check if the rest of word continues diagonally down-right.
    // Return a Coordinate for the last letter's position, or null if the word is not there.
    public Coordinate searchDownRight(int row, int col, String word) {
        return null;
    }

    // TODO (student) — do this NINTH.
    // grid[row][col] is the first letter. Check if the rest of word continues diagonally down-left.
    // Return a Coordinate for the last letter's position, or null if the word is not there.
    public Coordinate searchDownLeft(int row, int col, String word) {
        return null;
    }

    // TODO (student) — do this TENTH.
    // grid[row][col] is the first letter. Check if the rest of word continues diagonally up-right.
    // Return a Coordinate for the last letter's position, or null if the word is not there.
    public Coordinate searchUpRight(int row, int col, String word) {
        return null;
    }

    // TODO (student) — do this ELEVENTH.
    // grid[row][col] is the first letter. Check if the rest of word continues diagonally up-left.
    // Return a Coordinate for the last letter's position, or null if the word is not there.
    public Coordinate searchUpLeft(int row, int col, String word) {
        return null;
    }

    // PROVIDED — finds every cell containing the first letter, tries all 8 directions, and returns the match.
    public Coordinate[] search(String word) {
        if (word == null || word.isEmpty() || grid == null) return null;
        char first = word.charAt(0);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col] == first) {
                    Coordinate start = new Coordinate(row, col);
                    Coordinate end;
                    if ((end = searchRight(row, col, word))     != null) return new Coordinate[]{start, end};
                    if ((end = searchLeft(row, col, word))      != null) return new Coordinate[]{start, end};
                    if ((end = searchDown(row, col, word))      != null) return new Coordinate[]{start, end};
                    if ((end = searchUp(row, col, word))        != null) return new Coordinate[]{start, end};
                    if ((end = searchDownRight(row, col, word)) != null) return new Coordinate[]{start, end};
                    if ((end = searchDownLeft(row, col, word))  != null) return new Coordinate[]{start, end};
                    if ((end = searchUpRight(row, col, word))   != null) return new Coordinate[]{start, end};
                    if ((end = searchUpLeft(row, col, word))    != null) return new Coordinate[]{start, end};
                }
            }
        }
        return null;
    }

    // PROVIDED — calls search() for every word and collects results.
    public void searchAll() {
        results    = new ArrayList<>();
        foundWords = new ArrayList<>();
        for (String w : words) {
            Coordinate[] r = search(w);
            if (r != null) {
                results.add(r);
                foundWords.add(w);
            }
        }
    }
}
