import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class GameGate {

    String myPath = new File("").getAbsolutePath();

    public GameTree load_game() {

        CsvToTable CTT = new CsvToTable();

        return new GameTree(1,"Test");
    }



}


