import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int capacity;
    private int top;
    private Item[] queue;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        capacity = 1;
        queue = (Item[])new Object[capacity];
        top = 0;
    }

    public boolean isEmpty()                 // is the queue empty?
    {
        return top == 0;
    }

    public int size()                        // return the number of items on the queue
    {
        return top;
    }

    private void resize(int newCapacity)
    {
        Item[] newQueue = (Item[])new Object[newCapacity];
        for (int i = 0; i < top; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
        capacity = newCapacity;
    }

    public void enqueue(Item item)           // add the item
    {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size() == capacity) {
            resize(2*capacity);
        }
        queue[top++] = item;
        random_switch();
    }

    private void random_switch()
    {
        if (size() <= 1)
            return;
        int randomIdx = StdRandom.uniform(top);
        Item tmp = queue[randomIdx];
        queue[randomIdx] = queue[top-1];
        queue[top-1] = tmp;
    }

    public Item dequeue()                    // delete and return a random item
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item deleted = queue[--top];
        queue[top] = null;
        if (size() > 0 && size() <= capacity/4.0) {
            resize(capacity/2);
        }
        return deleted;
    }

    public Item sample()                     // return (but do not delete) a random item
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIdx = StdRandom.uniform(top);
        return queue[randomIdx];
    }

    private class RandomIterator implements Iterator<Item> {
        private int current;
        private int queue_size;
        private int[] indices;
        public RandomIterator()
        {
            current = 0;
            queue_size = top;
            if (queue_size > 0) {
                indices = new int[queue_size];
                for (int i = 0; i < indices.length; i++) {
                    indices[i] = i;
                }
                for (int i = 0; i < indices.length; i++) {
                    int randomIdx = StdRandom.uniform(i+1);
                    int tmp = indices[randomIdx];
                    indices[randomIdx] = indices[i];
                    indices[i] = tmp;
                }
            }
        }
        public boolean hasNext()
        {
            return current < queue_size;
        }
        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            return queue[indices[current++]];
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomIterator();
    }

    /*
    public void print_queue()
    {
        Iterator<Item> i = iterator();
        while (i.hasNext()) {
            System.out.print(i.next() + " ");
        }
        System.out.println();
    }
    */


    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        q.enqueue(3);
        q.enqueue(5);
        q.enqueue(10);
        // q.print_queue();
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
    }
}


