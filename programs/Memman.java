package programs;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.BufferedReader;

public class Memman
{
    private MemManager memman;
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub

    }

    public void parse(File file) throws Exception
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(new
            FileInputStream(file)));

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
                    memman.getHashTableArtists().put(info[0]);
                    memman.getHashTableSongs().put(info[1]);
                    break;
                case "remove":
                    info = command[1].split(" ", 2);
                    switch(info[0])
                    {
                        case "artist":
                            memman.getHashTableArtists().remove(info[1]);
                            break;
                        case "song":
                            memman.getHashTableSongs().remove(info[1]);
                            break;
                    }
                    break;
                case "print":
                    switch(command[1])
                    {
                        case "artist":
                            System.out.println("total artists: " +
                                memman.getHashTableArtists().currentSize());
                            break;
                        case "song":
                            System.out.println("total artists: " +
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
