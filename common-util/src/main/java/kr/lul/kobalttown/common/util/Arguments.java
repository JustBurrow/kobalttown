package kr.lul.kobalttown.common.util;

import java.time.Instant;
import java.util.regex.PatternSyntaxException;

import static java.lang.String.format;

/**
 * 메서드 인자의 간단한 검증 로직.
 *
 * @author justburrow
 * @since 2018. 9. 17.
 */
public abstract class Arguments {
  private static final String DEFAULT_TARGET_NAME = "target";

  /**
   * 단정 대상의 이름을 결정한다.
   *
   * @param targetName 요청받은 단정 대상의 원래 이름.
   *
   * @return 단정 대상의 이름.
   */
  private static String name(String targetName) {
    return null == targetName || targetName.isEmpty()
        ? DEFAULT_TARGET_NAME
        : targetName;
  }

  /**
   * 단정 대상이 {@code null}이면 실패.
   *
   * @param target 단정 대상.
   *
   * @throws AssertionException 단정 대상이 {@code null}일 때.
   * @see java.util.Objects#requireNonNull(Object)
   */
  public static void notNull(Object target) throws AssertionException {
    if (null == target) {
      throw new AssertionException(DEFAULT_TARGET_NAME + " is null.");
    }
  }

  /**
   * 단정 대상이 {@code null}이면 실패.
   *
   * @param target     단정 대상.
   * @param targetName 단정 대상의 이름.
   *
   * @throws AssertionException 단정 대상이 {@code null}일 때.
   * @see java.util.Objects#requireNonNull(Object, String)
   */
  public static void notNull(Object target, String targetName) throws AssertionException {
    if (null == target) {
      throw new AssertionException(name(targetName) + " is null.");
    }
  }

  /**
   * 단정 대상이 0 보다 작거나 같으면 실패.
   *
   * @param target 단정 대상.
   *
   * @throws AssertionException 단정 대상이 0보다 작거나 같을 때.
   */
  public static void positive(int target) throws AssertionException {
    if (0 >= target) {
      throw new AssertionException(format("%s is not positive : %d", DEFAULT_TARGET_NAME, target));
    }
  }

  /**
   * 단정 대상이 0 보다 작거나 같으면 실패.
   *
   * @param target     단정 대상.
   * @param targetName 단정 대상의 이름.
   *
   * @throws AssertionException 단정 대상이 0보다 작거나 같을 때.
   */
  public static void positive(int target, String targetName) throws AssertionException {
    if (0 >= target) {
      throw new AssertionException(format("%s is not positive : %d", name(targetName), target));
    }
  }

  /**
   * 단정 대상이 0 보다 작거나 같으면 실패.
   *
   * @param target 단정 대상.
   *
   * @throws AssertionException 단정 대상이 0보다 작거나 같을 때.
   */
  public static void positive(long target) throws AssertionException {
    if (0 >= target) {
      throw new AssertionException(format("%s is not positive : %d", DEFAULT_TARGET_NAME, target));
    }
  }

  /**
   * 단정 대상이 0 보다 작거나 같으면 실패.
   *
   * @param target     단정 대상.
   * @param targetName 단정 대상의 이름.
   *
   * @throws AssertionException 단정 대상이 0보다 작거나 같을 때.
   */
  public static void positive(long target, String targetName) throws AssertionException {
    if (0 >= target) {
      throw new AssertionException(format("%s is not positive : %d", name(targetName), target));
    }
  }

  /**
   * 단정 대상이 0 보다 작으면 실패.
   *
   * @param target 단정 대상.
   *
   * @throws AssertionException 단정 대상이 0보다 작을 때.
   */
  public static void notNegative(int target) throws AssertionException {
    if (0 > target) {
      throw new AssertionException(format("%s is negative : %d", DEFAULT_TARGET_NAME, target));
    }
  }

  /**
   * 단정 대상이 0 보다 작으면 실패.
   *
   * @param target     단정 대상.
   * @param targetName 단정 대상의 이름.
   *
   * @throws AssertionException 단정 대상이 0보다 작을 때.
   */
  public static void notNegative(int target, String targetName) throws AssertionException {
    if (0 > target) {
      throw new AssertionException(format("%s is negative : %d", name(targetName), target));
    }
  }

  /**
   * 단정 대상이 0 보다 작으면 실패.
   *
   * @param target 단정 대상.
   *
   * @throws AssertionException 단정 대상이 0보다 작을 때.
   */
  public static void notNegative(long target) throws AssertionException {
    if (0 > target) {
      throw new AssertionException(format("%s is negative : %d", DEFAULT_TARGET_NAME, target));
    }
  }

  /**
   * 단정 대상이 0 보다 작으면 실패.
   *
   * @param target     단정 대상.
   * @param targetName 단정 대상의 이름.
   *
   * @throws AssertionException 단정 대상이 0보다 작을 때.
   */
  public static void notNegative(long target, String targetName) throws AssertionException {
    if (0 > target) {
      throw new AssertionException(format("%s is negative : %d", name(targetName), target));
    }
  }

  /**
   * 대상이 0 보다 크면 실패.
   *
   * @param target 대상.
   *
   * @throws AssertionException 대상이 0보다 클 때.
   */
  public static void notPositive(int target) throws AssertionException {
    if (0 < target) {
      throw new AssertionException(format("%s is positive : %d", DEFAULT_TARGET_NAME, target));
    }
  }

