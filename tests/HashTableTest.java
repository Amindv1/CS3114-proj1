package tests;

import programs.MemManager;
import datastructures.HashTable;
/**
 * // -------------------------------------------------------------------------
/**
 *  This class tests the HashTable class.
 *
 *  @author Amin Davoodi (amind1)
 *  @version Sep 10, 2014
 */
public class HashTableTest extends student.TestCase
{
    private HashTable table;
    private MemManager manager;

    public void setUp() {
        manager = new MemManager(50);
        table = new HashTable(5, manager);
    }

    /**
     * tests the hash method
     * @throws Exception
     */
    public void testPut() throws Exception {

        assertEquals("added successfully", table.put("hellbarr"));
        assertEquals(1, table.currentSize());
        assertEquals(table.size(), 5);

        assertEquals("position taken, added to probed position 0 successfully", table.put("barrhell"));
        assertEquals(2, table.currentSize());
        assertEquals(5, table.size());

        assertEquals("The key already exists", table.put("hellbarr"));
        assertEquals(2, table.currentSize());
        assertEquals(5, table.size());

        assertEquals("added successfully", table.put("faz"));
        assertEquals(3, table.currentSize());
        assertEquals(10, table.size());

        assertEquals("added successfully", table.put("doloyoloholo"));
        assertEquals(10, table.size());
        assertEquals(4, table.currentSize());

        assertEquals("position taken, added to probed position 9 successfully", table.put("holodoloyolo"));
        assertEquals(10, table.size());
        assertEquals(5, table.currentSize());

        assertEquals("added successfully", table.put("fat"));
        assertEquals(20, table.size());
        assertEquals(6, table.currentSize());

        assertEquals("The key already exists", table.put("fat"));
        assertEquals(20, table.size());
        assertEquals(6, table.currentSize());

        assertEquals("position taken, added to probed position 9 successfully", table.put("yolodoloholo"));
        assertEquals(20, table.size());
        assertEquals(7, table.currentSize());

        assertEquals(9, table.searchAndReturnPosOfString("yolodoloholo"));
        assertEquals(5, table.searchAndReturnPosOfString("holodoloyolo"));
        assertEquals(5, table.searchAndReturnPosOfString("doloyoloholo"));

    }

    /**
     * tests the get method
     * @throws Exception
     */
    public void testGet() throws Exception {
        table.put("hello");
        assertNull(table.get("heo"));

        assertEquals(1, table.get("hello").getData());

        table.put("yoloswag");
        assertEquals(40, table.get("yoloswag").getData());

        assertNull(table.get("swagger"));
    }

    /**
     * Place a description of your method here.
     * @throws Exception
     */
    public void testRemove() throws Exception {
        table.put("woop");
        assertEquals(1, table.get("woop").getData());
        assertTrue(table.remove("woop"));

        assertFalse(table.remove("woop"));
        assertEquals(0, table.currentSize());
    }

    /**
     *
     */
}
