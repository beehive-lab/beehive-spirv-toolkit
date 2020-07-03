package uk.ac.manchester.spirvtool.lib;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class DisassemblerTest {

	@Test
	public void testMagicNumberInHeader() throws InvalidBinarySPIRVInputException {
		BinaryWordStream wordStream = Mockito.mock(BinaryWordStream.class);
		Mockito.when(wordStream.getNextWord()).thenReturn(0x07230203).thenReturn(0x03022307);

		Disassembler littleE = new Disassembler(wordStream);

		assertEquals(littleE.getHeader().magicNumber, 0x07230203);
	}
}