package programs;

import datastructures.MemPool;
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
     * a constructor that also takes a tablesize
     * @param poolsize the poolsize for the memory pool
     * @param tablesize the hash table size
     */
    public MemManager(int poolsize, int tablesize) {
        memPool = new MemPool(poolsize);
        hashMapArtists = new HashTable(tablesize, this);
        hashMapSongs = new HashTable(tablesize, this);
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
     * @param theHandle the handle referencing the data to remove
     */
    public void remove(Handle theHandle)
    {
        memPool.remove(theHandle);
    }


    /**
     * takes the handle and returns the corresponding string
     *
     * @param h
     * @return returns the byte array corresponding the handle
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
     * returns the hashtable of artists
     * @return the hashtable of artists
     */
    public HashTable getHashTableArtists()
    {
        return hashMapArtists;
    }


    /**
    * returns the hashtable of songs
    * @return the hashtable of songs
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
        return memPool.getCurrentPoolSize();
    }
}
