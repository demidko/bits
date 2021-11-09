package com.github.demidko.bits;

import static java.util.BitSet.valueOf;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.BitSet;

/**
 * Класс предназначен для побитового чтения примитивов из последовательностей бит.
 */
public class BitReader {

  private final BitSet bs;
  private int pos = -1;

  public BitReader(long... n) {
    bs = valueOf(n);
  }

  public BitReader(byte... b) {
    bs = valueOf(b);
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
   * Метод читает следующие N бит по порядку
   *
   * @param size количество бит, не больше 64ех
   * @return биты сохраненные в длинное целое число (не вместившееся будут отброшены)
   */
  public long readLong(int size) {
    BitWriter w = new BitWriter();
    for (int i = 0; i < size; ++i) {
      w.write(readBit());
    }
    return w.toLong();
  }

  /**
   * @return прочитать следующее длинное целое (следующие 64 бита целиком)
   */
  public long readLong() {
    return readLong(64);
  }

  /**
   * Метод читает следующие N бит по порядку
   *
   * @param size количество бит, не больше 32ух
   * @return биты сохраненные в целое число (не вместившиеся будут отброшены)
   */
  public int readInt(int size) {
    return (int) readLong(size);
  }

  /**
   * Прочитать следующий примитив int (следующие 32 бита целиком)
   */
  public int readInt() {
    return readInt(32);
  }

  /**
   * Метод читает следующие N бит по порядку
   *
   * @param size количество бит, не больше 16
   * @return биты сохраненные в короткое целое число (не вместившиеся будут отброшены)
   */
  public short readShort(int size) {
    return (short) readLong(size);
  }

  /**
   * Прочитать следующий примитив short (следующие 16 бит целиком)
   */
  public short readShort() {
    return readShort(16);
  }

  /**
   * Метод читает следующие N бит по порядку
   *
   * @param size количество бит, не больше 8
   * @return биты сохраненные в байт (не вместившееся будут отброшены)
   */
  public byte readByte(int size) {
    return (byte) readLong(size);
  }

  /**
   * Прочитать следующий примитив byte (следующие 8 бит целиком)
   */
  public byte readByte() {
    return readByte(8);
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
  public int getTotalSize() {
    return bs.size();
  }

  /**
   * @return количество еще не прочитанных бит
   */
  public int getUnreadSize() {
    return bs.size() - pos - 1;
  }
}
