
public class Subset {
    public static void main(String[] args)
    {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            q.enqueue(s);
        }
        if (k > 0) {
            int counter = 0;
            for (String t: q) {
                StdOut.println(t);
                counter++;
                if (counter == k)
                    break;
            }
        }
    }
}
