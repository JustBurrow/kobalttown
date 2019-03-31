package kr.lul.kobalttown.article.jpa.entity;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.mapping.AccountMapping;
import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.jpa.mapping.ArticleMapping.E;
import kr.lul.kobalttown.article.jpa.mapping.ArticleMapping.T;
import kr.lul.kobalttown.support.spring.jpa.entity.CreatableMappedSuperclass;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

import static java.lang.Math.min;
import static kr.lul.kobalttown.common.util.Arguments.*;
import static kr.lul.kobalttown.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-03-31
 */
@Entity(name = E.NAME)
@Table(name = T.NAME,
    indexes = @Index(name = T.FK_ARTICLE_PK_ACCOUNT, columnList = T.FK_ARTICLE_PK_ACCOUNT_COLUMNS))
public class ArticleEntity extends CreatableMappedSuperclass implements Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = T.COL_ID, nullable = false, insertable = false, updatable = false)
  private long id;
  @Column
  private String title;
  @Column
  private String summary;
  @Column
  private String body;
  @ManyToOne(targetEntity = AccountEntity.class)
  @JoinColumn(referencedColumnName = AccountMapping.T.COL_ID)
  private Account creator;

  private ArticleEntity() { // JPA only
  }

  public ArticleEntity(String title, String body, Account creator, Instant createdAt) {
    super(createdAt);

    notEmpty(title, "title");
    notEmpty(body, "body");
    notNull(creator, "creator");
    positive(creator.getId(), "creator.id");

    this.title = title;
    this.summary = body.substring(0, min(body.length(), SUMMARY_MAX_LENGTH));
    this.body = body;
    this.creator = creator;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.article.domain.Article
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public long getId() {
    return this.id;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  @Override
  public String getSummary() {
    return this.summary;
  }

  @Override
  public String getBody() {
    return this.body;
  }

  @Override
  public Account getCreator() {
    return this.creator;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (0 >= this.id || !(o instanceof ArticleEntity)) {
      return false;
    }
    return this.id == ((ArticleEntity) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ArticleEntity.class.getSimpleName() + "[", "]")
        .add("id=" + this.id)
        .add("title=" + singleQuote(this.title))
        .add("summary=" + singleQuote(this.summary))
        .add("body=" + singleQuote(this.body))
        .add("creator=" + this.creator.simpleToString())
        .toString();
  }
}