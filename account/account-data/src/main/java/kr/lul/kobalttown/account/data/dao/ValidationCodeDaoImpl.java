package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.ValidationCodeEntity;
import kr.lul.kobalttown.account.data.repository.ValidationCodeRepository;
import kr.lul.kobalttown.account.domain.ValidationCode;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
