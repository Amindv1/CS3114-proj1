package davoodi.src;
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
     *            the position od the data in the memory pool.
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


    public String toString()
    {
        String s = "";
        s += getData();
        return s;
    }


    /**
     * Place a description of your method here.
     *
     * @param h
     *            the handle to check
     * @return true if the handles have same data false otherwise
     */
    public boolean equals(Handle h)
    {

        if (h == null)
        {
            return false;
        }

        if (pos == h.getData())
        {
            return true;
        }
        return false;
    }
}
