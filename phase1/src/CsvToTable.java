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

        BufferedReader csvReader = new BufferedReader(new FileReader(myPath + "\\phase1\\data\\GameData.csv"));
        String aRow;
        List<String[]> rows = new ArrayList<>();

        while ((aRow = csvReader.readLine()) != null) {
            String[] dataLine = aRow.split(",");
            rows.add(dataLine);
        }
        return rows;
    }
}
