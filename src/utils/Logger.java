package utils;

import java.io.*;

public class Logger {
    private BufferedWriter writer;
    public Logger(String filename){
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename + ".csv"), "utf-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void writeGeneration(int genNumber, int bestFitness, double avgFitness, int worstFitness){
        try {
            writer.write(genNumber + ";" + bestFitness + ";" + avgFitness + ";" + worstFitness  +"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
