package programs;

import datastructures.MemPool;
import datastructures.DoublyLinkedList;
import datastructures.HashTable;

/**
 * This class takes in inputs and adds them into the memory pool. It maintains
 * the size and locations of the entries through the doubly linked list, hashmap
 * and the memory pool
 *
 * @author Amin Davoodi (amind1)
 * @version Sep 11, 2014
 */
public class MemManager
{
    private HashTable        hashMapArtists;
    private HashTable        hashMapSongs;
    private MemPool          memPool;
    private static int       initialPoolsize;
    private static final int DATASTART = 3;


    /**
     * creates a memory manager object
     *
     * @param poolsize
     *            the poolsize for the memory pool
     */
    public MemManager(int poolsize)
    {
        memPool = new MemPool(poolsize);
        hashMapArtists = new HashTable(50, this);
        hashMapSongs = new HashTable(50, this);
    }


    /**
     * this method inserts the string into the memory pool and returns the
     * Corresponding position
     *
     * @param space
     *            the string converted to a byte array
     * @param size
     *            the size of the string
     * @return h the handle with the position that the string was stored
     */
    public Handle insert(byte[] space, int size)
    {
        Handle h = new Handle(memPool.insert(space, size));
        return h;
    }



    /**
     * removes the handle from the byte array
     */
    private void remove(Handle theHandle)
    {
        memPool.remove(theHandle);
    }


    /**
     * takes the handle and returns the corresponding string
     *
     * @param h
     * @return
     */
    public byte[] getValue(Handle h)
    {
        byte[] pool = new byte[5];
        int startPos = h.getData();

        return pool;
    }


    /**
     * prints out the freeblock list
     */
    public void dump()
    {
        memPool.dump();
    }


    /**
     *
     */
    public HashTable getHashTableArtists()
    {
        return hashMapArtists;
    }


    /**
    *
    */
    public HashTable getHashTableSongs()
    {
        return hashMapSongs;
    }


    /**
     * gets the current poolsize
     */
    public int getPoolSize()
    {
        return memPool.getPoolSize();
    }
}
