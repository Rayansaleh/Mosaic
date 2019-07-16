import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Read
{

    List<Integer>  info_base;
    char [][]      map_base;

    public Read(String file)
    {
        this.info_base = get_info(file);
        this.map_base = get_map(file);
    }

    //Read only the first line of the file
    //" " is set as the delimiter of each elements
    //These elements are converted to int
    //And stored into a list of int
    private List<Integer> get_info(String file)
    {
        List<Integer> info = null;
        try
        {
            String str = Files.lines(Paths.get(file)).skip(0).findFirst().get();
            info = str.lines().map(line -> line.split(" ", 4)).flatMap(Arrays::stream)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return (info);
    }

    //Read all the file except the first line
    //All the elements collected are stored in a 2d array
    private char [][] get_map(String file)
    {
        char[][] map = null;
        try (final Stream<String> fileLines = new BufferedReader(new FileReader(file)).lines().skip(1))
        {
            map = fileLines.map(String::toCharArray).toArray(char[][]::new);
        }
        catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return (map);
    }
}