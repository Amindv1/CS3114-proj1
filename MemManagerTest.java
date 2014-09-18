import junit.framework.TestCase;

/**
 * Tests the memManager class
 *
 * @author Amin Davoodi
 * @author Burhan Ishaq
 * @version Sep 11, 2014
 */
public class MemManagerTest
    extends TestCase
{
    private MemManager manager;


    public void setUp()
    {
        manager = new MemManager(10, 50);
    }

    /**
     * tests the increase pool size method.
     */
    public void testIncreaseSize()
    {
        assertEquals(10, manager.getPoolSize());
    }

    /**
     * tests the insert method
     */
    public void testInsert() {

        assertEquals(0, manager.insert("hello".getBytes(), "hello".length()).getData());

    }

    /**
     * tests the remove method
     */
    public void testRemove() {
        //TODO
    }

}
