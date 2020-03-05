package kr.lul.kobalttown.document.domain;

import java.util.List;

import static java.util.Collections.unmodifiableList;
import static kr.lul.common.util.Arguments.*;

/**
 * @author justburrow
 * @since 2020/03/05
 */
public class HistoryImpl<S extends Snapshot> implements History<S> {
  private int maxSize;
  private int page;
  private long totalSize;
  private List<S> content;

  public HistoryImpl(final int maxSize, final int page, final long totalSize, final List<S> content) {
    positive(maxSize, "maxSize");
    notNegative(page, "page");
    notNegative(totalSize, "totalSize");
    notNull(content, "content");
    lt(content.size(), maxSize, "content.size");

    this.maxSize = maxSize;
    this.page = page;
    this.totalSize = totalSize;
    this.content = unmodifiableList(content);
  }

  @Override
  public int maxSize() {
    return this.maxSize;
  }

  @Override
  public int page() {
    return this.page;
  }

  @Override
  public long totalSize() {
    return this.totalSize;
  }

  @Override
  public List<S> content() {
    return this.content;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{maxSize=").append(this.maxSize)
               .append(", page=").append(this.page)
               .append(", totalSize=").append(this.totalSize)
               .append(", content=").append(this.content)
               .append('}').toString();
  }
}
