import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private       boolean              grid[][];
    private final int                  gridSize;
    private       int                  openSite;
    private final int                  begin;
    private final int                  end;
    private       WeightedQuickUnionUF wquf;


    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument out of range");
        }
        grid = new boolean[n][n];
        wquf = new WeightedQuickUnionUF(n * n + 2);
        gridSize = n;

        openSite = 0;
        begin = 0;
        end = n * n + 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }

    }       // create n-by-n grid, with all sites blocked

    public void open(int row, int col) {
        checkValue(row);
        checkValue(col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSite++;
        } else {
            return;
        }

        int sitePosition = convertIndex(row, col);

        // left
        if (col > 1 && isOpen(row, col - 1)) {
            int left = sitePosition - 1;
            wquf.union(left, sitePosition);
        }
        // right
        if (col < gridSize - 1 && isOpen(row, col + 1)) {
            int right = sitePosition + 1;
            wquf.union(right, sitePosition);
        }
        // top
        // if at first line
        if (row == 0) {
            wquf.union(begin, convertIndex(row, col));
        } else if (isOpen(row - 1, col)) {
            int top = convertIndex(row - 1, col);
            wquf.union(top, sitePosition);
        }

        // bottom
        // last line
        if (row == gridSize - 1) {
            wquf.union(end, convertIndex(row, col));
        } else if (isOpen(row + 1, col)) {
            int bot = convertIndex(row + 1, col);
            wquf.union(bot, sitePosition);
        }


    }   // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        checkValue(row);
        checkValue(col);
        return grid[row][col];
    } // is site (row, col) open?

    public boolean isFull(int row, int col) {
        checkValue(row);
        checkValue(col);
        return wquf.connected(0, convertIndex(row, col));
    } // is site (row, col) full?

    public int numberOfOpenSites() {
        return openSite;
    }     // number of open sites

    public boolean percolates() {
        return wquf.connected(0, gridSize * gridSize + 1);
    }

    private void checkValue(int input) {
        if (input > gridSize * gridSize || input < 0) {
            throw new IllegalArgumentException("Argument out of range");
        }
        // if (input < 0) {
        //     throw new IllegalArgumentException("Argument out of range");
        // }
    }

    private int convertIndex(int row, int col) {
        return row * gridSize + col + 1;
    }


}
