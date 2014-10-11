public class Board {

    private class Index {
        private int row_idx;
        private int col_idx;
    }

    private int N;
    private int[][] blocks;
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
    {
        int N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
        this.N = N;
    }
    public int dimension()                 // board dimension N
    {
        return N;
    }
    private Index get_indices(int n)
    {
        Index p = new Index();
        int row_num = n / N + 1;
        if (n % N == 0)
            row_num--;
        p.row_idx = row_num - 1;

        int col_num = n % N;
        if (n % N == 0)
            col_num = N;
        p.col_idx = col_num - 1;

        return p;
    }
    private int get_num(int row_idx, int col_idx)
    {
        return row_idx*N + col_idx + 1;
    }
    public int hamming()                   // number of blocks out of place
    {
        int hamming = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] == 0)
                    continue;
                if (get_num(i, j) != this.blocks[i][j])
                    hamming++;
            }
        return hamming;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manhattan = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] == 0)
                    continue;
                Index p = get_indices(this.blocks[i][j]);
                manhattan += (Math.abs(p.row_idx-i) + Math.abs(p.col_idx-j));
            }
        return manhattan;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return hamming() == 0;
    }
    private void swap_col(int i, int j1, int j2)
    {
        int tmp = this.blocks[i][j1];
        this.blocks[i][j1] = this.blocks[i][j2];
        this.blocks[i][j2] = tmp;
    }
    public Board twin()                    // a boadr that is obtained by exchanging two adjacent blocks in the same row
    {
        Index blank_idx = search_blank_block();
        int row_idx = (blank_idx.row_idx + 1) % N;
        int first_col_idx = 0;
        int second_col_idx = 1;

        /*
        int row_idx = StdRandom.uniform(N);
        while (row_idx == blank_idx.row_idx)
            row_idx = StdRandom.uniform(N);

        int first_col_idx = StdRandom.uniform(N-1);
        int second_col_idx = first_col_idx + 1;
        */

        swap_col(row_idx, first_col_idx, second_col_idx);
        Board twin_board = new Board(this.blocks);
        swap_col(row_idx, first_col_idx, second_col_idx);
        return twin_board;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == null)
            return false;
        if (this == y)
            return true;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board)y;
        if (that.dimension() != this.dimension())
            return false;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (this.blocks[i][j] != that.blocks[i][j])
                    return false;
        return true;
    }
    private Index search_blank_block()
    {
        Index p = new Index();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (this.blocks[i][j] == 0) {
                    p.row_idx = i;
                    p.col_idx = j;
                    return p;
                }
        return null;
    }
    private void swap(int i1, int j1, int i2, int j2)
    {
        int tmp = this.blocks[i1][j1];
        this.blocks[i1][j1] = this.blocks[i2][j2];
        this.blocks[i2][j2] = tmp;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> stack = new Stack<Board>();

        // find the blank block
        Index blank_idx = search_blank_block();
        int blank_i = blank_idx.row_idx;
        int blank_j = blank_idx.col_idx;

        // swap with the left
        if (blank_j > 0) {
            swap(blank_i, blank_j, blank_i, blank_j-1);
            stack.push(new Board(this.blocks));
            swap(blank_i, blank_j, blank_i, blank_j-1);
        }
        // swap with the right
        if (blank_j < N-1) {
            swap(blank_i, blank_j, blank_i, blank_j+1);
            stack.push(new Board(this.blocks));
            swap(blank_i, blank_j, blank_i, blank_j+1);
        }
        // swap with the top
        if (blank_i > 0) {
            swap(blank_i, blank_j, blank_i-1, blank_j);
            stack.push(new Board(this.blocks));
            swap(blank_i, blank_j, blank_i-1, blank_j);
        }
        // swap with the bottom
        if (blank_i < N-1) {
            swap(blank_i, blank_j, blank_i+1, blank_j);
            stack.push(new Board(this.blocks));
            swap(blank_i, blank_j, blank_i+1, blank_j);
        }
        return stack;
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] blocks = {{8,1,3}, {4,0,2}, {7,6,5}};
        Board board = new Board(blocks);
        System.out.println(board.toString());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.twin());
        for (Board b: board.neighbors()) {
            System.out.println(b);
        }
    }
}



