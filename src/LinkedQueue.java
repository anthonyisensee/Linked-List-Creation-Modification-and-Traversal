/*************************************************************************
 *  Compilation:  javac LinkedQueue.java
 *  Execution:    java LinkedQueue < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt  
 *
 *  A generic queue, implemented using a singly-linked list.
 *
 *  % java Queue < tobe.txt 
 *  to be or not to be (2 left on queue)
 *
 *************************************************************************/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The <tt>LinkedQueue</tt> class represents a first-in-first-out (FIFO)
 *  queue of generic items.
 *  It supports the usual <em>enqueue</em> and <em>dequeue</em>
 *  operations, along with methods for peeking at the first item,
 *  testing if the queue is empty, and iterating through
 *  the items in FIFO order.
 *  <p>
 *  This implementation uses a singly-linked list with a non-static nested class 
 *  for linked-list nodes.  See for a version that uses a static nested class.
 *  The <em>enqueue</em>, <em>dequeue</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @author Anthony Isensee (changes to source code)
 */
public class LinkedQueue<Item> implements Iterable<Item> {
    private int N;         // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    /**
     * Initializes an empty queue.
     */
    public LinkedQueue() {
        first = null;
        last  = null;
        N = 0;
        assert check();
    }

    /**
     * Is this queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     * @return the number of items in this queue
     */
    public int size() {
        return N;     
    }

