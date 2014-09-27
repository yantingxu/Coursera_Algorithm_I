public class Percolation {
    private int len;
    private WeightedQuickUnionUF uf;
    private int[] states;

    public Percolation(int N)                // create N-by-N grid, with all sites blocked
    {
        if (N <= 0)
            throw new IllegalArgumentException();

        len = N;
        int grid_num = N*N+2;
        uf = new WeightedQuickUnionUF(grid_num);
        states = new int[grid_num];
        for (int i = 0; i < grid_num; i++) {
            states[i] = 0;
        }
        states[0] = states[grid_num-1] = 1;

    }

    private int get_index(int i, int j)
    {
        return (i-1)*len + j;
    }

    private boolean in_bound(int i, int j)
    {
        return i >= 1 && i <= len && j >= 1 && j <= len;
    }

    public void open(int i, int j)           // open site (row i, column j) if it is not already
    {
        if (!in_bound(i, j))
            throw new IndexOutOfBoundsException();

        int current_index = get_index(i, j);
        states[current_index] = 1;

        // PRE ROW
        if (i > 1) {
            if (isOpen(i-1, j)) {
                int top_index = get_index(i-1, j);
                uf.union(top_index, current_index);
            }
        } else {
            uf.union(0, current_index);
        }

        // NEXT ROW
        if (i < len) {
            if (isOpen(i+1, j)) {
                int bottom_index = get_index(i+1, j);
                uf.union(bottom_index, current_index);
            }
        } else {
            uf.union(len*len+1, current_index);
        }

        // PRE COL
        if (j > 1) {
            if (isOpen(i, j-1)) {
                int left_index = get_index(i, j-1);
                uf.union(left_index, current_index);
            }
        }

        // NEXT COL
        if (j < len) {
            if (isOpen(i, j+1)) {
                int right_index = get_index(i, j+1);
                uf.union(right_index, current_index);
            }
        }
    }

    public boolean isOpen(int i, int j)      // is site (row i, column j) open?
    {
        if (!in_bound(i, j))
            throw new IndexOutOfBoundsException();
        int idx = get_index(i, j);
        return states[idx] == 1;
    }

    public boolean isFull(int i, int j)      // is site (row i, column j) full?
    {
        if (!in_bound(i, j))
            throw new IndexOutOfBoundsException();
        int idx = get_index(i, j);
        return uf.connected(0, idx);
    }

    public boolean percolates()              // does the system percolate?
    {
        return uf.connected(0, len*len+1);
    }

    private void print_matrix()
    {
        System.out.println(states.length);
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= len; j++) {
                int idx = get_index(i, j);
                System.out.print(states[idx] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args)   // test client, optional
    {
        // int n = StdRandom.uniform(1, 10);
        int N = 5;
        Percolation p = new Percolation(N);
        p.print_matrix();

        p.open(1, 1);
        p.open(1, 2);
        p.open(2, 2);
        p.open(2, 3);
        p.open(2, 4);
        p.print_matrix();
        System.out.println(p.percolates());

        p.open(2, 5);
        p.open(3, 5);
        p.open(4, 5);
        p.open(5, 5);
        p.print_matrix();
        System.out.println(p.percolates());
    }
}






