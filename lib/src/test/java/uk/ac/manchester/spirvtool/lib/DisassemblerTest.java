package uk.ac.manchester.spirvtool.lib;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import uk.ac.manchester.spirvtool.lib.Disassembler;

public class DisassemblerTest {

	@Test
	public void testToString() {
		Disassembler sut = new Disassembler("test");

		assertEquals("Disassembler - test", sut.toString());
	}
}