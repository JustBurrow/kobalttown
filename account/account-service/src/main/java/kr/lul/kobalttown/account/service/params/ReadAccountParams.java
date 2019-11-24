package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.UuidContext;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class ReadAccountParams {
  private UuidContext context;
  private long id;

  public ReadAccountParams(UuidContext context, long id) {
    notNull(context, "context");
    positive(id, "id");

    this.context = context;
    this.id = id;
  }

  public UuidContext getContext() {
    return this.context;
  }

  public long getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return new StringBuilder(ReadAccountParams.class.getSimpleName())
        .append("{context=").append(this.context)
        .append(", id=").append(this.id)
        .append('}')
        .toString();
  }
}
