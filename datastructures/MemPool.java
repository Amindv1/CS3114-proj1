package datastructures;

import programs.Handle;

/**
 * // -------------------------------------------------------------------------
 * /** Write a one-sentence summary of your class here. Follow it with
 * additional details about its purpose, what abstraction it represents, and how
 * to use it.
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
     * creates the byte array
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
     * @return the doubly linked list.
     */
    public DoublyLinkedList getLinkedList()
    {
        return freeblockList;
    }


    /**
     * inserts the string into the byte array and uses the freeblocks
     *
     * @param string the string to insert
     * @param size the size of the string
     * @return the position that the string was inserted into
     */
    public int insert(byte[] string, int size)
    {

        int k = findBestFit(size + DATALENGTH);

        // get the byte representation of the size
        byte byte1 = (byte)(size & 0xF);
        byte byte2 = (byte)((size >> 8) & 0xF);

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

        freeblockList.setCurrentLength(freeblockList.getCurrentLength() - size
            - DATALENGTH);
        freeblockList.setCurrentPosition(freeblockList.getCurrentPosition()
            + size + DATALENGTH);

        return k;
    }


    /**
     * @param theHandle
     *            the handle contains the position of the data we're trying to
     *            remove
     */
    public void remove(Handle theHandle)
    {

        freeblockList.add(
            theHandle.getData(),
            ((memPool[theHandle.getData()] & 0xFF))
                + (memPool[theHandle.getData() + 1] << 8) + DATALENGTH);

        merge();
    }


    /**
     * @param length
     * @return returns the number corresponding to the best fit in the memory
     *         pool
     */
    public int findBestFit(int length)
    {
        int bestPos = -1;
        int bestDiff = Integer.MAX_VALUE;
        freeblockList.moveToFront();

        if (freeblockList.size() == 0)
        {
            increaseSize();
            bestPos = findBestFit(length);
        }

        System.out.println("dll size: " + freeblockList.size());

        for (int i = 0; i < freeblockList.size(); i++)
        {
            int currDiff = freeblockList.getCurrentLength() - length;
            // we change our best difference if the new one is closer to 0.
            if (0 <= currDiff && currDiff < bestDiff)
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
     *
     */
    public void dump()
    {
        String str = "";
        freeblockList.moveToFront();
        for (int i = 0; i < freeblockList.size(); i++)
        {
            str +=
                "(" + freeblockList.getCurrentPosition() + ", "
                    + freeblockList.getCurrentLength() + ") -> ";
            freeblockList.next();
        }

        System.out.println(str.substring(0, str.length() - 4));
    }


    /**
     *
     */
    public void increaseSize()
    {

        byte[] newPool = new byte[memPool.length + initialPoolSize];
        for (int i = 0; i < memPool.length; i++)
        {
            newPool[i] = memPool[i];
        }

        freeblockList.add(memPool.length, initialPoolSize);

        merge();

        memPool = newPool;
    }


    /**
     * merges the free blocks in the freeblock list.
     */
    public void merge()
    {

        freeblockList.moveToFront();
        for (int i = 0; i < freeblockList.size(); i++)
        {

            if (freeblockList.getNext() == null)
            {
                return;
            }

            // checks if the two freeblocks are adjacent, if they are it merges
// them.
            if (freeblockList.getCurrentPosition()
                + freeblockList.getCurrentLength() == (freeblockList.getNext()
                .getPosition()))
            {

                freeblockList.setCurrentLength(freeblockList.getCurrentLength()
                    + freeblockList.getNext().getLength());

                freeblockList.next();
                freeblockList.removeCurrent();

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
