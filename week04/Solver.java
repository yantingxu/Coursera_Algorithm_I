public class Solver {
    private Node goal;
    private int steps;
    private Stack<Board> sequence;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        Node initNode = makeSearchNode(initial, null);
        MinPQ<Node> queue = new MinPQ<Node>(1);
        queue.insert(initNode);

        Node initTwinNode = makeSearchNode(initial.twin(), null);
        MinPQ<Node> twinQueue = new MinPQ<Node>(1);
        twinQueue.insert(initTwinNode);

        while (true) {
            if (!queue.isEmpty()) {
                Node ret = check(queue);
                if (ret != null) {
                    goal = ret;
                    break;
                }
            } else {
                goal = null;
                break;
            }
            if (!twinQueue.isEmpty()) {
                Node ret = check(twinQueue);
                if (ret != null) {
                    goal = null;
                    break;
                }
            }
            // System.out.println("Queue Size: " + queue.size());
            // System.out.println("TwinQueue Size: " + twinQueue.size());
        }

        reconstruct_solution();
    }

    private void reconstruct_solution()
    {
        if (goal == null) {
            steps = -1;
            sequence = null;
        } else {
            sequence = new Stack<Board>();
            Node currentNode = goal;
            while (currentNode != null) {
                sequence.push(currentNode.board);
                currentNode = currentNode.prev_node;
            }
            steps = sequence.size()-1;
        }
    }

    private Node check(MinPQ<Node> queue)
    {
        Node node = queue.delMin();
        if (node.board.isGoal()) {
            return node;
        } else {
            for (Board board: node.board.neighbors()) {
                if (node.prev_node == null || !exists(board, node.prev_node)) {
                    queue.insert(makeSearchNode(board, node));
                }
            }
            return null;
        }
    }

    private boolean exists(Board candidate, Node current)
    {
        while (current != null) {
            if (current.board.equals(candidate))
                return true;
            current = current.prev_node;
        }
        return false;
    }


    private Node makeSearchNode(Board board, Node prev_node)
    {
        Node node = new Node();
        node.board = board;
        node.prev_node = prev_node;
        if (prev_node == null)
            node.moves = 0;
        else
            node.moves = prev_node.moves + 1;
        return node;
    }
    private class Node implements Comparable<Node> {
        private Board board;
        private Node prev_node;
        private int moves;
        public int compareTo(Node that)
        {
            int this_priority = this.board.manhattan() + this.moves;
            int that_priority = that.board.manhattan() + that.moves;
            if (this_priority == that_priority)
                return 0;
            else if (this_priority < that_priority)
                return -1;
            else
                return 1;
        }
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return goal != null;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return steps;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        return sequence;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
