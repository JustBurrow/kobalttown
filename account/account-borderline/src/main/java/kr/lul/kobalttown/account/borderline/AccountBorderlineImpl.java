package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.IssueValidateCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.borderline.command.ValidateAccountCmd;
import kr.lul.kobalttown.account.converter.AccountConverter;
import kr.lul.kobalttown.account.converter.ValidateCodeConverter;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ValidationCode;
import kr.lul.kobalttown.account.domain.ValidationCodeStatusException;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.dto.ValidationCodeSummaryDto;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.IssueValidateParams;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import kr.lul.kobalttown.account.service.params.ValidateAccountParams;
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
  private ValidateCodeConverter validateCodeConverter;

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
  @Transactional(noRollbackFor = ValidationCodeStatusException.class)
  public AccountDetailDto validate(final ValidateAccountCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#validate args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final ValidateAccountParams params = new ValidateAccountParams(cmd.getContext(), cmd.getValidationCode(), cmd.getTimestamp());
    if (log.isDebugEnabled())
      log.debug("#validate params={}", params);
    final Account account = this.service.validate(params);
    final AccountDetailDto dto = this.converter.convert(account, AccountDetailDto.class);

    if (log.isTraceEnabled())
      log.trace("#validate (context={}) return : {}", cmd.getContext(), dto);
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
  public ValidationCodeSummaryDto issue(final IssueValidateCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#issue args : cmd={}", cmd);

    final IssueValidateParams params = new IssueValidateParams(cmd, cmd.getEmail(), cmd.getTimestamp());
    final ValidationCode validationCode = this.service.issue(params);
    final ValidationCodeSummaryDto dto = this.validateCodeConverter.convert(validationCode, ValidationCodeSummaryDto.class);

    if (log.isTraceEnabled())
      log.trace("#issue (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }
}
