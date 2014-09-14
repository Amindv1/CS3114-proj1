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
    private byte[]                    memPool;
    private DoublyLinkedList<Integer> freeblockList;
    private int                       initialPoolSize;


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
        freeblockList = new DoublyLinkedList<Integer>();
        freeblockList.addAtFront(0, poolSize);
    }

    /**
     *
     */
    public void insert() {

    }

    public void remove(Handle theHandle) {
        freeblockList.add((Integer) theHandle.getData(),
            (Integer) (((memPool[theHandle.getData()] & 0xFF) << 8) +
                memPool[theHandle.getData() + 1]));

        if (!freeblockList.isCurrentFirst())
        {
            freeblockList.previous();
            int pos = freeblockList.getCurrentPosition() +
            freeblockList.getCurrentLength();

            freeblockList.next();
            if (pos == freeblockList.getCurrentPosition())
            {
                merge();
            }
        }

        if (!freeblockList.isCurrentLast())
        {
            int pos = freeblockList.getCurrentPosition() +
            freeblockList.getCurrentLength();

            freeblockList.next();
            if (pos == freeblockList.getCurrentPosition())
            {
                merge();
            }
        }
    }

    public void merge()
    {
        int capacity = freeblockList.getCurrentLength();

        freeblockList.removeCurrent();
        capacity += freeblockList.getCurrentLength();

        int pos = freeblockList.getCurrentPosition();
        freeblockList.removeCurrent();

        freeblockList.add(pos, capacity);
    }


    /**
     * @param length
     * @return
     *
     */
    public int findBestFit(int length)
    {

        int bestPos = -1;
        int bestDiff = Integer.MAX_VALUE;
        freeblockList.moveToFront();


        for (int i = 0; i < freeblockList.size(); i++)
        {
            int currDiff = freeblockList.getCurrentLength() - length;
            //we change our best difference if the new one is closer to 0.
            if (0 <= currDiff && currDiff < bestDiff)
            {
                bestPos = freeblockList.getCurrentPosition();
                bestDiff = currDiff;
            }

            //if our best difference is 0 then break the loop, we have found
            //the best position
            if(bestDiff == 0) {
                return bestPos;
            }
        }

        //if we did not change the best position then there is no block large
        //enough so we have to increase the size and call the method again.
        if (bestPos == -1) {
            increaseSize();
            findBestFit(length);
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
            str += "(" + freeblockList.getCurrentPosition() +
                ", " + freeblockList.getCurrentLength() + ") -> ";
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
        memPool = newPool;
    }


    /**
     * @return returns the pool size
     */
    public int getPoolSize()
    {
        return initialPoolSize;
    }

}
