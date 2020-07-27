package uk.ac.manchester.spirvproto.lib.disassembler;

import java.io.*;

public class SPVFileReader extends SPVByteStreamReader {
    public SPVFileReader(String filename) throws FileNotFoundException {
        super(new BufferedInputStream(new FileInputStream(new File(filename))));
    }

    public SPVFileReader(File inputFile) throws FileNotFoundException {
        super(new BufferedInputStream(new FileInputStream(inputFile)));
    }
}
