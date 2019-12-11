package kr.lul.kobalttown.configuration.bean.context;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import static java.lang.String.format;
import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/11
 */
public class StrictContextServiceTest {
  private static final Logger log = getLogger(StrictContextServiceTest.class);

  private ContextService service;

  @Before
  public void setUp() throws Exception {
    this.service = new StrictContextService();
  }

  @Test
  public void test_issue() throws Exception {
    // WHEN
    Context context = this.service.issue();
    log.info("WHEN - context={}", context);

    // THEN
    assertThat(context)
        .isNotNull()
        .extracting(Context::id)
        .isNotNull();
  }

  @Test
  public void test_issue_twice() throws Exception {
    // GIVEN
    Context context = this.service.issue();
    log.info("GIVEN - context={}", context);

    // WHEN & THEN
    assertThatThrownBy(() -> this.service.issue())
        .isInstanceOf(IllegalStateException.class)
        .hasMessageStartingWith("context already exists")
        .hasMessageContaining(context.id().toString());
  }

  @Test
  public void test_get_before_issue() throws Exception {
    assertThatThrownBy(() -> this.service.get())
        .isInstanceOf(IllegalStateException.class)
        .hasMessageStartingWith("context does not exist.");
  }

  @Test
  public void test_get() throws Exception {
    // GIVEN
    Context expected = this.service.issue();
    log.info("GIVEN - expected={}", expected);

    // WHEN
    Context actual = this.service.get();
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(expected)
        .extracting(Context::id)
        .isNotNull()
        .isEqualTo(expected.id());
  }

  @Test
  public void test_clear_before_issue() throws Exception {
    assertThatThrownBy(() -> this.service.clear())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("context does not exist.");
  }

  @Test
  public void test_clear() throws Exception {
    // GIVEN
    Context context = this.service.issue();
    log.info("GIVEN - context={}", context);

    // WHEN
    boolean clear = this.service.clear();
    log.info("WHEN - clear={}", clear);

    // THEN
    assertThat(clear)
        .isTrue();
    assertThatThrownBy(() -> this.service.get())
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("context does not exist.");
  }

  @Test
  public void test_clear_and_issue() throws Exception {
    // GIVEN
    Context expected = this.service.issue();
    log.info("GIVEN - expected={}", expected);

    boolean clear = this.service.clear();
    log.info("GIVEN - clear={}", clear);

    // WHEN
    Context actual = this.service.issue();
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isNotEqualTo(expected)
        .extracting(Context::id)
        .isNotNull()
        .isNotEqualTo(expected.id());
  }

  class ContextRunnable implements Runnable {
    final Object lock;
    Context context;
    boolean clear;

    ContextRunnable(Object lock) {
      this.lock = lock;
    }

    @Override
    public void run() {
      synchronized (this.lock) {
        try {
          this.context = StrictContextServiceTest.this.service.issue();
          this.lock.wait();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      this.clear = StrictContextServiceTest.this.service.clear();
    }

    @Override
    public String toString() {
      return format("%s{context=%s, clear=%b}", ContextRunnable.class.getSimpleName(), this.context, this.clear);
    }
  }

  @Test
  public void test_issue_multi_thread() throws Exception {
    // GIVEN
    final Object lock = new Object();
    ContextRunnable cr1 = new ContextRunnable(lock);
    log.info("GIVEN - cr1={}", cr1);
    ContextRunnable cr2 = new ContextRunnable(lock);
    log.info("GIVEN - cr2={}", cr2);

    Thread t1 = new Thread(cr1);
    log.info("GIVEN - t1={}", t1);
    Thread t2 = new Thread(cr2);
    log.info("GIVEN - t2={}", t2);

    // WHEN
    t1.start();
    t2.start();
    sleep(10L);
    log.info("WHEN - t1={}, cr1={}", t1, cr1.context);
    log.info("WHEN - t2={}, cr2={}", t2, cr2.context);

    // THEN
    synchronized (lock) {
      assertThat(cr1.context)
          .isNotNull()
          .isNotEqualTo(cr2.context)
          .extracting(Context::id)
          .isNotEqualTo(cr2.context.id());
      lock.notifyAll();
    }
    sleep(10L);
    assertThat(cr1.clear)
        .isTrue();
    assertThat(cr2.clear)
        .isTrue();
  }
}
