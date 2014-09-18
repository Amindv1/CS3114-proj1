import java.util.Arrays;

/**
 * // -------------------------------------------------------------------------
 * /**
 *  This is the memory pool class. It stores the byte array and the freeblock
 *  list and provides methods to change their values.
 *
 * @author Amin Davoodi
 * @version Sep 12, 2014
 */
public class MemPool
{
    private byte[]           memPool;
    private DoublyLinkedList freeblockList;
    private int              initialPoolSize;
    private static final int DATALENGTH = 2;


    /**
     * initializes the byte array and the freeblock list.
     *
     * @param poolSize
     *            the size of the byte array
     */
    public MemPool(int poolSize)
    {
        initialPoolSize = poolSize;
        memPool = new byte[poolSize];
        freeblockList = new DoublyLinkedList();
        freeblockList.add(0, poolSize);
    }


    /**
     * returns the doubly linked list
     *
     * @return the doubly linked list.
     */
    public DoublyLinkedList getLinkedList()
    {
        return freeblockList;
    }


    /**
     * inserts the string into the byte array
     *
     * @param string
     *            the string to insert
     * @param size
     *            the size of the string
     * @return the position that the string was inserted into
     */
    public int insert(byte[] string, int size)
    {

        int k = findBestFit(size + DATALENGTH);

        // get the byte representation of the size
        byte byte1 = (byte)(size & 0xFF);
        byte byte2 = (byte)((size >> 8) & 0xFF);

        memPool[k] = byte1;
        memPool[k + 1] = byte2;

        for (int i = 0; i < string.length; i++)
        {
            memPool[i + k + DATALENGTH] = string[i];
        }

        freeblockList.moveToPosition(k);

        // if the current length is the same as the length of the string we're
        // adding then we want to remove the node.
        if (freeblockList.getCurrentLength() == size + DATALENGTH)
        {
            freeblockList.removeCurrent();
            return k;
        }

        //change the freeblocks length and position
        freeblockList.setCurrentLength(freeblockList.getCurrentLength() - size
            - DATALENGTH);
        freeblockList.setCurrentPosition(freeblockList.getCurrentPosition()
            + size + DATALENGTH);

        return k;
    }


    /**
     * 'removes' the string corresponding to the handle by adding a new
     * freeblock whose position starts at the handle start and whose length we
     * calculate.
     *
     * @param theHandle
     *            the handle contains the position of the data we're trying to
     *            remove
     */
    public void remove(Handle theHandle)
    {
        // add the freeblock with the handles position and the length
        // corresponding to the data shift.
        freeblockList.add(
            theHandle.getData(),
            ((memPool[theHandle.getData()] & 0xFF))
                + (memPool[theHandle.getData() + 1] << 8) + DATALENGTH);

        //merge after we add the freeblock
        merge();
    }


    /**
     * finds the freeblock that has the length closest to the length we pass in
     *
     * @param length
     *            the length we are looking for
     * @return returns the number corresponding to the best fit in the memory
     *         pool
     */
    public int findBestFit(int length)
    {
        int bestPos = -1;
        int bestDiff = Integer.MAX_VALUE;
        freeblockList.moveToFront();

        // if the freeblock list is equal to zero then we need to increase the
        // size
        if (freeblockList.size() == 0)
        {
            increaseSize();
            bestPos = findBestFit(length);
        }

        for (int i = 0; i < freeblockList.size(); i++)
        {
            int currDiff = freeblockList.getCurrentLength() - length;
            // we change our best difference if the new one is closer to 0.
            if (currDiff >= 0 && currDiff < bestDiff)
            {
                bestPos = freeblockList.getCurrentPosition();
                bestDiff = currDiff;
            }

            // if our best difference is 0 then break the loop, we have found
            // the best position
            if (bestDiff == 0)
            {
                return bestPos;
            }

            freeblockList.next();
        }

        // if we did not change the best position then there is no block large
        // enough so we have to increase the size and call the method again.
        if (bestPos == -1)
        {
            increaseSize();
            bestPos = findBestFit(length);
        }

        return bestPos;
    }


    /**
     * Prints the freeblock list in the format (number,number)->(number,number)
     */
    public void dump()
    {
        String str = "";
        freeblockList.moveToFront();
        for (int i = 0; i < freeblockList.size(); i++)
        {
            str +=
                "(" + freeblockList.getCurrentPosition() + ","
                    + freeblockList.getCurrentLength() + ") -> ";
            freeblockList.next();
        }

        System.out.println(str.substring(0, str.length() - 4));
    }


    /**
     * This method takes a handle and returns the byte array corresponding to
     * the string. return
     *
     * @param h
     *            the handle we want to get the byte array from.
     * @return the corresponding byte array
     */
    public byte[] getValue(Handle h)
    {
        // we know the first two values in the array after the handles position
        // are the length. We then shift the second position by 8 to get
        // the 8 bits next to it to be 0. We then add on the first byte and get
        // the length of our string. We return a copy of the array from the
        // starting position until the length we just obtained. We add
        // DATALENGTH because we know the first two bytes in the byte array
        // represent the length of the string so we want to exclude them
        return Arrays.copyOfRange(
            memPool,
            h.getData() + DATALENGTH,
            (h.getData() + DATALENGTH)
                + ((memPool[h.getData() + 1] & 0xFF) << 8)
                + memPool[h.getData()]);
    }


    /**
     * increases the size of the memory pool by the initial size
     */
    public void increaseSize()
    {
        // create a newpool and copy over the old one.
        byte[] newPool = new byte[memPool.length + initialPoolSize];
        for (int i = 0; i < memPool.length; i++)
        {
            newPool[i] = memPool[i];
        }

        // add a new freeblock to the freeblock list with the poolsize
        freeblockList.add(memPool.length, initialPoolSize);

        // merge the newblock incase there are other freeblocks at the end
        // of our list
        merge();
        memPool = newPool;
    }


    /**
     * merges the free blocks in the freeblock list.
     */
    public void merge()
    {

        // move to the start of the freeblock list to start merging from
        freeblockList.moveToFront();
        for (int i = 0; i < freeblockList.size(); i++)
        {
            // represents the end of the current node
            int end =
                freeblockList.getCurrentPosition()
                    + freeblockList.getCurrentLength();

            freeblockList.next();
            // if the last blocks length and position added together equal our
            // current blocks position then they are adjacent and we need to
            // merge them
            if (end == freeblockList.getCurrentPosition())
            {
                int len = freeblockList.getCurrentLength();
                freeblockList.removeCurrent();
                freeblockList.setCurrentLength(freeblockList.getCurrentLength()
                    + len);
            }
        }
    }


    /**
     * @return returns the pool size
     */
    public int getInitialPoolSize()
    {
        return initialPoolSize;
    }


    /**
     * get the size of the memory pool
     *
     * @return returns the current size of the memory pool
     */
    public int getCurrentPoolSize()
    {
        return memPool.length;
    }

}
