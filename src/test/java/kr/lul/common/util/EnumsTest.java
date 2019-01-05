package kr.lul.common.util;

import org.junit.Test;
import org.slf4j.Logger;

import static kr.lul.common.util.Enums.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-05
 */
public class EnumsTest {
  private static final Logger log = getLogger(EnumsTest.class);
  private static final int SMALL_LOOP_SIZE = 1000;

  @Test
  public void test_random_with_null() throws Exception {
    assertThatThrownBy(() -> random(null))
        .isNotNull()
        .isInstanceOf(AssertionException.class)
        .hasMessage("clz is null.");
  }

  private enum TestEmptyEnum {}

  @Test
  public void test_random_with_empty_enum() throws Exception {
    assertThatThrownBy(() -> random(TestEmptyEnum.class))
        .isNotNull()
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("enum class has no constant");
  }

  private enum TestSingleEnum {
    CONSTNAT;
  }

  @Test
  public void test_random_with_single_enum() throws Exception {
    for (int i = 0; i < SMALL_LOOP_SIZE; i++) {
      assertThat(random(TestSingleEnum.class))
          .isSameAs(TestSingleEnum.CONSTNAT);
    }
  }

  private enum TestMultiEnum {
    CONSTANT1,
    CONSTANT2,
    CONSTANT3,
    CONSTANT4;
  }

  @Test
  public void test_random_with_multi_enum() throws Exception {
    for (int i = 0; i < SMALL_LOOP_SIZE; i++) {
      // WHEN
      TestMultiEnum actual = random(TestMultiEnum.class);
      log.info("WHEN - actual={}", actual);

      // THEN
      assertThat(actual)
          .isNotNull()
          .isIn(TestMultiEnum.CONSTANT1, TestMultiEnum.CONSTANT2, TestMultiEnum.CONSTANT3, TestMultiEnum.CONSTANT4);
    }
  }
}