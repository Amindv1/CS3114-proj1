/**
 *  Tests the handle class
 *
 *  @author Amin
 *  @version Sep 17, 2014
 */
public class HandleTest extends student.TestCase
{

    /**
     * tests the methods in the handle class
     */
    public void testHandle() {

        Handle h = new Handle(3);
        Handle h2 = new Handle(2);

        assertEquals(3, h.getData());
        assertFalse(h.equals(h2));
        h2.setData(3);
        assertEquals(3, h2.getData());
        assertTrue(h2.equals(h));

        assertFalse(h2.equals(null));
    }
}
