package com.github.demidko.bits;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class BitWriterTest {

  @Test
  void toBitSet() {
    int[] sourceNumbers = new int[]{-7, 26, 0, 5435, -4, 1, 476, 0, 43, 16};
    BitWriter bitWriter = new BitWriter(sourceNumbers.length * 32); // 32 бита это размер одного примитива int
    for (int n : sourceNumbers) {
      bitWriter.writeInt(n);
    }
    BitReader bitReader = bitWriter.toBitReader();
    assertThat(bitReader.getSize(), equalTo(bitWriter.getSize()));
    for (int originalNumber : sourceNumbers) {
      int readiedNumber = bitReader.readInt();
      assertThat(readiedNumber, equalTo(originalNumber));
    }
  }
}