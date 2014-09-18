/**
 * This is the implementation of the hash table data structure.
 *
 * @author Amin Davoodi (amind1)
 * @author Burhan Ishaq (iburhan)
 * @version Sep 8, 2014
 */
public class HashTable
{
    private int        size;
    private Handle[]   hashMap;
    private int        currSize = 0;
    private MemManager memoryManager;


    /**
     * creates the table, initializes the memory manager and creates the array
     * of Handles for the table
     *
     * @param size
     *            the size of the table
     * @param memManager
     *            the memory manager
     */
    public HashTable(int size, MemManager memManager)
    {
        this.size = size;
        hashMap = new Handle[size];
        memoryManager = memManager;
    }


    /**
     * this method hashes the string that is passed in using the sfold function
     *
     * @param key
     *            the string to be hashed
     * @return hash function output (first position to place)
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


    /**
     * hashes and finds the best position to place the Handle for the String
     * after inserting the String into the memory pool, then inserts the Handle
     * into the HashTable.
     *
     * @param str
     *            String to be hashed and have its Handle placed into the table
     * @throws Exception
     * @return if put worked and placed a Handle into the table
     */
    public boolean put(String str)
        throws Exception
    {

        int pos = searchAndReturnBestPosToPlace(str);

        // if searchAndReturnBestPosToPlace could not find a position to place
        // the method returns false
        if (pos == -1)
        {
            return false;
        }

        // inserts the String into the memory pool and adds the Handle to the
        // table
        hashMap[pos] = memoryManager.insert(str.getBytes(), str.length());

        // checks for if this put will pass half the capacity of the table and
        // rehashes if it does
        rehash();
        currSize++;
        return true;
    }


    /**
     * goes through the table and prints each String with its Handle's position
     * in the table
     *
     * @throws Exception
     */
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
        // condition to rehash
        if (currSize >= size / 2)
        {
            Handle[] temp = new Handle[size];
            // make a temporary array that is the same as the first
            for (int i = 0; i < hashMap.length; i++)
            {
                temp[i] = hashMap[i];
            }
            // set the old hashmap to be a new one with double the size and
            // double the size
            hashMap = new Handle[size *= 2];
            // rehash everything in the temporary array to the old array with
            // a larger size
            for (int i = 0; i < size / 2; i++)
            {
                if (getStringFromBytes(temp[i]) != null)
                {

                    String str = getStringFromBytes(temp[i]);
                    // probe for the best position inside the new hashmap
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
        // if the handle is a tombstone or null then return null
        if (h == null || h.getData() == -1)
        {
            return null;
        }

        byte[] bytes = memoryManager.getValue(h);
        return new String(bytes, "UTF-8");

    }


    /**
     * gets the handle corresponding to the string from the hashtable
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
     * this method returns the best index to place the string
     *
     * @param stringToInsert
     *            the string whose position we are looking for
     * @return returns the best position in the array for the given string
     *         returns -1 if there is a duplicate
     * @throws Exception
     */
    public int searchAndReturnBestPosToPlace(String stringToInsert)
        throws Exception
    {

        int home = (int)hashString(stringToInsert);
        int pos = home;
        int tombstoneIdx = -1;
        // if there is no handle then this is the position we want
        if (hashMap[pos] == null)
        {
            return pos;
        }
        // if there is a tombstone or a handle whose string is not the string
        // we are trying to insert
        if (hashMap[pos].getData() == -1
            || !getStringFromBytes(hashMap[pos]).equals(stringToInsert))
        {
            // if there is a tombstone at the location we want to change the
            // tombstone index. Only change if it's the first tombstone we've
// hit
            if (hashMap[pos].getData() == -1 && tombstoneIdx == -1)
            {
                tombstoneIdx = pos;
            }
            // probe
            int i = 1;
            pos += (i * i);
            pos = pos % size;
            // as long as we don't hit a null position we want to keep checking
            // the array
            while (hashMap[pos] != null)
            {
                // when we hit our first tombstone change the tombstone index
                if (hashMap[pos].getData() == -1 && tombstoneIdx == -1)
                {
                    tombstoneIdx = pos;
                }
                // if we are not at a handle and the strings are equal then
// we've
                // found a duplicate and we want to exit
                if (hashMap[pos].getData() != -1
                    && getStringFromBytes(hashMap[pos]).equals(stringToInsert))
                {
                    return -1;
                }
                // if we end up at the position we've started then the array is
                // full of tombstones and we return the index of the first
                // tombstone we've it
                if (pos == home)
                {
                    return tombstoneIdx;
                }

                pos = home;
                i++;
                pos += (i * i);
                pos = pos % size;
            }
            // if we broke the loop then we hit null and we want to return the
            // first tombstone index that we hit, if we hit one
            if (tombstoneIdx != -1)
            {
                return tombstoneIdx;
            }
        }
        // if the strings are equal return -1
        if (hashMap[pos] != null
            && getStringFromBytes(hashMap[pos]).equals(stringToInsert))
        {
            return -1;
        }

        return pos;
    }


    /**
     * gets the size of the hashtable
     *
     * @return returns the size of the hashmap
     */
    public int size()
    {
        return size;
    }


    /**
     * returns the number of slots of the map that are currently being used
     *
     * @return returns the current size of the hashmap
     */
    public int currentSize()
    {
        return currSize;
    }


    /**
     * removes a string from the memory pool and the corresponding handle form
     * the hashtable
     *
     * @param key
     *            the value to remove
     * @return true the value was successfully removed false the value was not
     *         found
     * @throws Exception
     */
    public boolean remove(String key)
        throws Exception
    {
        //returns the position of the string if it exists, -1 if it does not
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
     * searches the hash table for the position of the key if it exists
     *
     * @param key the string we are looking for
     * @return returns the position in the array that the string is held
     *         -1 if it is not found
     * @throws Exception
     */
    public int searchAndReturnPosOfString(String key)
        throws Exception
    {

        int position = (int)hashString(key);
        int home = position;
        //if the position we probed to is null then it is not in the array
        if (hashMap[position] == null)
        {
            return -1;
        }
        //if we are at a tombstone or some oher string is in the position we
        //hashed to then we need to probe
        if (hashMap[position].getData() == -1
            || !getStringFromBytes(hashMap[position]).equals(key))
        {
            int i = 1;
            position += i * i;
            position = position % size;
            //while we don't hit null then it can still be in the array
            while (hashMap[position] != null)
            {
                //if the array is full of tombstones then we need to return -1
                if (position == home)
                {
                    return -1;
                }
                //if we find it then return the position
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
            //if we hit null then we broke the loop and it was not in the array
            return -1;
        }
        //if we never went into the if statement then we found the string at
        //our current spot
        return position;
    }
}
