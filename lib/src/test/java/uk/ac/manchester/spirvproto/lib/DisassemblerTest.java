package uk.ac.manchester.spirvproto.lib;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import uk.ac.manchester.spirvproto.lib.disassembler.*;
import uk.ac.manchester.spirvproto.lib.disassembler.InvalidSPIRVEnumerantException;
import uk.ac.manchester.spirvproto.lib.disassembler.InvalidSPIRVOpcodeException;

import java.io.PrintStream;
import java.nio.ByteOrder;

public class DisassemblerTest {

	protected static PrintStream outStream;
	protected static SPIRVDisassemblerOptions options;

	@BeforeClass
	public static void setupFixture() {
		outStream = new VoidStream();
		options = Mockito.mock(SPIRVDisassemblerOptions.class);
	}

	@Test
	public void testAutomaticEndiannessDetection() throws Exception {
		BinaryWordStream wordStreamL = Mockito.mock(BinaryWordStream.class);
		BinaryWordStream wordStreamB = Mockito.mock(BinaryWordStream.class);
		Mockito.when(wordStreamL.getNextWord()).thenReturn(0x07230203);
		Mockito.when(wordStreamB.getNextWord()).thenReturn(0x03022307);


		Disassembler littleE = new Disassembler(wordStreamL, outStream, options);
		Disassembler BigE = new Disassembler(wordStreamB, outStream, options);

		try { littleE.run(); } catch (Exception ignore) {}
		try { BigE.run(); } catch (Exception ignore) {}

		Mockito.verify(wordStreamL, Mockito.times(1)).setEndianness(ByteOrder.LITTLE_ENDIAN);
		Mockito.verify(wordStreamB, Mockito.times(1)).setEndianness(ByteOrder.BIG_ENDIAN);
	}

	@Test(expected = InvalidBinarySPIRVInputException.class)
	public void testInvalidModule() throws Exception {
		BinaryWordStream ws = Mockito.mock(BinaryWordStream.class);
		Mockito.when(ws.getNextWord()).thenReturn(0);

		Disassembler disassembler = new Disassembler(ws, outStream, options);
		disassembler.run();
	}

	@Test(expected = InvalidSPIRVOpcodeException.class)
	public void testWrongOpCode() throws Exception {
		BinaryWordStream ws = new TesterWordStream(new int[] {0x00023211, 4});
		Disassembler disasm = new Disassembler(ws, outStream, options);

		disasm.run();
	}

	@Test(expected = InvalidSPIRVEnumerantException.class)
	public void testWrongEnumerant() throws Exception {
		BinaryWordStream ws = new TesterWordStream(new int[]{0x00020011, 3211});
		Disassembler disasm = new Disassembler(ws, outStream, options);

		disasm.run();
	}

	@Test(expected = InvalidSPIRVWordCountException.class)
	public void testWrongWordCount() throws Exception {
		BinaryWordStream ws = new TesterWordStream(new int[] {0x00010011, 0});
		Disassembler disassembler = new Disassembler(ws, outStream, options);

		disassembler.run();
	}
}