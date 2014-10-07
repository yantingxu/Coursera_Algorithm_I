import java.util.Arrays;

public class Fast {

    private static void output(Point[] aux, int i, int j, Point current) {
        int len = j - i + 1;
        Point[] ps = new Point[len];
        for (int k = 0; k < len; k++) {
            ps[k] = aux[i+k];
        }
        Arrays.sort(ps);
        if (current.compareTo(ps[0]) > 0)
            return;
        current.drawTo(ps[len-1]);
        System.out.print(current + " -> ");
        for (int k = 0; k < len; k++) {
            if (k < len - 1) {
                System.out.print(ps[k] + " -> ");
            } else {
                System.out.println(ps[k]);
            }
        }
    }

    private static void search(Point[] points, int N) {
        Point[] aux = new Point[N];
        for (int i = 0; i < N; i++) {
            aux[i] = points[i];
        }
        for (int i = 0; i < N; i++) {
            Point current = points[i];
            Arrays.sort(aux, current.SLOPE_ORDER);
            int j = 1, k = 1;
            while (k < N) {
                boolean cmp = current.slopeTo(aux[j]) == current.slopeTo(aux[k]);
                if (cmp == true) {
                    k++;
                } else {
                    if (k-1-j+1 >= 3) {
                        output(aux, j, k-1, current);
                    }
                    j = k;
                }
            }
            if (j <= N-3) {
                output(aux, j, N-1, current);
            }
        }
    }

    public static void main(String[] args)
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        search(points, N);
    }
}
