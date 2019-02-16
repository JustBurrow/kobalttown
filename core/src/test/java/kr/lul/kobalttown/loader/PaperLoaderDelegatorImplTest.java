package kr.lul.kobalttown.loader;

import kr.lul.common.util.AssertionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaperLoaderTestConfiguration.class)
public class PaperLoaderDelegatorImplTest {
  private static final Logger log = getLogger(PaperLoaderDelegatorImplTest.class);

  @Autowired
  private PaperLoaderDelegator delegator;

  @Before
  public void setUp() throws Exception {
    assertThat(this.delegator).isNotNull();

    log.info("SETUP - delegator={}", this.delegator);
  }

  @Test
  public void test_isSupported_with_null() throws Exception {
    assertThatThrownBy(() -> this.delegator.load(null))
        .isInstanceOf(AssertionException.class);
  }
}