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
    private static final int          DATALENGTH = 2;


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

    public DoublyLinkedList getLinkedList() {
        return freeblockList;
    }


    /**
     *
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
        }

        freeblockList.setCurrentLength(freeblockList.getCurrentLength() - size);
        freeblockList.setCurrentPosition(freeblockList.getCurrentPosition()
            + size + DATALENGTH);

        return k;
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

        for (int i = 0; i < freeblockList.size(); i++)
        {
            System.out.println("entered for loop");
            int currDiff = freeblockList.getCurrentLength() - length;
            System.out.println("curr diff for iteration " + i + ": " + currDiff);
            // we change our best difference if the new one is closer to 0.
            if (0 <= currDiff && currDiff < bestDiff)
            {
                System.out.println("entered if statement");
                bestPos = freeblockList.getCurrentPosition();
                bestDiff = currDiff;
                System.out.println("best diff, best pos: " + bestDiff + ", " +  bestPos);
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

        freeblockList.add(memPool.length, initialPoolSize);

        merge();

        memPool = newPool;
    }

    public void merge() {

        freeblockList.moveToFront();

        for (int i = 0; i < freeblockList.size(); i++) {

            if(freeblockList.getNext() == null) {
                System.out.println("in merge. Next is null");
                return;
            }

            //checks if the two freeblocks are adjacent, if they are it merges them.
            System.out.println("in merge. curr postiion: " + freeblockList.getCurrentPosition() + "next position: " + freeblockList.getNext().getPosition());
            System.out.println("curr length: " + freeblockList.getCurrentLength());
            if(freeblockList.getCurrentPosition() + freeblockList.getCurrentLength() == ((int)freeblockList.getNext().getPosition())) {

                freeblockList.setCurrentLength(freeblockList.getCurrentLength() + (int)freeblockList.getNext().getLength());

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
