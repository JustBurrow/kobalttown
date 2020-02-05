package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.borderline.command.*;
import kr.lul.kobalttown.account.converter.AccountConverter;
import kr.lul.kobalttown.account.converter.EnableCodeConverter;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.domain.EnableCodeStatusException;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.dto.EnableCodeSummaryDto;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.account.service.params.*;
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
  private AccountService service;
  @Autowired
  private AccountConverter converter;
  @Autowired
  private EnableCodeConverter enableCodeConverter;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.service, "service is not autowired.");
    requireNonNull(this.converter, "converter is not autowired.");
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.borderline.AccountBorderline
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public AccountDetailDto create(final CreateAccountCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#create args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final CreateAccountParams params = new CreateAccountParams(cmd.getContext(), cmd.getNickname(),
        cmd.getEmail(), cmd.getUserKey(), cmd.getPassword(), cmd.getTimestamp());
    final Account account = this.service.create(params);
    final AccountDetailDto dto = this.converter.convert(account, AccountDetailDto.class);
    if (log.isDebugEnabled())
      log.debug("#read cmd={}, params={}, account={}, dto={}", cmd, params, account, dto);

    if (log.isTraceEnabled())
      log.trace("#create (context={} return : {}", cmd.getContext(), dto);
    return dto;
  }

  @Override
  @Transactional(noRollbackFor = EnableCodeStatusException.class)
  public AccountDetailDto enable(final EnableAccountCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#enable args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final EnableAccountParams params = new EnableAccountParams(cmd.getContext(), cmd.getValidationCode(), cmd.getTimestamp());
    if (log.isDebugEnabled())
      log.debug("#enable params={}", params);
    final Account account = this.service.enable(params);
    final AccountDetailDto dto = this.converter.convert(account, AccountDetailDto.class);

    if (log.isTraceEnabled())
      log.trace("#enable (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }

  @Override
  public AccountDetailDto read(final ReadAccountCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#read args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account account = this.service.read(
        new ReadAccountParams(cmd.getContext(), cmd.getId(), cmd.getTimestamp()));
    final AccountDetailDto dto = this.converter.convert(account, AccountDetailDto.class);
    if (log.isDebugEnabled())
      log.debug("#read cmd={}, account={}, dto={}", cmd, account, dto);

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }

  @Override
  public EnableCodeSummaryDto issue(final IssueEnableCodeCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#issue args : cmd={}", cmd);

    final IssueEnableCodeParams params = new IssueEnableCodeParams(cmd, cmd.getEmail(), cmd.getTimestamp());
    final EnableCode enableCode = this.service.issue(params);
    final EnableCodeSummaryDto dto = this.enableCodeConverter.convert(enableCode, EnableCodeSummaryDto.class);

    if (log.isTraceEnabled())
      log.trace("#issue (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }

  @Override
  public void update(final UpdatePasswordCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#update args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account user = this.service.read(new ReadAccountParams(cmd, cmd.getUser(), cmd.getTimestamp()));
    final UpdatePasswordParams params = new UpdatePasswordParams(cmd, user, cmd.getCurrent(), cmd.getPassword(), cmd.getTimestamp());
    this.service.update(params);
  }
}
