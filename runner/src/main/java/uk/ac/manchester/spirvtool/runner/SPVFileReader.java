package uk.ac.manchester.spirvtool.runner;

import uk.ac.manchester.spirvtool.lib.BinaryWordStream;

import java.io.*;

public class SPVFileReader implements BinaryWordStream {

    private final BufferedInputStream inputStream;

    public SPVFileReader(String filename) throws FileNotFoundException {
        inputStream = new BufferedInputStream(new FileInputStream(new File(filename)));
    }

    @Override
    public int getNextWord() {
        try {
            return inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
