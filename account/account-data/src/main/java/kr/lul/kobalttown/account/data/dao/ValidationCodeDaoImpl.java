package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.ValidationCodeEntity;
import kr.lul.kobalttown.account.data.repository.ValidationCodeRepository;
import kr.lul.kobalttown.account.domain.ValidationCode;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.unmodifiableList;
import static kr.lul.common.util.Arguments.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@Service
class ValidationCodeDaoImpl implements ValidationCodeDao {
  private static final Logger log = getLogger(ValidationCodeDaoImpl.class);

  @Autowired
  private ValidationCodeRepository repository;

  @Override
  public ValidationCode create(final Context context, final ValidationCode validationCode) {
    notNull(context, "context");
    notNull(validationCode, "validationCode");
    typeOf(validationCode, ValidationCodeEntity.class, "validationCode");
    notPositive(validationCode.getId(), "validationCode.id");

    final ValidationCodeEntity saved = this.repository.save((ValidationCodeEntity) validationCode);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, saved);
    return saved;
  }

  @Override
  public ValidationCode read(final Context context, final String code) {
    if (log.isTraceEnabled())
      log.trace("#read args : context={}, code={}", context, code);
    notNull(context, "context");
    notEmpty(code, "code");

    final ValidationCodeEntity validationCode = this.repository.findOneByCode(code);

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", context, validationCode);
    return validationCode;
  }

  @Override
  public boolean exists(final Context context, final String code) {
    if (log.isTraceEnabled())
      log.trace("#exists args : context={}, code={}", context, code);
    notNull(context, "context");

    final boolean exists = null != code &&
                               !code.isEmpty() &&
                               this.repository.existsByCode(code);

    if (log.isTraceEnabled())
      log.trace("#exists (context={}) return : {}", context, exists);
    return exists;
  }

  @Override
  public List<ValidationCode> list(final Context context, final String email) {
    if (log.isTraceEnabled())
      log.trace("#list args : context={}, email={}", context, email);
    notEmpty(email, "email");

    final List<ValidationCode> codes = unmodifiableList(this.repository.findAllByEmail(email));

    if (log.isTraceEnabled())
      log.trace("#list (context={}) return : {}", context, codes);
    return codes;
  }
}
