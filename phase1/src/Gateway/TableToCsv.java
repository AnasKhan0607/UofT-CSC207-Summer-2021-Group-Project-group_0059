package Gateway;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TableToCsv {
//      From StackOverflow: https://stackoverflow.com/questions/35749250/java-write-the-list-string-into-csv-file

    public TableToCsv() throws IOException {


        FileWriter writer = new FileWriter(new File("").getAbsolutePath() + "\\phase1\\data\\WriteData.csv");

        String input = "[\"user1\",\"track1\",\"player1\", \"user1\",\"track2\",\"player2\", \"user1\",\"track3\",\"player3\"]";
        input = input.substring(1, input.length() - 1); // get rid of brackets
        String[] split = input.split(" ");

        for(String s : split) {
            String[] split2 = s.split(",");
            writer.write(Arrays.asList(split2).stream().collect(Collectors.joining(",")));
            writer.write("\n"); // newline
        }

        writer.close();


    }

}
