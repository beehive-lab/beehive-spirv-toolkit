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

package uk.ac.manchester.beehivespirvtoolkit.lib;

import uk.ac.manchester.beehivespirvtoolkit.lib.disassembler.BinaryWordStream;

import java.nio.ByteOrder;

public class TesterWordStream implements BinaryWordStream {
    private final int[] words;
    private int position;

    public TesterWordStream(int[] words) {
        // Prepend header
        this.words = new int[words.length + 5];
        this.words[0] = 0x07230203;
        this.words[1] = 0x00010000;
        this.words[2] = 6;
        this.words[3] = 37;
        this.words[4] = 0;
        System.arraycopy(words, 0, this.words, 5, words.length);

        position = 0;
    }

    @Override
    public int getNextWord() {
        return words[position++];
    }

    @Override
    public void setEndianness(ByteOrder endianness) {

    }

    @Override
    public ByteOrder getEndianness() {
        return null;
    }

}
