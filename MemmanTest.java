package davoodi.src;

import junit.framework.TestCase;

/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Amin Davoodi (amind1)
 *  @author Burhan Ishaq (iburhan)
 *  @version Sep 16, 2014
 */
public class MemmanTest extends TestCase
{
    private Memman mm;
    /**
     * Tests parse.
     * @throws Exception if parse fails
     */
    @SuppressWarnings("static-access")
    public void testParse() throws Exception
    {
        mm = new Memman();
        String[] s = {"10", "32", "input.txt"};
        mm.main(s);

        assertEquals(6, mm.get().getHashTableArtists().currentSize());
    }
}
