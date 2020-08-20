package uk.ac.manchester.spirvproto.generator;

import java.io.File;

public class Runner {
    public static void main(String[] args) {
        try {
            Constants constants = new Constants(Integer.decode(args[1]), Integer.decode(args[2]), Integer.decode(args[3]));
            new Generator(new File(args[0]), constants).generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