    /**
     * Returns the item least recently added to this queue.
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    /**
     * Adds the item to this queue.
     * @param item the item to add
     */
    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else           oldlast.next = last;
        N++;
        assert check();
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;   // to avoid loitering
        assert check();
        return item;
    }

    /**
     * Returns a string representation of this queue.
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    } 

    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null) return false;
            if (last  != null) return false;
        }
        else if (N == 1) {
            if (first == null || last == null) return false;
            if (first != last)                 return false;
            if (first.next != null)            return false;
        }
        else {
            if (first == last)      return false;
            if (first.next == null) return false;
            if (last.next  != null) return false;

            // check internal consistency of instance variable N
            int numberOfNodes = 0;
            for (Node x = first; x != null; x = x.next) {
               numberOfNodes++;
            }
            if (numberOfNodes != N) return false;

            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
               lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
        }

        return true;
    } 
 

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }


    /**
     * Unit tests the <tt>LinkedQueue</tt> data type.
     */
    public static void main(String[] args) {

        // new queues
        LinkedQueue<String> q = new LinkedQueue<String>();

        // add items to queue
        String[] newItems = { "a", "a", "b", "c", "a", "d", "b", "abba", "a", "z", "a" };
        for (String i : newItems) {
            q.enqueue(i);
        }



        // perform assigned activities
        StdOut.println(q.toString());   // display queue using stack
        q.reverseByStack();             // reverse queue
        StdOut.println(q.toString());   // display queue using stack
        q.remove("a");             // remove 'a' from queue
        q.remove("f");             // remove 'f' from queue
        q.remove("c");             // remove 'c' from queue
        StdOut.println(q.toString());   // display queue using stack
        q.reverseByLinks();             // reverse queue using links
        StdOut.println(q.toString());   // display queue



        /* Additional Testing

        // create queues for rigorous testing
        LinkedQueue<String> test1 = new LinkedQueue<String>();
        LinkedQueue<String> test2 = new LinkedQueue<String>();

        // add items to test1 queue
        String[] testItems1 = { "1", "2", "3", "4", "5", "6", "7", "8" };
        for (String i : testItems1) {
            test1.enqueue(i);
        }

        // add items to test2 queue
        String[] testItems2 = { "a", "first", "a", "a", "last", "a", "a" };
        for (String i : testItems2) {
            test2.enqueue(i);
        }

        // output results to preview proper behavior //

        // test 1
        StdOut.println("");   // whitespace

        // before modification preview
        StdOut.println(test1.toString());   // first sample
        StdOut.println("First: " + test1.first.item.toString());    // make sure first is set correctly
        StdOut.println("Last: " + test1.last.item.toString());      // make sure last is set correctly

        // modification
        test1.remove("8");  // testing removal
        test1.reverseByStack();

        // after modification preview
        StdOut.println(test1.toString());   // second sample
        if (test1.first != null) { StdOut.println("First: " + test1.first.item.toString()); } else { StdOut.println("First: null"); }  // make sure first is set correctly
        if (test1.last != null) { StdOut.println("Last: " + test1.last.item.toString()); } else { StdOut.println("Last: null"); }      // make sure last is set correctly

        // test 2
        StdOut.println("");   // whitespace

        // before modification preview
        StdOut.println(test2.toString());   // first sample
        StdOut.println("First: " + test2.first.item.toString());    // make sure first is set correctly
        StdOut.println("Last: " + test2.last.item.toString());      // make sure last is set correctly

        // modification
        test2.remove("a");  // testing removal

        // after modification preview
        StdOut.println(test2.toString());   // second sample
        if (test2.first != null) { StdOut.println("First: " + test2.first.item.toString()); } else { StdOut.println("First: null"); }  // make sure first is set correctly
        if (test2.last != null) { StdOut.println("Last: " + test2.last.item.toString()); } else { StdOut.println("Last: null"); }      // make sure last is set correctly

        */

    }

    /**
     * This method reverses the order of the items in the linked list
     * (1st becomes last and last becomes 1st) using a stack data structure.
     */
    public void reverseByStack() {

        // create new stack
        Stack<Item> s = new Stack<Item>();

        // push all items in queue to stack
        while(!isEmpty()) {
            s.push(this.dequeue());
        }

        // enqueue all items from stack
        while(!s.isEmpty()) {
            this.enqueue(s.pop());
        }
    }

    /**
     * This method reverses the order of the items in the linked list
     * (1st becomes last and last becomes 1st) using only the links in
     * the original queue data structure.
     */
    public void reverseByLinks() {

        // initializes variables needed to traverse and reverse the linked list
        Node previous = null;   // null on left side
        Node current = first;   // begin on the dequeue side
        Node next = null;       // null on right side
        //current = last;         // sets the last node to our current first

        // iterates over our linked list
        while(current != null) {

            next = current.next;    // tracks our next, we can now detach current node without losing our place in the list
            current.next = previous;    // on first iteration, will be null. On all others, will be previous node
            previous = current;     // sets us up for our next iteration by making our new previous our current working node
            current = next;         // sets the node we're looking at to the next node in the list so that we can continue.

        }

        // sets our new first as the previous node in the list, allowing our data structure to be complete.
        first = previous;
    }

    /**
     * This method scans the queue for occurrences of the given item and removes
     * them from the queue. It returns the number of items deleted from the queue.
     */
    int remove(Item remove) {

        int itemsDeleted = 0;
        Node previous = null;
        Node current = first;

        // null list case, there are no items in list
        if(isEmpty()) { return itemsDeleted; }

        // front of list case, there are multiple items to be deleted at the front of the list
        while(first.item == remove) {

            // whole list deletion case
            if(first == last) {
                last = null;
                first = null;
                return ++itemsDeleted;
            }

            // advance our head (effectively deleting a node from master list)
            first = first.next;
            // add 1 to our number of items deleted
            itemsDeleted++;
        }

        // set our values for the rest of cases
        // note that current still refers to our head at this point
        previous = current;
        current = current.next;

        // middle/end of list case
        while(current != null)  {

            // if item is detected
            if(current.item == remove && current.next != null) {

                // splice linked list around detected item to remove and set other values to correct places
                previous.next = current.next;   // splice linked list around detected item
                current = current.next;         // move current forward (to the node we just pointed the previous node towards)
                itemsDeleted++;

            }
            // end of list case (current.next == null is redundant, but shown for clarity)
            else if (current.item == remove && current.next == null){

                // splice linked list around detected item to remove and set other values to correct places
                previous.next = null;   // unlink the final element from the previous element
                // set last to the previous item in the structure
                last = previous;
                return ++itemsDeleted;

            }
            else {

                // only iterates previous when an item has not been detected, or previous will catch up with current
                previous = previous.next;   // note that this must advance from itself, not current, or previous will become the old node
                current = current.next;     // the current node is advanced by one for our next loop

            }

        }

        // returns number of items deleted
        return itemsDeleted;

    }
}
