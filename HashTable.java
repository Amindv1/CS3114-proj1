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
     * @param memManager
     */
    public HashTable(int size, MemManager memManager)
    {
        this.size = size;
        hashMap = new Handle[size];
        memoryManager = memManager;
    }


    /**
     * this method hashes the string that is passed in
     *
     * @param key
     *            the string to be hashed
     */
    public long hashString(String key)
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


    public boolean put(String str)
        throws Exception
    {

        int pos = searchAndReturnBestPosToPlace(str);
        if (pos == -1)
        {
            return false;
        }

        hashMap[pos] = memoryManager.insert(str.getBytes(), str.length());

        currSize++;
        rehash();
        return true;
    }


    public void print()
        throws Exception
    {

        for (int i = 0; i < size; i++)
        {

            String s = getStringFromBytes(hashMap[i]);

            if (s != null && hashMap[i].getData() != -1 && hashMap[i] != null)
            {
                System.out.println("|" + s + "| " + i);
            }
        }
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
            Handle[] temp = new Handle[size];
            for (int i = 0; i < hashMap.length; i++)
            {
                temp[i] = hashMap[i];
            }

            hashMap = new Handle[size *= 2];

            for (int i = 0; i < size / 2; i++)
            {
                if (getStringFromBytes(temp[i]) != null)
                {

                    String str = getStringFromBytes(temp[i]);

                    int newpos = searchAndReturnBestPosToPlace(str);
                    hashMap[newpos] = temp[i];
                }
            }
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
        if (h == null || h.getData() == -1)
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
        if (stringToInsert == null)
        {
            return -1;
        }
        int home = (int)hashString(stringToInsert);
        int pos = home;
        int tombstoneIdx = -1;

        if (hashMap[pos] == null)
        {
            return pos;
        }
        if (hashMap[pos].getData() == -1
            || !getStringFromBytes(hashMap[pos]).equals(stringToInsert))
        {

            int i = 1;
            pos += (i * i);
            pos = pos % size;
            while (hashMap[pos] != null)
            {

                if (hashMap[pos].getData() == -1 && tombstoneIdx == -1)
                {
                    tombstoneIdx = pos;
                }

                if (hashMap[pos].getData() != -1
                    && getStringFromBytes(hashMap[pos]).equals(stringToInsert))
                {
                    return -1;
                }

                if (pos == home)
                {
                    return tombstoneIdx;
                }

                pos = home;
                i++;
                pos += (i * i);
                pos = pos % size;
            }

            if (tombstoneIdx != -1)
            {
                return tombstoneIdx;
            }
        }

        if (hashMap[pos] != null
            && getStringFromBytes(hashMap[pos]).equals(stringToInsert))
        {
            return -1;
        }

        return pos;
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

        if (hashMap[position].getData() == -1
            || !getStringFromBytes(hashMap[position]).equals(key))
        {
            int i = 1;
            position += i * i;
            position = position % size;
            while (hashMap[position] != null)
            {

                if (position == home)
                {
                    return -1;
                }

                if (hashMap[position].getData() != -1
                    && getStringFromBytes(hashMap[position]).equals(key))
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

        return position;
    }
}