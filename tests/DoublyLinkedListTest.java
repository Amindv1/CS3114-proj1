package tests;
import datastructures.DoublyLinkedList;
import student.TestCase;
/**
 * Tests for the {@link DoublyLinkedList} class.
 *
 * @author Stephen Edwards and Tony Allevatos
 * @author Mohammed Amin Davoodi (amind1)
 * @author Thomas Lazor (tjlazor)
 * @author Mary-Wynn Rogers (marywynn)
 * @version (2013.10.24)
 */
public class DoublyLinkedListTest
    extends TestCase
{
    // ~ Instance/static variables .............................................

    private DoublyLinkedList<String> list;

    /**
     * Create a new test class
     */
    public DoublyLinkedListTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }

    /**
     * Creates two brand new, empty sets for each test method.
     */
    public void setUp()
    {
        list = new DoublyLinkedList<String>();
    }


    /**
     * tests tostring
     */
    public void testToString()
    {
        assertEquals(list.toString(), "[]");
        list.addAtFront("hello");
        assertEquals(list.toString(), "[hello]");
        list.addAtFront("hi");
        assertEquals(list.toString(), "[hi, hello]");

    }

    /**
     * tests clear
     */
    public void testClear()
    {
        assertEquals(list.size(), 0);
        list.clear();
        assertEquals(list.size(), 0);
        list.addAtFront("hello");
        list.addAtFront("hello");
        list.clear();
        assertEquals(list.size(), 0);
    }


    // ----------------------------------------------------------
    /**
     * tests size method
     */
    public void testSize()
    {
        assertEquals(list.size(), 0);
        list.addAtFront("hello");
        assertEquals(list.size(), 1);
    }


    // ----------------------------------------------------------
    /**
     * tests rearItem
     */
    public void testRearItem()
    {
        list.addAtFront("hello");
        list.addAtFront("hi");
        assertEquals(list.rearItem(), "hello");
    }


    // ----------------------------------------------------------
    /**
     * tests front item
     */
    public void testFrontItem()
    {
        list.addAtFront("hello");
        list.addAtFront("hi");
        assertEquals(list.frontItem(), "hi");
    }


    /**
     * tests deququ
     */
    public void testremoveAtRear()
    {
        list.addAtFront("hello");
        list.addAtFront("hi");
        assertEquals(list.removeAtRear(), "hello");
        assertEquals(list.size(), 1);
    }


    // ----------------------------------------------------------
    /**
     * tests dequque
     */
    public void testremoveAtFront()
    {
        list.addAtFront("hello");
        list.addAtFront("hi");
        assertEquals(list.removeAtFront(), "hi");
        assertEquals(list.size(), 1);
    }


    // ----------------------------------------------------------
    /**
     * tests addAtFront
     */
    public void testaddAtFront()
    {
        list.addAtFront("hello");
        assertEquals(list.toString(), "[hello]");
        assertEquals(list.size(), 1);
        list.addAtFront("hi");
        assertEquals(list.toString(), "[hi, hello]");
    }


    /**
     * tests addAtFront
     */
    public void testaddAtRear()
    {
        list.addAtRear("hello");
        assertEquals(list.size(), 1);
        assertEquals(list.toString(), "[hello]");
        list.addAtRear("hi");
        assertEquals(list.toString(), "[hello, hi]");
    }
}
