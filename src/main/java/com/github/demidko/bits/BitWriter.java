package com.github.demidko.bits;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.util.BitSet;

/**
 * Класс предназначен для побитной записи.
 */
public class BitWriter {

  private final BitSet bs;
  private int pos = -1;

  /**
   * По умолчанию для записи доступно 64 бита (размер примитива long)
   */
  public BitWriter() {
    this(64);
  }

  /**
   * @param size Количество бит которые будут доступны для записи.
   */
  public BitWriter(int size) {
    bs = new BitSet(size);
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
    long[] arr = bs.toLongArray();
    return arr.length == 0 ? 0 : arr[0];
  }

  /**
   * @return Набор бит (каждый байт суть блок из 8 бит). Сохранятся все записанные и незаписанные биты, вместе, по
   * порядку.
   */
  public byte[] toByteArray() {
    return bs.toByteArray();
  }

  /**
   * @return набор бит (safe copy)
   */
  public BitSet toBitSet() {
    return (BitSet) bs.clone();
  }

  /**
   * @return новый {@link BitReader}
   */
  public BitReader toBitReader() {
    return new BitReader(this);
  }

  /**
   * @return Набор бит (каждый long суть блок из 64 бит). Сохранятся все записанные и незаписанные биты, вместе, по
   * порядку.
   */
  public long[] toLongArray() {
    return bs.toLongArray();
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
   * @return 16 бит, результат записи в short (не вместившиеся биты будут отброшены)
   */
  public char toChar() {
    return (char) toLong();
  }

  /**
   * @return биты сохраненные в виде UTF-8 строки
   */
  @Override
  public String toString() {
    BitReader bytesReader = new BitReader(this);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    while (bytesReader.hasUnreadBits()) {
      outputStream.write(bytesReader.readByte());
    }
    return outputStream.toString(UTF_8);
  }

  /**
   * @return 8 бит, результат записи в byte (не вместившиеся биты будут отброшены)
   */
  public byte toByte() {
    return bs.toByteArray()[0];
  }

  /**
   * Метод записывает следующий по порядку бит
   */
  public void writeBit(boolean bit) {
    bs.set(++pos, bit);
  }

  /**
   * Метод записывает по порядку заданное количество бит
   *
   * @param r   набор бит для записи
   * @param len количество первых прочитанных бит из набора, которые будут записаны
   */
  public void writeBits(BitReader r, int len) {
    for (int i = 0; i < len; ++i) {
      writeBit(r.readBit());
    }
  }

  /**
   * Записать long побитно целиком (понадобится 64 бита)
   */
  public void writeLong(long l) {
    writeBits(new BitReader(l), 64);
  }

  /**
   * Записать int побитно целиком (понадобится 32 бита)
   */
  public void writeInt(int i) {
    writeBits(new BitReader(i), 32);
  }

  /**
   * Записать short побитно целиком (понадобится 16 бит)
   */
  public void writeShort(short s) {
    writeBits(new BitReader(s), 16);
  }

  /**
   * Записать char побитно целиком (понадобится 16 бит)
   */
  public void writeChar(char s) {
    writeBits(new BitReader(s), 16);
  }

  /**
   * Записать строку побитно целиком в UTF-8
   */
  public void writeString(String s) {
    for (byte c : s.getBytes(UTF_8)) {
      writeByte(c);
    }
  }

  /**
   * Записать byte побитно целиком (понадобится 8 бит)
   */
  public void writeByte(byte b) {
    writeBits(new BitReader(b), 8);
  }

  /**
   * @return количество всех бит, уже записанных и доступных для записи
   */
  public int size() {
    return bs.size();
  }

  /**
   * @return количество записанных бит
   */
  public int getUsedSize() {
    return pos + 1;
  }

  /**
   * @return количество доступных для записи бит
   */
  public int getFreeSize() {
    return bs.size() - pos - 1;
  }

  /**
   * @return есть ли еще свободные биты для записи?
   */
  public boolean hasFreeBits() {
    return getFreeSize() > 0;
  }
}
