package kr.lul.kobalttown.domain;

import java.util.StringJoiner;

import static java.lang.String.format;

/**
 * @author justburrow
 * @since 2019-01-05
 */
public class PaperNotFoundException extends RuntimeException {
  public static final String DEFAULT_MESSGAE_FORMAT = "paper does not found for [%s]";

  private final Papermark papermark;

  public PaperNotFoundException(Papermark papermark) {
    this(format(DEFAULT_MESSGAE_FORMAT, papermark), papermark);
  }

  public PaperNotFoundException(String message, Papermark papermark) {
    super(message);
    this.papermark = papermark;
  }

  public PaperNotFoundException(String message, Throwable cause, Papermark papermark) {
    super(message, cause);
    this.papermark = papermark;
  }

  public PaperNotFoundException(Throwable cause, Papermark papermark) {
    this(format(DEFAULT_MESSGAE_FORMAT, papermark), papermark);
  }

  public Papermark getPapermark() {
    return this.papermark;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", PaperNotFoundException.class.getSimpleName() + "[", "]")
        .add("message=" + getMessage())
        .add("papermark=" + this.papermark)
        .toString();
  }
}