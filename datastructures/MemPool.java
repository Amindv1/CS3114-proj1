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


    public DoublyLinkedList getLinkedList()
    {
        return freeblockList;
    }


    /**
     * @param string
     * @param size
     * @return
     */
    public int insert(byte[] string, int size)
    {

        int k = findBestFit(size + DATALENGTH);

        // get the byte representation of the size
        byte byte1 = (byte)(size & 0xF);
        byte byte2 = (byte)((size >> 8) & 0xF);

        System.out.println("mempool length: " + memPool.length);
        System.out.println("k value: " + k);
        memPool[k] = byte1;
        memPool[k + 1] = byte2;

        for (int i = k + DATALENGTH; i < k + string.length; i++)
        {
            memPool[i] = string[i - k];
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
     * @return
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
            System.out.println("entered for loop");
            System.out.println("dll size in loop: " + freeblockList.size());
            int currDiff = freeblockList.getCurrentLength() - length;
            System.out
                .println("curr diff for iteration " + i + ": " + currDiff);
            // we change our best difference if the new one is closer to 0.
            if (0 <= currDiff && currDiff < bestDiff)
            {
                System.out.println("entered if statement");
                bestPos = freeblockList.getCurrentPosition();
                bestDiff = currDiff;
                System.out.println("best diff, best pos: " + bestDiff + ", "
                    + bestPos);
            }

            // if our best difference is 0 then break the loop, we have found
            // the best position
            if (bestDiff == 0)
            {
                System.out.println("if statement best pos: " + bestPos);
                return bestPos;
            }
        }
        System.out.println("current best pos: " + bestPos);

        // if we did not change the best position then there is no block large
        // enough so we have to increase the size and call the method again.
        if (bestPos == -1)
        {
            System.out.println("if statement1: " + bestPos);
            increaseSize();
            bestPos = findBestFit(length);
        }

        System.out.println("if statement2: " + bestPos);
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


    // ----------------------------------------------------------
    /**
     * merges the free blocks in the freeblock list.
     */
    public void merge()
    {

        freeblockList.moveToFront();
        System.out.println("in merge. dll size: " + freeblockList.size());
        for (int i = 0; i < freeblockList.size(); i++)
        {

            if (freeblockList.getNext() == null)
            {
                System.out.println("in merge. Next is null");
                return;
            }

            // checks if the two freeblocks are adjacent, if they are it merges
// them.
            if (freeblockList.getCurrentPosition()
                + freeblockList.getCurrentLength() == (freeblockList
                .getNext().getPosition()))
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
    public int getPoolSize()
    {
        return initialPoolSize;
    }

}