  /**
   * 대상이 0 보다 크면 실패.
   *
   * @param target     대상.
   * @param targetName 대상의 이름.
   *
   * @throws AssertionException 대상이 0보다 클 때.
   */
  public static void notPositive(int target, String targetName) throws AssertionException {
    if (0 < target) {
      throw new AssertionException(format("%s is positive : %d", name(targetName), target));
    }
  }

  /**
   * 대상이 0 보다 크면 실패.
   *
   * @param target 대상.
   *
   * @throws AssertionException 대상이 0보다 클 때.
   */
  public static void notPositive(long target) throws AssertionException {
    if (0 < target) {
      throw new AssertionException(format("%s is positive : %d", DEFAULT_TARGET_NAME, target));
    }
  }

  /**
   * 대상이 0 보다 크면 실패.
   *
   * @param target     대상.
   * @param targetName 대상의 이름.
   *
   * @throws AssertionException 대상이 0보다 클 때.
   */
  public static void notPositive(long target, String targetName) throws AssertionException {
    if (0 < target) {
      throw new AssertionException(format("%s is positive : %d", name(targetName), target));
    }
  }

  /**
   * 단정 대상이 {@code null} 이거나 빈 문자열이면 실패.
   *
   * @param target 단정 대상.
   *
   * @throws AssertionException 단정 대상이 {@code null} 이거나 빈 문자열일 때.
   */
  public static void notEmpty(String target) throws AssertionException {
    if (null == target) {
      throw new AssertionException(DEFAULT_TARGET_NAME + " is null.");
    } else if (target.isEmpty()) {
      throw new AssertionException(DEFAULT_TARGET_NAME + " is empty.");
    }
  }

  /**
   * 단정 대상이 {@code null} 이거나 빈 문자열이면 실패.
   *
   * @param target     단정 대상.
   * @param targetName 단정 대상의 이름.
   *
   * @throws AssertionException 단정 대상이 {@code null} 이거나 빈 문자열일 때.
   */
  public static void notEmpty(String target, String targetName) throws AssertionException {
    if (null == target) {
      throw new AssertionException(name(targetName) + " is null.");
    } else if (target.isEmpty()) {
      throw new AssertionException(name(targetName) + " is empty.");
    }
  }

  /**
   * 단정 대상 문자열이 지정한 패턴이 아니면 실패.
   *
   * @param target  단정 대상.
   * @param pattern 비교할 정규 표현식.
   *
   * @throws AssertionException 단정 대상 문자열이 지정한 패턴이 아닐 때.
   * @see String#matches(String)
   */
  public static void matches(String target, String pattern) throws AssertionException {
    if (null == target) {
      throw new AssertionException(DEFAULT_TARGET_NAME + " is null.");
    }
    if (null == pattern) {
      throw new AssertionException("pattern is null.");
    }

    try {
      if (!target.matches(pattern)) {
        throw new AssertionException(format("%s does not match : pattern='%s', target='%s'",
            DEFAULT_TARGET_NAME, pattern, target));
      }
    } catch (PatternSyntaxException e) {
      throw new AssertionException(format("illegal pattern : pattern='%s'", pattern), e);
    }
  }

  /**
   * 단정 대상 문자열이 지정한 패턴이 아니면 실패.
   *
   * @param target     단정 대상.
   * @param pattern    비교할 정규 표현식.
   * @param targetName 단정 대상의 이름.
   *
   * @throws AssertionException 단정 대상 문자열이 지정한 패턴이 아닐 때.
   * @see String#matches(String)
   */
  public static void matches(String target, String pattern, String targetName) throws AssertionException {
    if (null == target) {
      throw new AssertionException(name(targetName) + " is null.");
    }
    if (null == pattern) {
      throw new AssertionException("pattern is null.");
    }

    try {
      if (!target.matches(pattern)) {
        throw new AssertionException(format("%s does not match : pattern='%s', target='%s'",
            name(targetName), pattern, target));
      }
    } catch (PatternSyntaxException e) {
      throw new AssertionException(format("illegal pattern : pattern='%s'", pattern), e);
    }
  }

  /**
   * {@code after or equal to}
   *
   * @param target
   * @param comp
   */
  public static void ae(Instant target, Instant comp) {
    ae(target, comp, DEFAULT_TARGET_NAME);
  }

  /**
   * {@code after or equal to}
   *
   * @param target
   * @param comp
   * @param targetName
   */
  public static void ae(Instant target, Instant comp, String targetName) {
    if (null == target) {
      throw new AssertionException(name(targetName) + " is null.");
    }
    if (null == comp) {
      throw new AssertionException("comp is null.");
    }

    if (target.isBefore(comp)) {
      throw new AssertionException(format("%s is not after or equal to compare : compare=%s, target=%s",
          name(targetName), comp, target));
    }
  }

  /**
   * 대상의 클래스를 검사한다.
   *
   * @param target 검사 대상.
   * @param clz    기대하는 클래스.
   */
  public static void typeOf(Object target, Class clz) {
    typeOf(target, clz, null);
  }

  /**
   * 대상의 클래스를 검사한다.
   *
   * @param target     검사 대상.
   * @param clz        기대하는 클래스.
   * @param targetName 검사 대상의 이름.
   */
  public static void typeOf(Object target, Class clz, String targetName) {
    if (null == clz) {
      throw new AssertionException("clz is null.");
    } else if (null == target || target.getClass().equals(clz)) {
      return;
    }

    throw new AssertionException(format("%s is not instance of %s", name(targetName), clz.getName()));
  }

  private Arguments() {
    throw new UnsupportedOperationException();
  }
}
