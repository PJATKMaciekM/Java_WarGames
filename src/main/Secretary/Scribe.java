package main.Secretary;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class Scribe implements Observer, Serializable {
    private String message;
    private String fileName;

    public Scribe(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void update(String message) {
        this.message = message;
        writeDown();
    }

    private void writeDown() {
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
