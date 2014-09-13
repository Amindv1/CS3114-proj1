package tests;

import programs.MemManager;

/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author Amin Davoodi
 * @version Sep 11, 2014
 */
public class MemManagerTest
    extends student.TestCase
{
    private MemManager manager;


    public void setUp()
    {
        manager = new MemManager(10);
    }


    /**
     * tests the increase pool size method.
     */
    public void testIncreaseSize()
    {
        assertEquals(10, manager.getPoolSize());
    }

}
