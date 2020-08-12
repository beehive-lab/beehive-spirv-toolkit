package uk.ac.manchester.spirvproto.lib;

import uk.ac.manchester.spirvproto.lib.assembler.InvalidSPIRVModuleException;
import uk.ac.manchester.spirvproto.lib.assembler.SPIRVModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class TestUtils {

    private static void writeBufferToFile(ByteBuffer buffer, String filepath) {
        buffer.flip();
        File out = new File(filepath);
        try {
            FileChannel channel = new FileOutputStream(out, false).getChannel();
            channel.write(buffer);
            channel.close();
        } catch (IOException e) {
            System.err.println("IO exception: " + e.getMessage());
        }

    }

    public static void writeModuleToFile(SPIRVModule module, String filepath) throws InvalidSPIRVModuleException {
        ByteBuffer out = ByteBuffer.allocate(module.getByteCount());
        out.order(ByteOrder.LITTLE_ENDIAN);
        module.validate().write(out);
        writeBufferToFile(out, filepath);
    }
}
