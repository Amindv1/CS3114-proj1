import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * @author Amin Davoodi (amind1)
 * @author Burhan Ishaq (iburhan)
 * @version Sep 16, 2014
 */
public class Memman
{
    private static MemManager mm;

    /**
     * Main.
     *
     * @param args takes String arguments in an array
     * @throws Exception if parse fails
     */
    public static void main(String[] args) throws Exception
    {
        parse(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
    }


    /**
     * @return MemManager
     */
    public MemManager get()
    {
        return mm;
    }

    /**
     * Parses through the file.
     *
     * @param hashSize initial size of the HashTable
     * @param poolSize initial size of the memory pool
     * @param path String pathname to file input
     * @throws Exception if the method fails
     */
    public static void parse(int hashSize, int poolSize, String path)
        throws Exception
    {
        mm = new MemManager(poolSize, hashSize);
        BufferedReader in = new BufferedReader(new FileReader(path));

        String line;
        String[] command;
        String[] info;

        while (in.ready())
        {
            line = in.readLine();
            command = line.split(" ", 2);
            switch (command[0])
            {
                case "insert":
                    info = command[1].split("<SEP>");

                    info[0] = info[0].trim();
                    info[1] = info[1].trim();

                    int asize = mm.getHashTableArtists().size();
                    int msize = mm.getPoolSize();
                    if (mm.getHashTableArtists().put(info[0]))
                    {
                        if (asize < mm.getHashTableArtists().size())
                        {
                            System.out
                                .println("Artist hash table size doubled.");
                        }
                        int k = mm.getPoolSize() - msize;
                        k = k / poolSize;
                        for(int i = 1; i <= k; i++)
                        {
                            int bytes = i * poolSize + msize;
                            System.out.println("Memory pool expanded to be "
                                + bytes + " bytes.");
                        }
                        System.out.println("|" + info[0]
                            + "| is added to the artist database.");

                    }
                    else
                    {
                        System.out
                            .println("|"
                                + info[0]
                                + "| duplicates a record already in the artist database.");
                    }
                    msize = mm.getPoolSize();
                    int ssize = mm.getHashTableSongs().size();
                    if (mm.getHashTableSongs().put(info[1]))
                    {
                        if (ssize < mm.getHashTableSongs().size())
                        {
                            System.out.println("Song hash table size doubled.");
                        }
                        int k = mm.getPoolSize() - msize;
                        k = k / poolSize;
                        for(int i = 1; i <= k; i++)
                        {
                            int bytes = i * poolSize + msize;
                            System.out.println("Memory pool expanded to be "
                                + bytes + " bytes.");
                        }
                        System.out.println("|" + info[1]
                            + "| is added to the song database.");

                    }
                    else
                    {
                        System.out
                            .println("|"
                                + info[1]
                                + "| duplicates a record already in the song database.");
                    }

                    break;
                case "remove":
                    info = command[1].split(" ", 2);

                    info[0] = info[0].trim();
                    info[1] = info[1].trim();

                    switch (info[0])
                    {
                        case "artist":
                            if (mm.getHashTableArtists().remove(info[1]))
                            {
                                System.out.println("|" + info[1]
                                    + "| is removed from the artist database.");
                            }
                            else
                            {
                                System.out.println("|" + info[1]
                                    + "| does not exist in the artist "
                                    + "database.");
                            }
                            break;
                        case "song":
                            if (mm.getHashTableSongs().remove(info[1]))
                            {
                                System.out.println("|" + info[1]
                                    + "| is removed from the song database.");
                            }
                            else
                            {
                                System.out.println("|" + info[1]
                                    + "| does not exist in the song database.");
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case "print":
                    switch (command[1])
                    {
                        case "artist":
                            mm.getHashTableArtists().print();
                            System.out.println("total artists: "
                                + mm.getHashTableArtists().currentSize());
                            break;
                        case "song":
                            mm.getHashTableSongs().print();
                            System.out.println("total songs: "
                                + mm.getHashTableSongs().currentSize());
                            break;
                        case "blocks":
                            mm.dump();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        in.close();
    }
}
