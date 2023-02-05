package conwaygame;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicBorders.ToggleButtonBorder;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(file);
        
        int r = StdIn.readInt();
        int c = StdIn.readInt();
        grid = new boolean[r][c];
        totalAliveCells = 0;

        for(int x = 0; x < r; x++)
        {
            for (int y = 0; y < c; y++)
            {
                grid[x][y] = StdIn.readBoolean();
                if(grid[x][y])
                totalAliveCells++;
            }
        }

    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        return grid[row][col]; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE
        if(totalAliveCells > 0)
            return true; // update this line, provided so that code compiles
        else
            return false;
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        // WRITE YOUR CODE HERE
        int r = grid.length;
        int c = grid[row].length;
        int count = 0;

        int coln  = col+1;
        int colp  = col-1;
        coln = coln % c;
        if (colp < 0) colp = colp + c;


        int rown = row+1;
        int rowp = row -1;
        rown = rown % r;
        if(rowp < 0) rowp = rowp + r;

        if(grid[row][coln])
            count++;
        if(grid[row][colp])
            count++;
        if(grid[rowp][colp])
            count++;
        if(grid[rowp][col])
            count++;
        if(grid[rowp][coln])
            count++;
        if(grid[rown][colp])
            count++;
        if(grid[rown][col])
            count++;
        if(grid[rown][coln])
            count++;
        
        StdOut.println("numOfAliveNeighbors["+row+"]["+col+"] = " + count);
        return count; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        // WRITE YOUR CODE HERE
        int r = grid.length;
        int c = grid[0].length;
        boolean[][] newGrid = new boolean[r][c];

        for (int x = 0; x < r; x++)
        {
            for (int y = 0; y< c; y++)
            {
                int neighbors = numOfAliveNeighbors(x, y);
                if (grid[x][y] && neighbors <= 1)
                {
                    newGrid[x][y] = false;
                }
                if (grid[x][y] && neighbors >= 4)
                {
                    newGrid[x][y] = false;
                }
                if (grid[x][y] && (neighbors == 2 || neighbors == 3))
                {
                    newGrid[x][y] = true;
                }
                if (grid[x][y] == false && neighbors == 3)
                {
                    newGrid[x][y] = true;
                }
                
            }
        }
        return newGrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        int r = grid.length;
        int c = grid[0].length;
        grid = computeNewGrid();
        totalAliveCells = 0;
        for (int x = 0; x < r; x++)
        {
            for (int y = 0; y< c; y++)
            {
                if(grid[x][y])
                {
                    totalAliveCells++;
                }
            }
        }
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        for (int i = 0; i < n; i++)
        {
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE
        int r = grid.length;
        int c = grid[0].length;
        WeightedQuickUnionUF qf = new WeightedQuickUnionUF(r, c);
        for (int row = 0; row < r; row++)
        {
            for (int col = 0; col < c; col++)
            {
                int colp = col - 1;
                int coln = col + 1;
                coln = coln % c;
                if(colp < 0) colp = colp + c;

                int rowp = row - 1;
                int rown = row + 1;
                rown = rown % r;
                if(rowp < 0) rowp = rowp + r;

                if(grid[row][coln] && grid[row][col])
                    qf.union(row,col,row,coln);

                if(grid[row][colp] && grid[row][col])
                    qf.union(row, col, row, colp);

                if(grid[rowp][colp] && grid[row][col])
                    qf.union(row, col, rowp, colp);

                if(grid[rowp][col] && grid[row][col])
                    qf.union(row, col, rowp, col);

                if(grid[rowp][coln] && grid[row][col])
                    qf.union(row, col, rowp, coln);

                if(grid[rown][colp] && grid[row][col])
                    qf.union(row, col, rown, colp);

                if(grid[rown][col] && grid[row][col])
                    qf.union(row, col, rown, col);
                    
                if(grid[rown][coln] && grid[row][col])
                    qf.union(row, col, rown, coln);
            }
        }

        boolean z[] = new boolean[r * c];
        
        for(int x = 0; x < r; x++)
        {
            for (int y = 0; y < c; y++)
            {
                int f = qf.find(x, y);
                if (grid[x][y])
                    z[f] = true;
            }
        }
        int nc = 0;
        for(int x = 0; x < z.length; x++)
        {
            if(z[x])
            {
                nc++;
            }
        }
        return nc; // update this line, provided so that code compiles
    }
}
