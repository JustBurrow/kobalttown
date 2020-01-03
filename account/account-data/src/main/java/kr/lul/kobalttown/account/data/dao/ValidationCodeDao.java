package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.ValidationCode;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public interface ValidationCodeDao {
  ValidationCode create(Context context, ValidationCode validationCode);
}
