package uk.ac.manchester.spirvtool.runner;

import uk.ac.manchester.spirvtool.lib.BinaryWordStream;

import java.io.*;

public class SPVFileReader implements BinaryWordStream {

    private final BufferedInputStream inputStream;
    private boolean littleEndian;

    public SPVFileReader(String filename) throws FileNotFoundException {
        inputStream = new BufferedInputStream(new FileInputStream(new File(filename)));
        littleEndian = true;
    }



    @Override
    public int getNextWord() {
        byte[] bytes = new byte[4];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        int word = 0;
        if (littleEndian) {
            word |= bytes[3];
            word <<= 8;
            word |= bytes[2];
            word <<= 8;
            word |= bytes[1];
            word <<= 8;
            word |= bytes[0];
        }
        else {
            word |= bytes[0];
            word <<= 8;
            word |= bytes[1];
            word <<= 8;
            word |= bytes[2];
            word <<= 8;
            word |= bytes[3];
        }
        return word;
    }

    @Override
    public void changeEndianness() {
        littleEndian = false;
    }

}
