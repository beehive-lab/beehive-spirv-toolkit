package uk.ac.manchester.spirvproto.generator;

import java.io.File;

public class Runner {
    public static void main(String[] args) {
        try {
            new Generator(new File(args[0])).generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
