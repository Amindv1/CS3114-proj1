/**
 * A "node" represents a single element in a sequence. Think of it like a link
 * in a chain -- the node contains an element of data and a reference to the
 * next element in the sequence. This node is "doubly linked" -- it has
 * references to the next node in the chain and the previous node in the chain.
 * The join and split methods manage both connections simultaneously to ensure
 * that the consistency of links in the chain is preserved at all times.
 *
 * @author Amin Davoodi
 * @version 2012.10.22
 */
public class Node
{
    // ~ Fields ................................................................

    // The data element stored in the node.
    private int  length;
    private int  position;
    // The next node in the sequence.
    private Node next;

    // The previous node in the sequence.
    private Node previous;


    // ~ Constructors ..........................................................

    // ----------------------------------------------------------

    // ----------------------------------------------------------
    /**
     * Create a new Node object.
     */
    public Node()
    {
        // generic node constructor for head and tail
    }


    /**
     * Creates a new Node with the specified data.
     *
     * @param length
     *            the data for the node
     * @param position
     *            the position of the node
     */
    public Node(int position, int length)
    {
        this.length = length;
        this.position = position;
    }


    /**
     *
     */
    public int getPosition()
    {
        return position;
    }


    /**
     *
     */
    public void setPosition(int position)
    {
        this.position = position;
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Gets the data in the node.
     *
     * @return the data in the node
     */
    public int getLength()
    {
        return length;
    }


    // ----------------------------------------------------------
    /**
     * Sets the data in the node.
     *
     * @param newData
     *            the new data to put in the node
     */
    public void setLength(int newData)
    {
        length = newData;
    }


    // ----------------------------------------------------------
    /**
     * Gets the node that follows this one in the sequence.
     *
     * @return the node that follows this one in the sequence
     */
    public Node next()
    {
        return next;
    }


    // ----------------------------------------------------------
    /**
     * Gets the node that precedes this one in the sequence.
     *
     * @return the node that precedes this one in the sequence
     */
    public Node previous()
    {
        return previous;
    }


    // ----------------------------------------------------------
    /**
     * Joins this node to the specified node so that the one passed as a
     * parameter follows this node. In other words, writing {@code A.join(B)}
     * would create the linkage A <-> B. An exception will be thrown if this
     * node already has another node following it, or if {@code newNext} already
     * has another node preceding it. In this case, you must {@link #split()}
     * the nodes before you can join it to something else.
     *
     * @param newNext
     *            the node that should follow this one
     * @return this node, so that you can chain methods like
     *         {@code A.join(B.join(C))}
     * @throws IllegalStateException
     *             if there is already a node following this node or if there is
     *             already a node preceding {@code newNext}
     */
    public Node join(Node newNext)
    {

        if (newNext != null)
        {
            this.next = newNext;
            newNext.previous = this;
        }

        return this;

    }


    // ----------------------------------------------------------
    /**
     * Splits this node from its follower and then returns the follower.
     *
     * @return the node that used to follow this node before they were split
     */
    public Node split()
    {
        Node oldNext = this.next;

        if (next != null)
        {
            next.previous = null;
        }

        this.next = null;
        return oldNext;
    }
}
