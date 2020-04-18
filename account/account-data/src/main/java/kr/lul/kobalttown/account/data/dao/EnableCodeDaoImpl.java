package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.EnableCodeEntity;
import kr.lul.kobalttown.account.data.repository.EnableCodeRepository;
import kr.lul.kobalttown.account.domain.EnableCode;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static kr.lul.common.util.Arguments.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@Service
class EnableCodeDaoImpl implements EnableCodeDao {
  private static final Logger log = getLogger(EnableCodeDaoImpl.class);

  @Autowired
  private EnableCodeRepository repository;

  @Override
  public EnableCode create(final Context context, final EnableCode code) {
    notNull(context, "context");
    notNull(code, "code");
    typeOf(code, EnableCodeEntity.class, "code");
    notPositive(code.getId(), "code.id");

    // TODO 유효한 코드가 있는지 확인.
    // TODO 토큰 중복 확인.

    final EnableCodeEntity saved = this.repository.save((EnableCodeEntity) code);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, saved);
    return saved;
  }

  @Override
  public EnableCode read(final Context context, final String token) {
    if (log.isTraceEnabled())
      log.trace("#read args : context={}, token={}", context, token);
    notNull(context, "context");
    notEmpty(token, "token");

    final EnableCodeEntity code = this.repository.findOneByToken(token);

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", context, code);
    return code;
  }

  @Override
  public boolean exists(final Context context, final String token) {
    if (log.isTraceEnabled())
      log.trace("#exists args : context={}, token={}", context, token);
    notNull(context, "context");
    notEmpty(token, "token");

    final boolean exists = this.repository.existsByToken(token);

    if (log.isTraceEnabled())
      log.trace("#exists (context={}) return : {}", context, exists);
    return exists;
  }

  @Override
  public List<EnableCode> list(final Context context, final String email) {
    if (log.isTraceEnabled())
      log.trace("#list args : context={}, email={}", context, email);
    notEmpty(email, "email");

    final List<EnableCode> codes = new ArrayList<>(this.repository.findAllByEmail(email));

    if (log.isTraceEnabled())
      log.trace("#list (context={}) return : {}", context, codes);
    return codes;
  }
}
