# Bits

Java library for working with bits.

## Download

You need Gradle, or Maven, or other build tool.

[![](https://jitpack.io/v/demidko/bits.svg)](https://jitpack.io/#demidko/bits)

## Usage

```java
class Examples {

  public void readBitsExample(long bits) {
    BitReader bitReader = new BitReader(bits);
    for (int n : sourceNumbers) {
      System.out.println(bitReader.readInt());
    }
  }

  public byte[] writeBitsExample() {
    int[] sourceNumbers = new int[]{-7, 26, 0, 5435, -4, 1, 476, 0, 43, 16};
    BitWriter bitWriter = new BitWriter(sourceNumbers.length * 32);
    for (int n : sourceNumbers) {
      bitWriter.write(n, 32);
    }
    return bitWriter.toByteArray();
  }
}
```

There are also convenience methods available for various primitives.






