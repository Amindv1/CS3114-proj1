/**
 * // -------------------------------------------------------------------------
 * /** This is the linked list used to store the free blocks in the memory
 *
 * @author Amin Davoodi (amind1)
 * @version Sep 4, 2014
 */
public class DoublyLinkedList
{
    private Node head;
    private Node tail;
    private Node current;
    private int  size;


    /**
     * Construct the stack.
     */
    public DoublyLinkedList()
    {
        head = new Node();
        tail = new Node();
        current = new Node();
        size = 0;

        head.join(tail);
    }


    /**
     * returns the data at the current location, throws an exception if the list
     * is empty.
     *
     * @return the data of the current node
     */
    public int getCurrentLength()
    {
        return current.getLength();

    }


    /**
     * returns the data at the current location, throws an exception if the list
     * is empty.
     *
     * @return the data of the current node
     */
    public int getCurrentPosition()
    {
        return current.getPosition();

    }


    /**
     * sets the length of the current node. throws an exception if the list is
     * empty.
     *
     * @param length
     *            the length to set it to
     */
    public void setCurrentLength(int length)
    {
        current.setLength(length);

    }


    /**
     * sets the position of the current node. throws an exception if the list is
     * empty.
     *
     * @param position
     *            the position to set it to
     */
    public void setCurrentPosition(int position)
    {
        current.setPosition(position);

    }


    /**
     * moves to the beginning of the linked list
     */
    public void moveToFront()
    {
        if (size != 0)
        {
            current = head.next();
        }
    }


    /**
     * moves to the next node in the list
     */
    public void next()
    {
        if (current.next() != tail)
        {
            current = current.next();
        }

    }


    /**
     * moves to the previous node in the list
     */
    public void previous()
    {
        if (current.previous() != head)
        {
            current = current.previous();
        }
    }


    /**
     * adds an item right before the current item and sets the current item
     * equal to the item added.
     *
     * @param position
     *            the position of the new node
     * @param length
     *            the length of the new node
     */
    public void add(int position, int length)
    {

        Node node = new Node(position, length);
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

        // this loop makes sure that everything that is added to the
        // doubly linked list is sorted by position
        moveToFront();
        while ((current.getPosition()) < position)
        {

            current = current.next();

            if (current.next() == null)
            {
                break;
            }
        }

        Node previous = current.previous();
        previous.split();
        node.join(current);
        previous.join(node);
        current = node;

        size++;
    }


    /**
     * gets the next node
     *
     * @return the next node
     */
    public Node getNext()
    {

        if (current.next() != tail)
        {
            return current.next();
        }

        return null;
    }


    /**
     * move to the given position in the list
     *
     * @param position
     *            moves to this position
     * @return true if the node with the corresponding position exists false
     *         otherwise
     */
    public boolean moveToPosition(int position)
    {

        moveToFront();

        while (position > current.getPosition())
        {

            current = current.next();

            if (current == tail)
            {
                return false;
            }
        }
        return (position == current.getPosition());
    }


    /**
     * removes the current node and sets the node before it to the new current
     * node. If the list is empty it throws an exception.
     */
    public void removeCurrent()
    {

        if (size != 0)
        {

            Node previous = current.previous();
            Node next = current.next();
            current.split();
            previous.split();
            previous.join(next);
            current = previous;

            size--;
        }
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
     * Empty the list
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
}

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

