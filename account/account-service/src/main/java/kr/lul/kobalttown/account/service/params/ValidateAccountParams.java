package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/01/05
 */
public class ValidateAccountParams extends ContextContainer {
  private String validationCode;
  private Instant timestamp;

  public ValidateAccountParams(final Context context, final String validationCode, final Instant timestamp) {
    super(context);
    notEmpty(validationCode, "validationCode");
    notNull(timestamp, "timestamp");

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
    return new StringBuilder(ValidateAccountParams.class.getSimpleName())
               .append("{context=").append(this.context)
               .append(", validationCode=").append(singleQuote(this.validationCode))
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
