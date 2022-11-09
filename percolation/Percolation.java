/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int CLOSED = 0;
    private static final int OPENED = 1;
    private static final int BOTTOM_CONNECTED = 3;
    private static final int TOP_CONNECTED = 5;
    private static final int TOP_AND_BOTTOM_CONNECTED = 7;
    private int gridSize;
    private int openedSitesCount;
    private int[] siteStatus;
    private WeightedQuickUnionUF qu;
    private boolean percolates;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        percolates = false;
        gridSize = n;
        openedSitesCount = 0;
        siteStatus = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            siteStatus[i] = CLOSED;
        }
        qu = new WeightedQuickUnionUF(n * n);
    }

    // maps 2D coordinates into 1D array index
    private int getIndex(int row, int col) {
        row--;
        col--;
        return (row * gridSize) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndicesOutsideBounds(row, col);
        if (!isOpen(row, col)) {
            openSite(row, col);
            connectSiteToOpenedNeighbors(row, col);
        }
    }

    // connects the site to all the opened neighbors
    private void connectSiteToOpenedNeighbors(int row, int col) {
        int site = getIndex(row, col);
        int neighbor;

        if ((row - 1 >= 1) && isOpen(row - 1, col)) {
            neighbor = getIndex(row - 1, col);
            connectSiteToNeighbor(site, neighbor);
        }
        if ((row + 1 <= gridSize) && isOpen(row + 1, col)) {
            neighbor = getIndex(row + 1, col);
            connectSiteToNeighbor(site, neighbor);
        }
        if ((col - 1 >= 1) && isOpen(row, col - 1)) {
            neighbor = getIndex(row, col - 1);
            connectSiteToNeighbor(site, neighbor);
        }
        if ((col + 1 <= gridSize) && isOpen(row, col + 1)) {
            neighbor = getIndex(row, col + 1);
            connectSiteToNeighbor(site, neighbor);
        }

    }

    // connects the site to one neighbor and updates its status
    private void connectSiteToNeighbor(int site, int neighbor) {
        int neighborRoot = qu.find(neighbor);
        int aggregatedStatus = siteStatus[site] | siteStatus[neighborRoot];
        siteStatus[site] = aggregatedStatus;
        siteStatus[neighborRoot] = aggregatedStatus;
        qu.union(site, neighbor);
        siteStatus[qu.find(site)] = aggregatedStatus;
        if (aggregatedStatus == TOP_AND_BOTTOM_CONNECTED) {
            percolates = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndicesOutsideBounds(row, col);
        int i = getIndex(row, col);
        if (siteStatus[i] > CLOSED) {
            return true;
        }
        return false;
    }

    // is the site (row, col) open?
    private void openSite(int row, int col) {
        int i = getIndex(row, col);

        if (row == 1 && row == gridSize) {
            siteStatus[i] = TOP_AND_BOTTOM_CONNECTED;
            percolates = true;
        }
        else if (row == 1) {
            siteStatus[i] = TOP_CONNECTED;
        }
        else if (row == gridSize) {
            siteStatus[i] = BOTTOM_CONNECTED;
        }
        else {
            siteStatus[i] = OPENED;
        }

        openedSitesCount++;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndicesOutsideBounds(row, col);
        int p = getIndex(row, col);
        int root = qu.find(p);
        if (siteStatus[root] >= TOP_CONNECTED) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    // checks if the indices are inside the grid
    private void checkIndicesOutsideBounds(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException();
        }
    }

}
