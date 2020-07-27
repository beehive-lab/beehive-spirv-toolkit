package uk.ac.manchester.spirvproto.lib;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import uk.ac.manchester.spirvproto.lib.disassembler.BinaryWordStream;
import uk.ac.manchester.spirvproto.lib.disassembler.Disassembler;
import uk.ac.manchester.spirvproto.lib.disassembler.InvalidBinarySPIRVInputException;
import uk.ac.manchester.spirvproto.lib.disassembler.InvalidSPIRVWordCountException;
import uk.ac.manchester.spirvproto.lib.grammar.InvalidSPIRVEnumerantException;
import uk.ac.manchester.spirvproto.lib.grammar.InvalidSPIRVOpcodeException;
import uk.ac.manchester.spirvproto.lib.grammar.InvalidSPIRVOperandKindException;

import java.io.IOException;
import java.io.PrintStream;

public class DisassemblerTest {

	protected static PrintStream outStream;

	@BeforeClass
	public static void setupFixture() {
		outStream = new VoidStream();
	}

	@Test
	public void testAutomaticEndiannessDetection() throws InvalidBinarySPIRVInputException, IOException {
		BinaryWordStream wordStreamL = Mockito.mock(BinaryWordStream.class);
		BinaryWordStream wordStreamB = Mockito.mock(BinaryWordStream.class);
		Mockito.when(wordStreamL.getNextWord()).thenReturn(0x07230203).thenReturn(0x00010000);
		Mockito.when(wordStreamB.getNextWord()).thenReturn(0x03022307).thenReturn(0x00010000);

		Disassembler littleE = new Disassembler(wordStreamL, outStream, false, false);
		Disassembler BigE = new Disassembler(wordStreamB, outStream, false, false);

		Mockito.verify(wordStreamL, Mockito.never()).changeEndianness();
		Mockito.verify(wordStreamB, Mockito.times(1)).changeEndianness();
	}

	@Test(expected = InvalidBinarySPIRVInputException.class)
	public void testInvalidModule() throws IOException, InvalidBinarySPIRVInputException {
		BinaryWordStream ws = Mockito.mock(BinaryWordStream.class);
		Mockito.when(ws.getNextWord()).thenReturn(0);

		Disassembler disassembler = new Disassembler(ws, outStream, false, false);
	}

	@Test(expected = InvalidSPIRVOpcodeException.class)
	public void testWrongOpCode() throws IOException, InvalidBinarySPIRVInputException, InvalidSPIRVWordCountException, InvalidSPIRVEnumerantException, InvalidSPIRVOpcodeException, InvalidSPIRVOperandKindException {
		BinaryWordStream ws = new TesterWordStream(new int[] {3211, 4});
		Disassembler disasm = new Disassembler(ws, outStream, false, false);

		disasm.disassemble();
	}

	@Test(expected = InvalidSPIRVEnumerantException.class)
	public void testWrongEnumerant() throws IOException, InvalidBinarySPIRVInputException, InvalidSPIRVWordCountException, InvalidSPIRVEnumerantException, InvalidSPIRVOpcodeException, InvalidSPIRVOperandKindException {
		BinaryWordStream ws = new TesterWordStream(new int[]{0x00020011, 3211});
		Disassembler disasm = new Disassembler(ws, outStream, false, false);

		disasm.disassemble();
	}

	@Test(expected = InvalidSPIRVWordCountException.class)
	public void testWrongWordCount() throws IOException, InvalidBinarySPIRVInputException, InvalidSPIRVWordCountException, InvalidSPIRVEnumerantException, InvalidSPIRVOpcodeException, InvalidSPIRVOperandKindException {
		BinaryWordStream ws = new TesterWordStream(new int[] {0x00010011, 0});
		Disassembler disassembler = new Disassembler(ws, outStream, false, false);

		disassembler.disassemble();
	}
}