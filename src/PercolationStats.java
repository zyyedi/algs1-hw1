import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.awt.*;

public class PercolationStats {
    private int      n;
    private int      trials;
    private double[] result;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Argument must large than 0");
        }
        this.n = n;
        this.trials = trials;

    }  // perform trials independent experiments on an n-by-n grid

    public double mean() {
        return StdStats.mean(result);
    }                        // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(result);
    }                  // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(this.trials);
    }               // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(this.trials);
    }           // high endpoint of 95% confidence interval


    public void setResult(double[] results) {
        this.result = results;
    }

    public static void main(String[] args) {
        int              n                = Integer.parseInt(args[0]);
        int              trail            = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trail);
        int              gridNum          = n * n;
        int              row, col;
        double[]         result           = new double[trail];


        for (int i = 0; i < trail; i++) {
            int         random;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                random = StdRandom.uniform(gridNum);
                row = random / n;
                col = random % n;
                percolation.open(row, col);
            }
            // System.out.println(percolation.numberOfOpenSites());
            result[i] = percolation.numberOfOpenSites() / (double) gridNum;
        }
        percolationStats.setResult(result);

        System.out.println("Mean                    = " + percolationStats.mean()
                + "\nStddev                   = " + percolationStats.stddev()
                + "\n95% confidence interval    = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");

    }
}
