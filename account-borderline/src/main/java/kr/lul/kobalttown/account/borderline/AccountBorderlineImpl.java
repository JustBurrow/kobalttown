package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.converter.AccountConverter;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.UsedNicknameException;
import kr.lul.kobalttown.account.dto.SimpleAccountDto;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@Service
@Transactional
class AccountBorderlineImpl implements AccountBorderline {
  private static final Logger log = getLogger(AccountBorderlineImpl.class);

  @Autowired
  private AccountService accountService;
  @Autowired
  private AccountConverter accountConverter;

  @Autowired
  private TimeProvider timeProvider;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.borderline.AccountBorderline
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public SimpleAccountDto create(CreateAccountCmd cmd) throws UsedNicknameException {
    if (log.isTraceEnabled()) {
      log.trace("args : cmd={}", cmd);
    }
    notNull(cmd, "cmd");

    CreateAccountParams params = new CreateAccountParams(UUID.randomUUID(), this.timeProvider.now(),
        cmd.getNickname(), cmd.getPassword());
    Account account = this.accountService.create(params);
    SimpleAccountDto dto = this.accountConverter.convert(account, SimpleAccountDto.class);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", dto);
    }
    return dto;
  }
}