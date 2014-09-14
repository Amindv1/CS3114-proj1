package tests;

import datastructures.MemPool;

/**
 * // -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Amin
 *  @version Sep 13, 2014
 */
public class MemPoolTest extends student.TestCase
{

    private MemPool pool;

    public void setUp() {
        pool = new MemPool(2);
    }


    /**
     * tests the insert method
     */
    public void testInsert() {

        assertEquals(2, pool.getPoolSize());

        assertEquals(1,pool.getLinkedList().size());
        pool.getLinkedList().moveToFront();
        assertEquals(2, pool.getLinkedList().getCurrentLength());

        assertEquals(0, pool.insert("hello".getBytes(), "hello".length()));
    }


}
