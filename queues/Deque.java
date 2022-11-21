/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node prev;

        public Node(Item item) {
            this.item = item;
            next = null;
            prev = null;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("there are no more items to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "remove() method should not be called on Iterator");
        }
    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null argument");
        }
        Node tmp = first;
        first = new Node(item);
        first.next = tmp;
        if (tmp != null) {
            tmp.prev = first;
        }
        if (last == null) {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null argument");
        }
        Node tmp = last;
        last = new Node(item);
        last.prev = tmp;
        if (tmp != null) {
            tmp.next = last;
        }
        if (first == null) {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot remove from empty Deque");
        }
        Node tmp = first;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        else {
            last = null;
        }

        size--;
        return tmp.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot remove from empty Deque");
        }
        Node tmp = last;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        }
        size--;
        return tmp.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            dq.addFirst(i);
        }
        for (int i = 0; i < n; i++) {
            StdOut.printf("%d,", dq.removeLast());
        }
        StdOut.println("\n------\n");
        for (int i = 0; i < n; i++) {
            dq.addLast(i);
        }
        for (int i = 0; i < n; i++) {
            StdOut.printf("%d,", dq.removeFirst());
        }

        StdOut.println("\n------\n");
        for (int i = 0; i < 10; i++) {
            dq.addLast(i);
        }
        for (int i : dq) {
            StdOut.println(i);
            for (int j : dq) {
                StdOut.printf("%d,", j);
            }
            StdOut.printf("\n");
        }

        Deque<String> dq2 = new Deque<String>();
        dq2.addFirst("Rodrigo");
        dq2.addFirst("Olá");
        dq2.addLast("Como vai?");
        StdOut.println(dq2.removeFirst());
        StdOut.println(dq2.removeFirst());
        StdOut.println(dq2.removeLast());

        dq2.addLast("lala");
        dq2.removeFirst();
        dq2.addLast("lala");
        dq2.removeLast();

        dq2.addLast("lala");
        dq2.addLast("lele");
        dq2.removeFirst();
        dq2.addLast("lala");
        dq2.addLast("lele");
        dq2.removeLast();
        dq2.removeFirst();
        dq2.removeLast();

    }

}
