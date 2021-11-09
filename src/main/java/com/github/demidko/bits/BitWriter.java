package com.github.demidko.bits;

import static java.nio.ByteBuffer.allocate;
import static java.util.BitSet.valueOf;

import java.util.BitSet;

/**
 * Класс предназначен для записи бит с последующей конвертацией в примитивы.
 */
class BitWriter {

  private final BitSet bs;
  private int pos = -1;

  /**
   * По умолчанию для записи доступно 64 бита (размер примитива long)
   */
  public BitWriter() {
    this(64);
  }

  /**
   * @param size количество бит которые будут доступны для записи
   */
  public BitWriter(int size) {
    bs = valueOf(allocate(size / 8 + 1));
  }

  /**
   * @param other этот экземпляр будет скопирован
   */
  public BitWriter(BitWriter other) {
    bs = (BitSet) other.bs.clone();
    pos = other.pos;
  }

  /**
   * @return 64 бита, результат записи в long (не вместившиеся биты будут отброшены)
   */
  public long toLong() {
    long[] numbers = bs.toLongArray();
    return numbers.length == 0 ? 0 : numbers[0];
  }

  /**
   * @return Набор байт (каждый байт суть блок из 8 бит). Сохранятся все записанные и незаписанные биты, вместе, по
   * порядку.
   */
  public byte[] toByteArray() {
    return bs.toByteArray();
  }

  /**
   * @return 32 бита, результат записи в int (не вместившиеся биты будут отброшены)
   */
  public int toInt() {
    return (int) toLong();
  }

  /**
   * @return 16 бит, результат записи в short (не вместившиеся биты будут отброшены)
   */
  public short toShort() {
    return (short) toLong();
  }

  /**
   * @return 8 бит, результат записи в byte (не вместившиеся биты будут отброшены)
   */
  public byte toByte() {
    return (byte) toLong();
  }

  /**
   * @return количество всех бит, уже записанных и доступных для записи
   */
  public int getTotalSize() {
    return bs.size();
  }

  /**
   * @return количество записанных бит
   */
  public int getWrittenSize() {
    return pos + 1;
  }

  /**
   * @return количество доступных для записи бит
   */
  public int getFreeSize() {
    return bs.size() - pos - 1;
  }

  /**
   * Метод записывает следующий по порядку бит
   */
  public void write(boolean bit) {
    bs.set(++pos, bit);
  }

  /**
   * Метод записывает по порядку заданное количество бит
   *
   * @param bits набор из 64-ех бит
   * @param len  количество первых бит из набора, которые будут записаны
   */
  public void write(long bits, int len) {
    BitReader r = new BitReader(bits);
    for (int i = 0; i < len; ++i) {
      write(r.readBit());
    }
  }

  /**
   * Записать long побитно (понадобится 64 бита)
   */
  public void writeLong(long l) {
    write(l, 64);
  }

  /**
   * Записать int побитно (понадобится 32 бита)
   */
  public void writeInt(int i) {
    write(i, 32);
  }

  /**
   * Записать short побитно (понадобится 16 бит)
   */
  public void writeShort(short s) {
    write(s, 16);
  }

  /**
   * Записать byte побитно (понадобится 8 бит)
   */
  public void writeByte(byte b) {
    write(b, 8);
  }
}
