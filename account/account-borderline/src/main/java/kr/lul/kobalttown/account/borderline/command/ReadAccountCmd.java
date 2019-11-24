package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.UuidContext;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class ReadAccountCmd {
  private UuidContext context;
  private long id;

  public ReadAccountCmd(UuidContext context, long id) {
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
    return new StringBuilder(ReadAccountCmd.class.getSimpleName())
        .append("{context=").append(this.context)
        .append(", id=").append(this.id)
        .append('}')
        .toString();
  }
}
