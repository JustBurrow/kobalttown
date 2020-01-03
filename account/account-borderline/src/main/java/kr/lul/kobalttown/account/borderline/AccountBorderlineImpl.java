package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.converter.AccountConverter;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static java.util.Objects.requireNonNull;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Service
@Transactional
class AccountBorderlineImpl implements AccountBorderline {
  private static final Logger log = getLogger(AccountBorderlineImpl.class);

  @Autowired
  private AccountService accountService;
  @Autowired
  private AccountConverter accountConverter;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.accountService, "accountService is not autowired.");
    requireNonNull(this.accountConverter, "accountConverter is not autowired.");
  }

  @Override
  public AccountDetailDto create(final CreateAccountCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#create args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final CreateAccountParams params = new CreateAccountParams(cmd.getContext(), cmd.getNickname(),
        cmd.getEmail(), cmd.getUserKey(), cmd.getPassword(), cmd.getTimestamp());
    final Account account = this.accountService.create(params);
    final AccountDetailDto dto = this.accountConverter.convert(account, AccountDetailDto.class);
    if (log.isDebugEnabled())
      log.debug("#read cmd={}, params={}, account={}, dto={}", cmd, params, account, dto);

    if (log.isTraceEnabled())
      log.trace("#create (context={} return : {}", cmd.getContext(), dto);
    return dto;
  }

  @Override
  public AccountDetailDto read(final ReadAccountCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#read args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account account = this.accountService.read(
        new ReadAccountParams(cmd.getContext(), cmd.getId(), cmd.getTimestamp()));
    final AccountDetailDto dto = this.accountConverter.convert(account, AccountDetailDto.class);
    if (log.isDebugEnabled())
      log.debug("#read cmd={}, account={}, dto={}", cmd, account, dto);

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }
}
