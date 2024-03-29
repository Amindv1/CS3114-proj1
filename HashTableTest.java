
import junit.framework.TestCase;

/**
 * // -------------------------------------------------------------------------
/**
 *  This class tests the HashTable class class.
 *
 *  @author Amin Davoodi (amind1)
 *  @version Sep 10, 2014
 */
public class HashTableTest extends TestCase
{
    private MemManager manager;

    public void setUp() {
        manager = new MemManager(10, 50);
    }

    /**
     * tests the put method
     * @throws Exception
     */
    public void testPut() throws Exception {

        assertTrue(manager.getHashTableArtists().put("doloyoloholo"));
        assertEquals(1, manager.getHashTableArtists().currentSize());
        assertEquals(50, manager.getHashTableArtists().size());

        assertTrue(manager.getHashTableArtists().put("holodoloyolo"));
        assertEquals(2, manager.getHashTableArtists().currentSize());
        assertEquals(50, manager.getHashTableArtists().size());

        assertFalse(manager.getHashTableArtists().put("doloyoloholo"));
        assertEquals(2, manager.getHashTableArtists().currentSize());
        assertEquals(50, manager.getHashTableArtists().size());

        assertTrue(manager.getHashTableArtists().put("ddaskdajbajsldbfaljsbdfansocmiqwe"));
        assertEquals(3, manager.getHashTableArtists().currentSize());
        assertEquals(50, manager.getHashTableArtists().size());

        assertFalse(manager.getHashTableArtists().put("doloyoloholo"));
        assertEquals(50, manager.getHashTableArtists().size());
        assertEquals(3, manager.getHashTableArtists().currentSize());

        assertTrue(manager.getHashTableArtists().put("yolodoloholo"));
        assertEquals(50, manager.getHashTableArtists().size());
        assertEquals(4, manager.getHashTableArtists().currentSize());

        assertTrue(manager.getHashTableArtists().put("fat"));
        assertEquals(50, manager.getHashTableArtists().size());
        assertEquals(5, manager.getHashTableArtists().currentSize());

        assertFalse(manager.getHashTableArtists().put("fat"));
        assertEquals(50, manager.getHashTableArtists().size());
        assertEquals(5, manager.getHashTableArtists().currentSize());

        assertFalse(manager.getHashTableArtists().put("yolodoloholo"));
        assertEquals(50, manager.getHashTableArtists().size());
        assertEquals(5, manager.getHashTableArtists().currentSize());

        assertEquals(19, manager.getHashTableArtists().searchAndReturnPosOfString("yolodoloholo"));
        assertEquals(16, manager.getHashTableArtists().searchAndReturnPosOfString("holodoloyolo"));
        assertEquals(15, manager.getHashTableArtists().searchAndReturnPosOfString("doloyoloholo"));

    }

    /**
     * tests the get method
     * @throws Exception
     */
    public void testGet() throws Exception {
        manager.getHashTableArtists().put("hello");
        assertNull(manager.getHashTableArtists().get("heo"));

        assertEquals(0, manager.getHashTableArtists().get("hello").getData());

        manager.getHashTableArtists().put("yoloswag");
        assertEquals(7, manager.getHashTableArtists().get("yoloswag").getData());

        assertNull(manager.getHashTableArtists().get("swagger"));
    }

    /**
     * Tests the remove method
     * @throws Exception
     */
    public void testRemove() throws Exception {
        manager.getHashTableArtists().put("woop");
        assertEquals(0, manager.getHashTableArtists().get("woop").getData());
        assertTrue(manager.getHashTableArtists().remove("woop"));

        assertFalse(manager.getHashTableArtists().remove("woop"));
        assertEquals(0, manager.getHashTableArtists().currentSize());

        manager.getHashTableArtists().put("woop");
        assertEquals(0, manager.getHashTableArtists().get("woop").getData());
    }

    /**
     * Tests a complicated series of insertions and deletions
     * @throws Exception
     *
     */
    public void testComplicated() throws Exception {

        manager = new MemManager(32, 10);

        assertTrue(manager.getHashTableArtists().put("aaaabbbbcccc"));
        assertTrue(manager.getHashTableArtists().put("aaaaccccbbbb"));
        assertTrue(manager.getHashTableArtists().put("bbbbaaaacccc"));
        assertTrue(manager.getHashTableArtists().put("bbbbccccaaaa"));
        assertTrue(manager.getHashTableArtists().put("ccccbbbbaaaa"));

        manager.getHashTableArtists().print();

        assertTrue(manager.getHashTableArtists().put("ccccaaaabbbb"));
        manager.getHashTableArtists().print();

        assertTrue(manager.getHashTableArtists().remove("ccccaaaabbbb"));
        assertTrue(manager.getHashTableArtists().remove("bbbbaaaacccc"));
        assertEquals(-1, manager.getHashTableArtists().searchAndReturnPosOfString("bbbbaaaacccc"));
        assertEquals(-1, manager.getHashTableArtists().searchAndReturnPosOfString("ccccaaaabbbb"));


    }

}
