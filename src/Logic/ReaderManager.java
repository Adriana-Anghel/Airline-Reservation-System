package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReaderManager {

   static public BufferedReader creareReader() {

        File inputFile = new File ("resources/input.txt");
        System.out.println(inputFile.getAbsolutePath());

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return reader;
    }
}
