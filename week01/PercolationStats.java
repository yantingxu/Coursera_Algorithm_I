public class PercolationStats {
    private int len;
    private int times;
    private double[] probs;

    public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
        len = N;
        times = T;
        probs = new double[times];

        int grid_num = N*N;
        Percolation p;
        for (int iter = 0; iter < T; iter++) {
            p = new Percolation(N);
            int i, j;
            double count = 0.0;
            while (!p.percolates()) {
                do {
                    i = StdRandom.uniform(1, N+1);
                    j = StdRandom.uniform(1, N+1);
                } while (p.isOpen(i, j));
                p.open(i, j);
                count++;
                // System.out.println("Open: " + i + ", " + j);
                // p.print_matrix();
            }
            probs[iter] = count/grid_num;
        }
    }

    public double mean()                     // sample mean of percolation threshold
    {
        return StdStats.mean(probs);
    }

    public double stddev()                   // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(probs);
    }

    public double confidenceLo()             // returns lower bound of the 95% confidence interval
    {
        return mean() - 1.96*stddev()/Math.sqrt(times);
    }

    public double confidenceHi()             // returns upper bound of the 95% confidence interval
    {
        return mean() + 1.96*stddev()/Math.sqrt(times);
    }

    public static void main(String[] args)   // test client, described below
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        System.out.println("mean\t=" + ps.mean());
        System.out.println("stddev\t=" + ps.stddev());
        System.out.println("95% confidence interval\t=" + ps.confidenceLo() + "," + ps.confidenceHi());
    }
}




