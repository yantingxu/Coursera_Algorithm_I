import java.util.Arrays;

public class Brute {
    private static void search(Point[] points, int N) {
        for (int p = 0; p < N; p++) {
            for (int q = p + 1; q < N; q++) {
                for (int r = q + 1; r < N; r++) {
                    for (int s = r + 1; s < N; s++) {
                        double slope_q = points[p].slopeTo(points[q]);
                        double slope_r = points[p].slopeTo(points[r]);
                        double slope_s = points[p].slopeTo(points[s]);
                        if (slope_q == slope_r && slope_q == slope_s) {
                            // System.out.println("Check: " + p + '|' + q + '|' + r + '|' + s);
                            // System.out.println("Slope: " + slope_q + '|' + slope_r + '|' + slope_s);
                            Point[] segment = new Point[]{points[p], points[q], points[r], points[s]};
                            Arrays.sort(segment);
                            segment[0].drawTo(segment[3]);

                            for (int i = 0; i < segment.length; i++) {
                                if (i < segment.length-1)
                                    System.out.print(segment[i] + " -> ");
                                else
                                    System.out.println(segment[i]);
                            }
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
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



