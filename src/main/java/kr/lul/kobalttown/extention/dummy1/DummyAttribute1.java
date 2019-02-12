package kr.lul.kobalttown.extention.dummy1;

import java.util.StringJoiner;

/**
 * @author justburrow
 * @since 2019-02-11
 */
public class DummyAttribute1 {
  private int i;

  public DummyAttribute1() {
  }

  public DummyAttribute1(int i) {
    setI(i);
  }

  public int getI() {
    return this.i;
  }

  public void setI(int i) {
    this.i = i;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DummyAttribute1.class.getSimpleName() + "[", "]")
        .add("i=" + this.i)
        .toString();
  }
}