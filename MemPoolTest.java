package davoodi.src;

import junit.framework.TestCase;

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
    extends TestCase
{

    private MemPool pool;


    public void setUp()
    {
        pool = new MemPool(5);
    }


    /**
     * tests the insert method
     */

    public void testInsert() {

        assertEquals(5, pool.getInitialPoolSize());

        assertEquals(1,pool.getLinkedList().size());
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

        assertEquals(pool.getLinkedList().size(), 0 );

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

        Handle h1 = new Handle(pool.insert("fat".getBytes(), "fat".length()));
        Handle h2 = new Handle(pool.insert("yolo".getBytes(), "yolo".length()));
        Handle h3 = new Handle(pool.insert("yolewqeo".getBytes(), "yolewqeo".length()));
        Handle h4 = new Handle(pool.insert("yolasdo".getBytes(), "yolasdo".length()));
        Handle h5 = new Handle(pool.insert("yolaklsndklnado".getBytes(), "yolaklsndklnado".length()));

        pool.remove(h2);
        pool.remove(h4);
        assertEquals(3, pool.getLinkedList().size());
        pool.remove(h3);
        assertEquals(2, pool.getLinkedList().size());
        pool.remove(h5);
        assertEquals(2, pool.getLinkedList().size());
        pool.remove(h1);
        assertEquals(2, pool.getLinkedList().size());
        assertEquals(50, pool.getCurrentPoolSize());
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

    /**
     * tests find best fit method
     */
    public void testFindBestFit() {

        assertEquals(5, pool.getCurrentPoolSize());

        Handle h1 = new Handle(pool.insert("fat".getBytes(), "fat".length()));
        Handle h2 = new Handle(pool.insert("yolo".getBytes(), "yolo".length()));
        Handle h3 = new Handle(pool.insert("yolewqeo".getBytes(), "yolewqeo".length()));
        Handle h4 = new Handle(pool.insert("yolasdo".getBytes(), "yolasdo".length()));
        Handle h5 = new Handle(pool.insert("yolaklsndklnado".getBytes(), "yolaklsndklnado".length()));

        assertEquals(1, pool.getLinkedList().size());
        assertEquals(50, pool.getCurrentPoolSize());

        pool.remove(h1);
        assertEquals(2, pool.getLinkedList().size());
        pool.getLinkedList().moveToFront();
        assertEquals(0, pool.getLinkedList().getCurrentPosition());
        assertEquals(5, pool.getLinkedList().getCurrentLength());

        pool.getLinkedList().next();
        assertEquals(47, pool.getLinkedList().getCurrentPosition());
        assertEquals(3, pool.getLinkedList().getCurrentLength());

        pool.insert("fat".getBytes(), "fat".length());
        assertEquals(1, pool.getLinkedList().size());
        assertEquals(50, pool.getCurrentPoolSize());

    }
}
