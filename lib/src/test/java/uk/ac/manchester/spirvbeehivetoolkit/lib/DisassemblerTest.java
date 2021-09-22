/*
 * MIT License
 *
 * Copyright (c) 2021, APT Group, Department of Computer Science,
 * The University of Manchester.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package uk.ac.manchester.spirvbeehivetoolkit.lib;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.BinaryWordStream;
import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.Disassembler;
import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.InvalidBinarySPIRVInputException;
import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.InvalidSPIRVEnumerantException;
import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.InvalidSPIRVOpcodeException;
import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.InvalidSPIRVWordCountException;
import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.SPIRVDisassemblerOptions;
import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.*;

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