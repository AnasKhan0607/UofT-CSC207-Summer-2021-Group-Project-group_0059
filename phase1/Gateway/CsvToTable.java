import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvToTable {

    String myPath = new File("").getAbsolutePath();

    public CsvToTable() {

    }

    public CsvToTable(String fileFolder) {
        myPath = fileFolder;
    }



    public List<String[]> read(String fileLocation) throws IOException {
        //        \phase1\data\GameData.csv
        BufferedReader csvReader = new BufferedReader(new FileReader(myPath + fileLocation));
        String aRow;
        List<String[]> rows = new ArrayList<>();

        while ((aRow = csvReader.readLine()) != null) {
            String[] dataLine = aRow.split(",");
            rows.add(dataLine);
        }
        return rows;
    }
}
