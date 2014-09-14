package datastructures;

import java.util.NoSuchElementException;


/**
 * // -------------------------------------------------------------------------
/**
 *  This is the linked list used to store the free blocks in the memory
 *  @param <T>
 *
 *  @author Amin Davoodi (amind1)
 *  @version Sep 4, 2014
 */
public class DoublyLinkedList<T>
{
    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;
    private int     size;

    /**
     * Construct the stack.
     */
    public DoublyLinkedList()
    {
        head = new Node<T>(null, null);
        tail = new Node<T>(null, null);
        current = new Node<T>(null, null);
        size = 0;

        head.join(tail);
    }

    /**
     * returns the data at the current location, throws an exception if the node
     * is null.
     *
     * @return the data of the current node
     */
    public T getCurrentLength()
    {
        if (size != 0)
        {
            return current.getLength();
        }
        else
        {
            throw new NoSuchElementException("The list is empty");
        }
    }

    /**
     * returns the data at the current location, throws an exception if the node
     * is null.
     *
     * @return the data of the current node
     */
    public T getCurrentPosition()
    {
        if (size != 0)
        {
            return current.getPosition();
        }
        else
        {
            throw new NoSuchElementException("The list is empty");
        }
    }

    public void setCurrentLength(T length) {
        current.setLength(length);
    }

    public void setCurrentPosition(T position) {
        current.setPosition(position);
    }

    /**
     * moves to the beginning of the linked list
     */
    public void moveToFront() {
        if (size != 0) {
            current = head.next();
        }
    }

    /**
     * moves to the next node in the list
     */
    public Node next() {
        if (current.next() != tail) {
            current = current.next();
            return current;
        }

        return null;
    }

    /**
     * moves to the previous node in the list
     */
    public Node previous() {
        if (current.previous() != head) {
            current = current.previous();
            return current;
        }
        return null;
    }


    /**
     * adds an item right before the current item and sets the current item
     * equal to the item added.
     *
     * @param item
     *            the data being added to the list
     */
    public void add(T position, T length)
    {

        Node<T> node = new Node<T>(position, length);
        // if it is the first item in the list, it sets the current item to the
        // new item
        if (size == 0)
        {
            current = node;
            current.join(tail);
            head.join(current);
            size++;
            return;
        }

        //this loop makes sure that everything that is added to the
        //doubly linked list is sorted by position
        moveToFront();
        if(current == null) System.out.println("curr null");
        if(current != null) System.out.println("curr not null");

        while( current != null && (int)(current.getPosition()) < (int)position) {

            current = current.next();

            if (current.next() == null) {
                break;
            }
        }

        Node<T> previous = current.previous();
        previous.split();
        node.join(current);
        previous.join(node);
        current = node;

        size++;
    }

    public Node getNext() {

        if (current.next() != null) {
            return current.next();
        }

        return null;
    }

    /**
     *
     */
    public boolean moveToPosition(T position) {

        if (position == null) {
            return false;
        }

        moveToFront();

        while((int) position > (int)current.getPosition()) {

                current = current.next();

                if(current == tail) {
                    return false;
                }
        }

        if (position == current.getPosition()) {
            return true;
        }

        return false;
    }


    /**
     * removes the current node and sets the node before it to the new current
     * node. If the list is empty it throws an exception.
     *
     * @return returns the data of the node removed.
     */
    public Node removeCurrent()
    {
        if (size == 0)
        {
            throw new NoSuchElementException(
                "The list is empty, there is nothing to remove.");
        }

        Node<T> previous = current.previous();
        Node<T> next = current.next();
        Node<T> oldCurrent = current;
        current.split();
        previous.split();
        previous.join(next);
        current = next;

        size--;
        return oldCurrent;
    }

    /**
     * Insert a new item at the rear (the tail) of the deque.
     *
     * @param value
     *            the item to insert.
     * @postcondition [new-contents] == [old-contents] * [value]
     */
    public void addAtRear(T position, T length)
    {
        Node<T> node = new Node<T>(position, length);
        Node<T> newNode = tail.previous();
        newNode.split();
        newNode.join(node);
        node.join(tail);

        current = node;
        size++;
    }

    /**
     * Remove the item at the rear (the tail) of the deque.
     *
     * @return The item that was removed
     * @precondition |[old-contents]| > 0
     * @postcondition [old-contents] = [new-contents] * [result]
     */
    public Node removeAtRear()
    {
        Node<T> node = (tail.previous().previous());
        Node<T> newNode = node.split();
        newNode.split();
        node.join(tail);
        size--;
        return newNode;
    }

    /**
     * Insert a new item at the front (the head) of the deque.
     *
     * @param value
     *            the item to insert.
     * @postcondition [new-contents] = [value] * [old-contents]
     */
    public void addAtFront(T position, T length)
    {
        Node<T> node = new Node<T>(position, length);
        node.join(head.split());
        head.join(node);

        current = node;
        size++;
    }

    /**
     * Remove the item at the front (the head) of the deque.
     *
     * @return The item that was removed
     * @precondition |[old-contents]| > 0
     * @postcondition [old-contents] == [result] * [new-contents]
     */
    public Node removeAtFront()
    {
        Node<T> node = head.split();
        Node<T> newNode = node.next();
        node.split();
        head.join(newNode);
        size--;
        return node;
    }

    /**
     * Get the number of items in this deque. Does not alter the deque.
     *
     * @return The number of items this deque contains.
     * @postcondition result = |[contents]|
     */
    public int size()
    {
        return size;
    }

    /**
     * Empty the deque.
     *
     * @postcondition [new-contents] = []
     */
    public void clear()
    {
        tail.previous().split();
        head.split();
        head.join(tail);
        size = 0;
    }

    /**
     * Returns a string representation of this deque. A deque's string
     * representation is written as a comma-separated list of its contents (in
     * front-to-rear order) surrounded by square brackets, like this:
     *
     * <pre>
     * [52, 14, 12, 119, 73, 80, 35]
     * </pre>
     * <p>
     * An empty deque is simply <code>[]</code>.
     * </p>
     *
     * @return a string representation of the deque
     */
    public String toString()
    {
        Node<T> temp = head.next();
        String str = "[";
        for (int i = 0; i < size; i++)
        {
            str += (temp.getPosition() + ", ");
            temp = temp.next();
        }
        if (size == 0)
        {
            return "[]";
        }
        return str.substring(0, str.length() - 2) + "]";
    }
}

//On my honor:
//
//- I have not used source code obtained from another student,
//or any other unauthorized source, either modified or
//unmodified.
//
//- All source code and documentation used in my program is
//either my original work, or was derived by me from the
//source code published in the textbook for this course.
//
//- I have not discussed coding details about this project with
//anyone other than my partner (in the case of a joint
//submission), instructor, ACM/UPE tutors or the TAs assigned
//to this course. I understand that I may discuss the concepts
//of this program with other students, and that another student
//may help me debug my program so long as neither of us writes
//anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor
//letter of this restriction.

