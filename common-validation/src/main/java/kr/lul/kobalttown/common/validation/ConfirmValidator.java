package kr.lul.kobalttown.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public class ConfirmValidator implements ConstraintValidator<Confirm, Object> {
  private Object getFieldValue(Object obj, String name) throws Exception {
    Class<?> type = obj.getClass();

    Field field = type.getDeclaredField(name);
    boolean accessible = field.canAccess(obj);
    field.setAccessible(true);
    Object value = field.get(obj);
    field.setAccessible(accessible);

    return value;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // javax.validation.ConstraintValidator
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (null == value) {
      return false;
    }

    Confirm annotation = value.getClass().getAnnotation(Confirm.class);

    try {
      Object target = getFieldValue(value, annotation.target());
      Object confirm = getFieldValue(value, annotation.confirm());

      return Objects.equals(target, confirm);
    } catch (Exception e) {
      return false;
    }
  }
}