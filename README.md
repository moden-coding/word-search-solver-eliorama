[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=24138221&assignment_repo_type=AssignmentRepo)
# Word Search Solver — Student Assignment

## Project layout

```
word_search_assignment/
├── src/
│   ├── WordSearchSketch.java   provided — launches the window and draws everything
│   ├── Grid.java               YOUR WORK — four methods to complete
│   └── Found.java              provided — data class, do not modify
├── lib/
│   └── core.jar                you add this (see setup below)
├── bin/                        compiled .class files go here
└── word_search.txt             provided — the puzzle input
```

---

## Setup: adding core.jar

Copy `core.jar` from your Processing installation into the `lib/` folder.

| OS | Default path |
|---|---|
| Windows | `C:\Program Files\processing-4.x\core\library\core.jar` |
| macOS | `/Applications/Processing.app/Contents/Java/core/library/core.jar` |

**IntelliJ IDEA**
1. File → Project Structure → Modules → Dependencies tab → `+` → JARs or directories → select `lib/core.jar` → OK.
2. Set Sources Root: right-click the `src/` folder → Mark Directory As → Sources Root.
3. Set compiler output: File → Project Structure → Modules → Paths tab → set "Output path" to the `bin/` folder.

---

## How to run

**IntelliJ:** Right-click `WordSearchSketch` in the project tree → Run `main()`.

The window opens immediately even before you write any code. You will see a "Search All" button and a placeholder box where the grid will go.

---

## Your task: four methods in Grid.java

Complete these four methods in order. Each one unlocks a new visual feature.

---

### 1. `readDimensions(String filename)`

**What it does:** Opens the file, reads the very first line, and parses the two numbers separated by a comma. Store them in `this.width` and `this.height`.

**Visual milestone:** The window resizes itself to fit the puzzle, and an empty grid of the correct number of cells appears.

---

### 2. `readGrid(String filename)`

**What it does:** Opens the file again, skips the first line (the dimensions), then reads the next `this.height` lines. Each line is one row of the grid. Store each character in `grid[row][col]`.

You will need to allocate the 2-D array before filling it.

**Visual milestone:** Every cell fills in with a letter.

---

### 3. `readWords(String filename)`

**What it does:** Opens the file, skips the first line and the next `this.height` lines, then reads every remaining line as a word to search for. Add each word to the `words` list.

**Visual milestone:** A "Words to Find" panel appears on the right side of the grid listing all the words.

---

### 4. `search(String word)`

**What it does:** Searches the `grid` array for the given word in all 8 directions (horizontal, vertical, and both diagonals, each forwards and backwards). If found, return a `Found` object with the row and column where the word starts and where it ends. If not found, return `null`.

**Visual milestone:** Clicking **Search All** highlights every word that your `search` method correctly locates, turning its name green in the word list. The more words your search finds, the more highlights appear.
