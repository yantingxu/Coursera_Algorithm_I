import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item value;
        Node prev;
        Node next;
    }
    private Node first, last;
    private int size;

    public Deque()                           // construct an empty deque
    {
        first = last = null;
        size = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return size == 0;
    }

    public int size()                        // return the number of items on the deque
    {
        return size;
    }

    private Node constructNode(Item item)
    {
        Node node = new Node();
        node.value = item;
        node.next = node.prev = null;
        return node;
    }

    public void addFirst(Item item)          // insert the item at the front
    {
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = constructNode(item);
        if (isEmpty()) {
            first = last = node;
        } else {
            node.next = first;
            first.prev = node;
            first = node;
            // System.out.println("First Node: " + first.value);
        }
        size++;
    }

    public void addLast(Item item)           // insert the item at the end
    {
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = constructNode(item);
        if (isEmpty()) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        size++;
    }

    public Item removeFirst()                // delete and return the item at the front
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node deleted = first;
        first = first.next;

        deleted.next = null;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        size--;
        return deleted.value;
    }

    public Item removeLast()                 // delete and return the item at the end
    {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node deleted = last;
        last = last.prev;

        deleted.prev = null;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;

        return deleted.value;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = null;
        public DequeIterator()
        {
            current = first;
        }
        public boolean hasNext()
        {
            return current != null;
        }
        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.value;
            current = current.next;
            return item;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
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
        Deque<Integer> q = new Deque<Integer>();
        q.addFirst(1);
        q.addFirst(3);
        q.addFirst(10);
        // q.print_queue();
        
        q.addLast(-1);
        q.addLast(-3);
        q.addLast(-10);
        // q.print_queue();

        q.removeFirst();
        q.removeFirst();
        q.removeFirst();
        q.removeFirst();
        // q.print_queue();

        q.removeLast();
        q.removeLast();
        // q.print_queue();

        q.removeLast();
    }
}
