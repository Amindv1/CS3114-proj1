import java.io.FileReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;

public class Memman
{
    private static MemManager memman;
    public static void main(String[] args) throws NumberFormatException, Exception
    {

        parse(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
    }

    public static void parse(int hashSize, int poolSize, String path) throws Exception
    {
        memman = new MemManager(poolSize, hashSize);
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));

        String line;
        String[] command;
        String[] info;

        while(in.ready())
        {
            line = in.readLine();
            command = line.split(" ", 2);
            switch(command[0])
            {
                case "insert":
                    info = command[1].split("<SEP>");
                    int asize = memman.getHashTableArtists().size();
                    int msize = memman.getPoolSize();
                    if (memman.getHashTableArtists().put(info[0])) {
                        if (asize < memman.getHashTableArtists().size())
                        {
                            System.out.println("Artist hash table size doubled.");
                        }
                        if (msize < memman.getPoolSize())
                        {
                            System.out.println("Memory pool expanded to be " + memman.getPoolSize() + " bytes.");
                        }
                        System.out.println("|" + info[0] + "| is added to the artist database.");

                    }
                    else {
                        System.out.println("|" + info[0] +  "| duplicates a record already in the artist database.");
                    }
                    msize = memman.getPoolSize();
                    int ssize = memman.getHashTableSongs().size();
                    if (memman.getHashTableSongs().put(info[1])) {
                        if (ssize < memman.getHashTableSongs().size())
                        {
                            System.out.println("Song hash table size doubled.");
                        }
                        if (msize < memman.getPoolSize())
                        {
                            System.out.println("Memory pool expanded to be " + memman.getPoolSize() + " bytes.");
                        }
                        System.out.println("|" + info[1] + "| is added to the song database.");

                    }
                    else {
                        System.out.println("|" + info[1] +  "| duplicates a record already in the song database.");
                    }

                    break;
                case "remove":
                    info = command[1].split(" ", 2);
                    switch(info[0])
                    {
                        case "artist":
                            if (memman.getHashTableArtists().remove(info[1]))
                            {
                                System.out.println("|" + info[1] + "| is removed from the artist database.");
                            }
                            else
                            {
                                System.out.println("|" + info[1] + "| does not exist in the artist database.");
                            }
                            break;
                        case "song":
                            if (memman.getHashTableSongs().remove(info[1]))
                            {
                                System.out.println("|" + info[1] + "| is removed from the song database.");
                            }
                            else
                            {
                                System.out.println("|" + info[1] + "| does not exist in the song database.");
                            }
                            break;
                    }
                    break;
                case "print":
                    switch(command[1])
                    {
                        case "artist":
                            memman.getHashTableArtists().print();
                            System.out.println("total artists: " +
                                memman.getHashTableArtists().currentSize());
                            break;
                        case "song":
                            memman.getHashTableSongs().print();
                            System.out.println("total songs: " +
                                memman.getHashTableSongs().currentSize());
                            break;
                        case "blocks":
                            memman.dump();
                            break;
                    }
                    break;
            }
        }
    }
}
