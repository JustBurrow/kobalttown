package kr.lul.kobalttown.document.web;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.test.NoteTestTool;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentWebTestConfiguration.class)
public class NoteControllerTest {
  protected static final Logger log = getLogger(NoteControllerTest.class);

  @Autowired
  private NoteController controller;

  @Autowired
  private NoteTestTool tool;
  @Autowired
  private AccountTestTool accountTestTool;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    this.before = this.timeProvider.now();
    log.info("SETUP - before={}", this.before);
  }
}
