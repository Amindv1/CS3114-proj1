package tests;

import programs.Handle;
import programs.MemManager;
import datastructures.MemPool;

/**
 * // -------------------------------------------------------------------------
 * /** Write a one-sentence summary of your class here. Follow it with
 * additional details about its purpose, what abstraction it represents, and how
 * to use it.
 *
 * @author Amin
 * @version Sep 13, 2014
 */
public class MemPoolTest
    extends student.TestCase
{

    private MemPool pool;


    public void setUp()
    {
        pool = new MemPool(5);
    }


    /**
     * tests the insert method
     */
    public void testInsert()
    {

        assertEquals(5, pool.getPoolSize());

        assertEquals(1, pool.getLinkedList().size());
        pool.getLinkedList().moveToFront();
        assertEquals(5, pool.getLinkedList().getCurrentLength());

        assertEquals(0, pool.insert("hello".getBytes(), "hello".length()));
        pool.getLinkedList().moveToFront();
        assertEquals(7, pool.getLinkedList().getCurrentPosition());
        assertEquals(1, pool.getLinkedList().size());
        pool.getLinkedList().next();
        assertEquals(7, pool.getLinkedList().getCurrentPosition());
        assertEquals(3, pool.getLinkedList().getCurrentLength());

        assertEquals(7, pool.insert("yoloswag".getBytes(), "yoloswag".length()));

    }


    /**
     * tests insert method when the size is equal to the string length
     */
    public void testInsert2()
    {

        assertEquals(0, pool.insert("333".getBytes(), "333".length()));

        assertEquals(pool.getLinkedList().size(), 0);
    }


    /**
     * tests the remove method
     */
    public void testRemove()
    {

        Handle h =
            new Handle(pool.insert("hello".getBytes(), "hello".length()));

        assertEquals(0, h.getData());
        assertEquals(3, pool.getLinkedList().getCurrentLength());
        assertEquals(7, pool.getLinkedList().getCurrentPosition());

        pool.remove(h);

        assertEquals(0, pool.getLinkedList().getCurrentPosition());
        assertEquals(10, pool.getLinkedList().getCurrentLength());
        assertEquals(1, pool.getLinkedList().size());
    }

    /**
     * tests the getValue method
     */
    public void testGetValue()
    {
        Handle h =
            new Handle(pool.insert("hello".getBytes(), "hello".length()));

        byte[] arr = pool.getValue(h);

        assertEquals('h', arr[0]);
        assertEquals('e', arr[1]);
        assertEquals('l', arr[2]);
        assertEquals('l', arr[3]);
        assertEquals('o', arr[4]);
    }
}
