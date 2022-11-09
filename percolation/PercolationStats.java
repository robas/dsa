/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceInterval;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        double[] results = new double[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = runTrial(n);
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        double factor = 1.96;
        confidenceInterval = factor * stddev / Math.sqrt(trials);
    }

    // executes a trial on an n-by-n grid
    private static double runTrial(int n) {
        Percolation p = new Percolation(n);

        int row, col;
        while (!p.percolates()) {
            row = StdRandom.uniformInt(1, n + 1);
            col = StdRandom.uniformInt(1, n + 1);
            p.open(row, col);
        }
        double openedSites = (double) p.numberOfOpenSites();
        double n2 = (double) n * n;
        double percolationThreshold = openedSites / n2;
        return percolationThreshold;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - confidenceInterval;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + confidenceInterval;
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);
        System.out.printf("mean \t\t\t = %f\n", ps.mean());
        System.out.printf("stddev \t\t\t = %f\n", ps.mean());
        System.out.printf("95%% confidence interval  = [%f, %f]\n", ps.confidenceLo(),
                          ps.confidenceHi());
    }
}
