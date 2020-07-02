package uk.ac.manchester.spirvtool.lib;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class DisassemblerTest {

	@Test
	public void testToString() {
		BinaryWordStream wordStream = Mockito.mock(BinaryWordStream.class);
		Disassembler sut = new Disassembler(wordStream);

		assertEquals("SPIR-V Disassembler", sut.toString());
	}
}