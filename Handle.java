/**
 * // -------------------------------------------------------------------------
 * /** The handle stores the position of the block in the memory pool.
 *
 * @author Amin Davoodi (amind1)
 * @version Sep 9, 2014
 */
public class Handle
{
    private int pos;


    /**
     * create the handle
     *
     * @param position
     *            the position of the data in the memory pool.
     */
    public Handle(int position)
    {
        pos = position;
    }


    /**
     * gets the position that the handle stores.
     *
     * @return return the position
     */
    public int getData()
    {
        return pos;
    }


    /**
     * set the position that the handle holds.
     *
     * @param position
     *            the position that we want the handle to hold
     */
    public void setData(int position)
    {
        pos = position;
    }


    /**
     * compares the handles data to see if they are equal
     *
     * @param h
     *            the handle to check
     * @return true if the handles have same data false otherwise
     */
    public boolean equals(Handle h)
    {

        return (h != null) && (pos == h.getData());

    }
}
