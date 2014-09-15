package datastructures;

import programs.MemManager;
import programs.Handle;

/**
 * // -------------------------------------------------------------------------
 * /** This is the implementation of the hash table data structure.
 *
 * @author Amin Davoodi (amind1)
 * @version Sep 8, 2014
 */
public class HashTable
{
    private int        size;
    private Handle[]   hashMap;
    private int        currSize = 0;
    private MemManager memoryManager;


    /**
     * creates the table initializes the memory manager creates the array of
     * handles for the table
     *
     * @param size
     *            the size of the table
     * @param manager
     */
    public HashTable(int size, MemManager manager)
    {
        this.size = size;
        hashMap = new Handle[size];
        memoryManager = manager;
    }


    /**
     * this method hashes the string that is passed in
     *
     * @param key
     *            the string to be hashed
     */
    private long hashString(String key)
    {

        String keyString = key;

        int intLength = keyString.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++)
        {
            char[] c = keyString.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++)
            {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = keyString.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++)
        {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (Math.abs(sum) % size);
    }


    /**
     * puts a handle into the map
     *
     * @param string
     *            the string we want to put in
     * @return returns the statement corresponding to the situation
     * @throws Exception
     */
    public String put(String string)
        throws Exception
    {

        int position = (int)hashString(string);
        Handle handle =
            memoryManager.insert(string.getBytes(), string.length());

        // if there is nothing in the position
        if (hashMap[position] == null)
        {
            position = (int)hashString(string);
            hashMap[position] = handle;
            currSize++;
            rehash();
            return "added successfully";
        }

        // if the position is already taken but it isn't same value
        else if (hashMap[position] != null && !hashMap[position].equals(handle))
        {

            // if the probe method finds the string inside it then return
            // that the string already exists
            int k = searchAndReturnBestPosToPlace(string);
            if (k == -1)
            {
                return "The key already exists";
            }

            hashMap[k] = handle;
            currSize++;
            rehash();
            return "position taken, added to probed position " + k + " successfully";
        } // if the position is already taken and is same value

        else if (hashMap[position] != null && hashMap[position].equals(handle))
        {
            return "The key already exists";
        }

        rehash();
        return "added successfully";
    }


    /**
     * re-hash's the entire array when we try to add over half the size
     *
     * @throws Exception
     */
    private void rehash()
        throws Exception
    {
        if (currSize > size / 2)
        {
            size = (size * 2);
            Handle[] newMap = new Handle[size];
            for (int i = 0; i < size / 2; i++)
            {
                if (hashMap[i] != null)
                {

                    String str = getStringFromBytes(hashMap[i]);

                    int newpos = searchAndReturnBestPosToPlace(str);
                    newMap[newpos] = hashMap[i];
                }
            }
            hashMap = newMap;

        }
    }


    /**
     * takes a handle and returns the corresponding string
     *
     * @param h
     *            the handle we want to get the corresponding string to
     * @return the return value
     * @throws Exception
     */
    private String getStringFromBytes(Handle h)
        throws Exception
    {
        // if the handle is a toumbstone then we don't want to return null
        if (h.getData() == -1)
        {
            return null;
        }

        byte[] bytes = memoryManager.getValue(h);
        return new String(bytes, "UTF-8");

    }


    /**
     * gets the value from the hashtable
     *
     * @param key
     *            the value we want to obtain
     * @return returns the value if it exists
     * @throws Exception
     */
    public Handle get(String key)
        throws Exception
    {

        if (searchAndReturnPosOfString(key) == -1)
        {
            return null;
        }
        else
        {
            return hashMap[searchAndReturnPosOfString(key)];
        }

    }


    /**
     * this method returns the new index of the
     *
     * @param stringToInsert
     *            the string whose position we are looking for
     * @return returns the next null position that is available.
     * @throws Exception
     */
    public int searchAndReturnBestPosToPlace(String stringToInsert)
        throws Exception
    {
        int home = (int)hashString(stringToInsert);
        int pos = home;
        int bestPos = home;
        int tombstoneIdx = -1;

        for (int i = 1; hashMap[pos] != null; i++)
        {

            String str = getStringFromBytes(hashMap[pos]);
            // we found our first tombstone so we want to replace it with
            // our value if we don't find a duplicate
            if (tombstoneIdx == -1 && hashMap[pos].getData() == -1)
            {
                tombstoneIdx = pos;
            }

            // a duplicate of the string was found return -1
            if (str == stringToInsert)
            {
                return -1;
            }

            pos = home;
            pos += i * i;
            pos = pos % size;

            // we ended up in the same location so that means that we did not
            // hit a null location and our array is full of toumbstones.
            // we return the pos we started at
            if (pos == home)
            {
                bestPos = tombstoneIdx;
                return bestPos;
            }
        }

        if (tombstoneIdx != -1)
        {
            bestPos = tombstoneIdx;
        }

        return bestPos;
    }


    /**
     * @return returns the size of the hashmap
     */
    public int size()
    {
        return size;
    }


    /**
     * returns the number of sots of the map that are currently being used
     *
     * @return returns the current size of the hashmap
     */
    public int currentSize()
    {
        return currSize;
    }


    /**
     * @param key
     *            the value to remove
     * @return true the value was successfully removed false the value was not
     *         found
     * @throws Exception
     */
    public boolean remove(String key)
        throws Exception
    {

        int pos = searchAndReturnPosOfString(key);

        if (pos != -1)
        {
            memoryManager.remove(hashMap[pos]);
            hashMap[pos] = new Handle(-1);
            currSize--;
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * searches the hash table for the position of the key
     *
     * @param key
     * @return returns the position in the array that the string is held
     * @throws Exception
     */
    public int searchAndReturnPosOfString(String key)
        throws Exception
    {

        int position = (int)hashString(key);
        int home = position;

        if (hashMap[position] == null)
        {
            return -1;
        }
        else if (getStringFromBytes(hashMap[position]) == key)
        {
            return position;
        }
        else
        {
            int i = 1;
            position += i * i;
            while (hashMap[position] != null)
            {

                if (position == home)
                {
                    return -1;
                }
                if (getStringFromBytes(hashMap[position]) == key)
                {
                    return position;
                }
                position = home;
                i++;
                position += i * i;
                position = (position % size);
            }
            return -1;
        }

    }
}
