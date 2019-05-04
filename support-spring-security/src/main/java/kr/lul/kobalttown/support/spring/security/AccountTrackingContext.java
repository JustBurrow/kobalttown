package kr.lul.kobalttown.support.spring.security;

import kr.lul.kobalttown.common.util.AbstractTrackingContext;

import java.time.Instant;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static kr.lul.kobalttown.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2019-05-04
 */
public class AccountTrackingContext extends AbstractTrackingContext<UUID> {
  protected int account;

  public AccountTrackingContext(Instant timestamp, int account) {
    this(randomUUID(), timestamp, account);
  }

  public AccountTrackingContext(Instant timestamp, AccountDetails account) {
    this(randomUUID(), timestamp, account.getId());
  }

  public AccountTrackingContext(UUID trackingId, Instant timestamp, AccountDetails account) {
    this(trackingId, timestamp, account.getId());
  }

  public AccountTrackingContext(UUID trackingId, Instant timestamp, int account) {
    super(trackingId, timestamp);
    positive(account, "account");
    this.account = account;
  }

  public int getAccount() {
    return this.account;
  }
}