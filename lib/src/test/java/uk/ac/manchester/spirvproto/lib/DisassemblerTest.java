package uk.ac.manchester.spirvproto.lib;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class DisassemblerTest {

	@Test
	public void testAutomaticEndiannessDetection() throws InvalidBinarySPIRVInputException, IOException {
		BinaryWordStream wordStreamL = Mockito.mock(BinaryWordStream.class);
		BinaryWordStream wordStreamB = Mockito.mock(BinaryWordStream.class);
		Mockito.when(wordStreamL.getNextWord()).thenReturn(0x07230203).thenReturn(0x00010000);
		Mockito.when(wordStreamB.getNextWord()).thenReturn(0x03022307).thenReturn(0x00010000);

		Disassembler littleE = new Disassembler(wordStreamL, new PrintStream(System.out));
		Disassembler BigE = new Disassembler(wordStreamB, new PrintStream(System.out));

		Mockito.verify(wordStreamL, Mockito.never()).changeEndianness();
		Mockito.verify(wordStreamB, Mockito.times(1)).changeEndianness();
	}
}