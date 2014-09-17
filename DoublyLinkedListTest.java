package davoodi.src;

import junit.framework.TestCase;

/**
 * Tests for the {@link DoublyLinkedList} class.
 *
 * @author Mohammed Amin Davoodi (amind1)
 * @version (2013.10.24)
 */
public class DoublyLinkedListTest
    extends TestCase
{
    // ~ Instance/static variables .............................................

    private DoublyLinkedList list;

    /**
     * Creates two brand new, empty sets for each test method.
     */
    public void setUp()
    {
        list = new DoublyLinkedList();
    }


    /**
     * tests clear
     */
    public void testClear()
    {
        assertEquals(list.size(), 0);
        list.clear();
        assertEquals(list.size(), 0);
        list.add(5, 5);
        list.add(5, 5);
        list.clear();
        assertEquals(list.size(), 0);
    }


    /**
     * tests size method
     */
    public void testSize()
    {
        assertEquals(list.size(), 0);
        list.add(5, 5);
        assertEquals(list.size(), 1);
    }


    /**
     * tests move to front method
     */
    public void testMoveToFront()
    {
        list.add(1, 7);
        list.add(2, 1);
        list.add(3, 1);
        list.add(4, 7);

        assertEquals(4, list.getCurrentPosition());
        list.moveToFront();
        assertEquals(1, list.getCurrentPosition());
        list.add(0, 2);

        list.moveToFront();

        assertEquals(0, list.getCurrentPosition());
    }


    /**
     * tests add method
     */
    public void testAdd()
    {
        list.add(2, 2);
        assertEquals(2, list.getCurrentPosition());
        list.add(1, 2);
        assertEquals(1, list.getCurrentPosition());
        list.add(8, 2);
        assertEquals(8, list.getCurrentPosition());
        list.add(3, 2);
        assertEquals(3, list.getCurrentPosition());
        list.add(0, 2);
        assertEquals(0, list.getCurrentPosition());

        list.moveToFront();

        assertEquals(0, list.getCurrentPosition());
        list.next();
        assertEquals(1, list.getCurrentPosition());
        list.next();
        assertEquals(2, list.getCurrentPosition());
        list.next();
        assertEquals(3, list.getCurrentPosition());
        list.next();
        assertEquals(8, list.getCurrentPosition());

        assertEquals(5, list.size());
    }


    /**
     * tests the move to position method
     */
    public void testMoveToPosition()
    {
        list.add(1, 7);
        list.add(2, 1);
        list.add(8, 1);
        list.add(4, 7);

        assertTrue(list.moveToPosition(8));
        assertEquals(8, list.getCurrentPosition());
        assertEquals(1, list.getCurrentLength());

        assertTrue(list.moveToPosition(1));
        assertEquals(1, list.getCurrentPosition());
        assertEquals(7, list.getCurrentLength());

        assertFalse(list.moveToPosition(9));
        assertFalse(list.moveToPosition(3));
    }


    /**
     * tests the removeCurrent method
     */
    public void testRemoveCurrent()
    {
        list.add(1, 7);
        list.add(2, 1);
        list.add(3, 1);
        list.add(4, 7);

        list.removeCurrent();
        assertEquals(3, list.size());
        assertFalse(list.moveToPosition(4));
    }


    /**
     * tests the next method
     */
    public void testNext()
    {
        list.add(1, 1);
        list.add(2, 2);

        list.moveToFront();

        assertEquals(1, list.getCurrentPosition());
        list.next();
        assertEquals(2, list.getCurrentPosition());
    }


    /**
     * tests the previous method
     */
    public void testPrevious()
    {

        list.add(1, 1);
        list.add(2, 2);

        assertEquals(2, list.getCurrentPosition());
        list.previous();
        assertEquals(1, list.getCurrentPosition());
    }


    /**
     * tests the getNext method
     */
    public void testGetNext()
    {
        list.add(1, 1);
        list.add(2, 2);

        list.moveToFront();

        assertEquals(1, list.getCurrentPosition());
        assertEquals(2, list.getNext().getPosition());
    }


    /**
     * tests the get current and set current position methods
     */
    public void testGetCurrentAndSetCurrentPosition()
    {

        list.add(1, 1);
        list.setCurrentPosition(2);

        assertEquals(2, list.getCurrentPosition());
    }


    /**
     * tests the get current and set current length methods
     */
    public void testGetCurrentAndSetCurrentLength()
    {

        list.add(1, 1);

        list.setCurrentLength(2);
        assertEquals(2, list.getCurrentLength());
    }

}
