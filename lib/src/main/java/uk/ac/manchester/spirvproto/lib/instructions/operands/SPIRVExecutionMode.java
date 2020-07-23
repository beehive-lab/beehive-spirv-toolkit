package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVExecutionMode extends SPIRVEnum {
    public static SPIRVExecutionMode Invocations = new SPIRVExecutionMode(0);
    public static SPIRVExecutionMode SpacingEqual = new SPIRVExecutionMode(1);
    public static SPIRVExecutionMode SpacingFractionalEven = new SPIRVExecutionMode(2);
    public static SPIRVExecutionMode SpacingFractionalOdd = new SPIRVExecutionMode(3);
    public static SPIRVExecutionMode VertexOrderCw = new SPIRVExecutionMode(4);
    public static SPIRVExecutionMode VertexOrderCcw = new SPIRVExecutionMode(5);
    public static SPIRVExecutionMode PixelCenterInteger = new SPIRVExecutionMode(6);
    public static SPIRVExecutionMode OriginUpperLeft = new SPIRVExecutionMode(7);
    public static SPIRVExecutionMode OriginLowerLeft = new SPIRVExecutionMode(8);
    public static SPIRVExecutionMode EarlyFragmentTests = new SPIRVExecutionMode(9);
    public static SPIRVExecutionMode PointMode = new SPIRVExecutionMode(10);
    public static SPIRVExecutionMode Xfb = new SPIRVExecutionMode(11);
    public static SPIRVExecutionMode DepthReplacing = new SPIRVExecutionMode(12);
    public static SPIRVExecutionMode DepthGreater = new SPIRVExecutionMode(14);
    public static SPIRVExecutionMode DepthLess = new SPIRVExecutionMode(15);
    public static SPIRVExecutionMode DepthUnchanged = new SPIRVExecutionMode(16);
    public static SPIRVExecutionMode LocalSize = new SPIRVExecutionMode(17);
    public static SPIRVExecutionMode LocalSizeHint = new SPIRVExecutionMode(18);
    public static SPIRVExecutionMode InputPoints = new SPIRVExecutionMode(19);
    public static SPIRVExecutionMode InputLines = new SPIRVExecutionMode(20);
    public static SPIRVExecutionMode InputLinesAdjacency = new SPIRVExecutionMode(21);
    public static SPIRVExecutionMode Triangles = new SPIRVExecutionMode(22);
    public static SPIRVExecutionMode InputTrianglesAdjacency = new SPIRVExecutionMode(23);
    public static SPIRVExecutionMode Quads = new SPIRVExecutionMode(24);
    public static SPIRVExecutionMode Isolines = new SPIRVExecutionMode(25);
    public static SPIRVExecutionMode OutputVertices = new SPIRVExecutionMode(26);
    public static SPIRVExecutionMode OutputPoints = new SPIRVExecutionMode(27);
    public static SPIRVExecutionMode OutputLineStrip = new SPIRVExecutionMode(28);
    public static SPIRVExecutionMode OutputTriangleStrip = new SPIRVExecutionMode(29);
    public static SPIRVExecutionMode VecTypeHint = new SPIRVExecutionMode(30);
    public static SPIRVExecutionMode ContractionOff = new SPIRVExecutionMode(31);
    public static SPIRVExecutionMode PostDepthCoverage = new SPIRVExecutionMode(4446);
    public static SPIRVExecutionMode StencilRefReplacingEXT = new SPIRVExecutionMode(5027);
    public static EmptySPIRVExecutionMode Empty = new EmptySPIRVExecutionMode();

    protected SPIRVExecutionMode(int value) {
        super(value);
    }

    public static class EmptySPIRVExecutionMode extends SPIRVExecutionMode {
        protected EmptySPIRVExecutionMode() {
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
