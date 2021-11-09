package com.github.demidko.bits;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class BitWriterTest {

  @Test
  void toByteArray() {
    int[] sourceNumbers = new int[]{-7, 26, 0, 5435, -4, 1, 476, 0, 43, 16};
    BitWriter bitWriter = new BitWriter(sourceNumbers.length * 8);
    for (int n : sourceNumbers) {
      bitWriter.writeInt(n);
    }
    byte[] bytesResult = bitWriter.toByteArray();
    BitReader bitReader = new BitReader(bytesResult);
    for (int n : sourceNumbers) {
      assertThat(bitReader.readInt(), is(equalTo(n)));
    }
  }
}