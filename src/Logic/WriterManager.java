package Logic;

import constants.Messages;

import java.io.*;

public class WriterManager {
    File outputFile = new File("resources/output.txt");

    private BufferedWriter writer;


    public void instantiereBufferedWriter(){
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public void write(String string) {

        try {
            writer.write(string);
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeMethod(){

        try {
            writer.flush();   // accelerarea scrierii
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



