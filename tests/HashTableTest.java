package tests;

import programs.MemManager;
/**
 * // -------------------------------------------------------------------------
/**
 *  This class tests the Hashmanager.getHashTableArtists() class.
 *
 *  @author Amin Davoodi (amind1)
 *  @version Sep 10, 2014
 */
public class HashTableTest extends student.TestCase
{
    private MemManager manager;

    public void setUp() {
        manager = new MemManager(10, 5);
    }

    /**
     * tests the hash method
     * @throws Exception
     */
    public void testPut() throws Exception {

        assertEquals("added successfully", manager.getHashTableArtists().put("hellbarr"));
        assertEquals(1, manager.getHashTableArtists().currentSize());
        assertEquals(5, manager.getHashTableArtists().size());

        assertEquals("position taken, added to probed position 4 successfully", manager.getHashTableArtists().put("barrhell"));
        assertEquals(2, manager.getHashTableArtists().currentSize());
        assertEquals(5, manager.getHashTableArtists().size());

        assertEquals("The key already exists", manager.getHashTableArtists().put("hellbarr"));
        assertEquals(2, manager.getHashTableArtists().currentSize());
        assertEquals(5, manager.getHashTableArtists().size());

        assertEquals("added successfully", manager.getHashTableArtists().put("faz"));
        assertEquals(3, manager.getHashTableArtists().currentSize());
        assertEquals(10, manager.getHashTableArtists().size());

        assertEquals("added successfully", manager.getHashTableArtists().put("doloyoloholo"));
        assertEquals(10, manager.getHashTableArtists().size());
        assertEquals(4, manager.getHashTableArtists().currentSize());

        assertEquals("position taken, added to probed position 9 successfully", manager.getHashTableArtists().put("holodoloyolo"));
        assertEquals(10, manager.getHashTableArtists().size());
        assertEquals(5, manager.getHashTableArtists().currentSize());

        assertEquals("added successfully", manager.getHashTableArtists().put("fat"));
        assertEquals(20, manager.getHashTableArtists().size());
        assertEquals(6, manager.getHashTableArtists().currentSize());

        assertEquals("The key already exists", manager.getHashTableArtists().put("fat"));
        assertEquals(20, manager.getHashTableArtists().size());
        assertEquals(6, manager.getHashTableArtists().currentSize());

        assertEquals("position taken, added to probed position 9 successfully", manager.getHashTableArtists().put("yolodoloholo"));
        assertEquals(20, manager.getHashTableArtists().size());
        assertEquals(7, manager.getHashTableArtists().currentSize());

        assertEquals(9, manager.getHashTableArtists().searchAndReturnPosOfString("yolodoloholo"));
        assertEquals(5, manager.getHashTableArtists().searchAndReturnPosOfString("holodoloyolo"));
        assertEquals(5, manager.getHashTableArtists().searchAndReturnPosOfString("doloyoloholo"));

    }

    /**
     * tests the get method
     * @throws Exception
     */
    public void testGet() throws Exception {
        manager.getHashTableArtists().put("hello");
        assertNull(manager.getHashTableArtists().get("heo"));

        assertEquals(1, manager.getHashTableArtists().get("hello").getData());

        manager.getHashTableArtists().put("yoloswag");
        assertEquals(40, manager.getHashTableArtists().get("yoloswag").getData());

        assertNull(manager.getHashTableArtists().get("swagger"));
    }

    /**
     * Place a description of your method here.
     * @throws Exception
     */
    public void testRemove() throws Exception {
        manager.getHashTableArtists().put("woop");
        assertEquals(1, manager.getHashTableArtists().get("woop").getData());
        assertTrue(manager.getHashTableArtists().remove("woop"));

        assertFalse(manager.getHashTableArtists().remove("woop"));
        assertEquals(0, manager.getHashTableArtists().currentSize());
    }

    /**
     *
     */
}
