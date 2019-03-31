package kr.lul.kobalttown.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@Documented
@Constraint(validatedBy = ConfirmValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Confirm {
  String message() default "confirm does not match.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String target();

  String confirm() default "confirm";
}