package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVExecutionModel extends SPIRVEnum {
    public static SPIRVExecutionModel Vertex = new SPIRVExecutionModel(0);
    public static SPIRVExecutionModel TessellationControl = new SPIRVExecutionModel(1);
    public static SPIRVExecutionModel TessellationEvaluation = new SPIRVExecutionModel(2);
    public static SPIRVExecutionModel Geometry = new SPIRVExecutionModel(3);
    public static SPIRVExecutionModel Fragment = new SPIRVExecutionModel(4);
    public static SPIRVExecutionModel GLCompute = new SPIRVExecutionModel(5);
    public static SPIRVExecutionModel Kernel = new SPIRVExecutionModel(6);
    public static EmptySPIRVExecutionModel Empty = new EmptySPIRVExecutionModel();

    protected SPIRVExecutionModel(int value) {
        super(value);
    }

    public static class EmptySPIRVExecutionModel extends SPIRVExecutionModel {
        protected EmptySPIRVExecutionModel() {
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
