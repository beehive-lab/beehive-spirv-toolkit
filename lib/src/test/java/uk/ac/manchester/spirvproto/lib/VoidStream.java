package uk.ac.manchester.spirvproto.lib;

import java.io.OutputStream;
import java.io.PrintStream;

public class VoidStream extends PrintStream {
    public VoidStream() {
        super(new OutputStream() {
            @Override
            public void write(int b) { }
        });
    }
}
