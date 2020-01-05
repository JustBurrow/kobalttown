package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;

import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/01/05
 */
public class ValidateAccountCmd extends ContextContainer {
  private String validationCode;
  private Instant timestamp;

  public ValidateAccountCmd(final Context context, final String validationCode, final Instant timestamp) {
    super(context);

    this.validationCode = validationCode;
    this.timestamp = timestamp;
  }

  public String getValidationCode() {
    return this.validationCode;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public String toString() {
    return new StringBuilder(ValidateAccountCmd.class.getSimpleName())
               .append("{context=").append(this.context)
               .append(", validationCode=").append(singleQuote(this.validationCode))
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
