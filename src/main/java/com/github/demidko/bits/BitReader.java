package com.github.demidko.bits;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.BitSet.valueOf;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.BitSet;

/**
 * Класс предназначен для побитного чтения.
 */
public class BitReader {

  private final BitSet bs;
  private int pos = -1;

  /**
   * @param n набор блоков для чтения по 64 бита каждый
   */
  public BitReader(long... n) {
    bs = valueOf(n);
  }

  /**
   * @param b набор блоков для чтения по 8 бит каждый
   */
  public BitReader(byte... b) {
    bs = valueOf(b);
  }

  public BitReader(BitWriter w) {
    bs = w.toBitSet();
  }

  public BitReader(BitSet b) {
    bs = b;
  }

  public BitReader(String s) {
    bs = valueOf(s.getBytes(UTF_8));
  }

  public BitReader(ByteArrayInputStream b) {
    bs = valueOf(b.readAllBytes());
  }

  public BitReader(ByteBuffer b) {
    bs = valueOf(b);
  }

  public BitReader(LongBuffer b) {
    bs = valueOf(b);
  }

  public BitReader(BitReader other) {
    bs = other.bs;
    pos = other.pos;
  }

  /**
   * @return метод читает следующий по порядку бит
   */
  public boolean readBit() {
    return bs.get(++pos);
  }

  /**
   * @return запрошенное количество бит (может быть меньше, если достаточно битов не осталось)
   */
  public BitWriter readBits(int size) {
    BitWriter w = new BitWriter(size);
    for (int i = 0; (i < size) && hasUnreadBits(); ++i) {
      w.writeBit(readBit());
    }
    return w;
  }

  /**
   * Метод читает следующие N бит по порядку
   *
   * @param size количество бит
   * @return биты сохраненные в виде unicode строки (каждый char как блок по 16 бит)
   */
  public String readString(int size) {
    return readBits(size).toString();
  }

  /**
   * Метод читает следующие N бит по порядку
   *
   * @param size количество бит
   * @return биты сохраненные в виде {@link BitSet}
   */
  public BitSet readBitSet(int size) {
    return readBits(size).toBitSet();
  }

  /**
   * Метод читает следующие N бит по порядку
   *
   * @param size количество бит
   * @return биты сохраненные в набор блоков байт по 64 бита каждый
   */
  public long[] readLongArray(int size) {
    return readBits(size).toLongArray();
  }

  /**
   * Метод читает следующие N бит по порядку
   *
   * @param size количество бит
   * @return биты сохраненные в набор блоков байт по 8 бит каждый
   */
  public byte[] readByteArray(int size) {
    return readBits(size).toByteArray();
  }

  /**
   * @return прочитать следующее длинное целое (следующие 64 бита целиком)
   */
  public long readLong() {
    return readBits(64).toLong();
  }

  /**
   * Прочитать следующий примитив int (следующие 32 бита целиком)
   */
  public int readInt() {
    return readBits(32).toInt();
  }

  /**
   * Прочитать следующий примитив short (следующие 16 бит целиком)
   */
  public short readShort() {
    return readBits(16).toShort();
  }

  /**
   * Прочитать следующий примитив short (следующие 16 бит целиком)
   */
  public char readChar() {
    return readBits(16).toChar();
  }

  /**
   * Прочитать следующий примитив byte (следующие 8 бит целиком)
   */
  public byte readByte() {
    return readBits(8).toByte();
  }

  /**
   * @return количество прочитанных бит
   */
  public int getReadiedSize() {
    return pos + 1;
  }

  /**
   * @return количество всех бит, прочитанных и не прочитанных
   */
  public int getSize() {
    return bs.size();
  }

  /**
   * @return количество еще не прочитанных бит
   */
  public int getUnreadSize() {
    return bs.size() - pos - 1;
  }

  /**
   * @return есть ли еще непрочитанные биты?
   */
  public boolean hasUnreadBits() {
    return (pos + 1) < bs.size();
  }
}
