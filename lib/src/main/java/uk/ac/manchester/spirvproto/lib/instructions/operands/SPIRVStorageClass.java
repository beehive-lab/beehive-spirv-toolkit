package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVStorageClass extends SPIRVEnum {
    public static SPIRVStorageClass UniformConstant = new SPIRVStorageClass(0);
    public static SPIRVStorageClass Input = new SPIRVStorageClass(1);
    public static SPIRVStorageClass Uniform = new SPIRVStorageClass(2);
    public static SPIRVStorageClass Output = new SPIRVStorageClass(3);
    public static SPIRVStorageClass Workgroup = new SPIRVStorageClass(4);
    public static SPIRVStorageClass CrossWorkgroup = new SPIRVStorageClass(5);
    public static SPIRVStorageClass Private = new SPIRVStorageClass(6);
    public static SPIRVStorageClass Function = new SPIRVStorageClass(7);
    public static SPIRVStorageClass Generic = new SPIRVStorageClass(8);
    public static SPIRVStorageClass PushConstant = new SPIRVStorageClass(9);
    public static SPIRVStorageClass AtomicCounter = new SPIRVStorageClass(10);
    public static SPIRVStorageClass Image = new SPIRVStorageClass(11);
    public static SPIRVStorageClass StorageBuffer = new SPIRVStorageClass(12);
    public static EmptySPIRVStorageClass Empty = new EmptySPIRVStorageClass();

    protected SPIRVStorageClass(int value) {
        super(value);
    }

    public static class EmptySPIRVStorageClass extends SPIRVStorageClass {
        protected EmptySPIRVStorageClass() {
            super(-1);
        }

        @Override
        public void write(ByteBuffer output) {
            // Do nothing as this is empty
        }

        @Override
        public int getWordCount() {
            return 0;
        }
    }
}
