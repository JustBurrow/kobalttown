package kr.lul.kobalttown.web.controller;

import kr.lul.common.util.Enums;
import kr.lul.kobalttown.domain.DummyPaper;
import kr.lul.kobalttown.loader.PaperLoaderDelegator;
import kr.lul.kobalttown.web.WebTestConfiguration;
import kr.lul.kobalttown.web.context.RequestContext;
import kr.lul.kobalttown.web.context.Verb;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfiguration.class)
public class FrontControllerImplTest {
  private static final Logger log = getLogger(FrontControllerImplTest.class);

  @Autowired
  private FrontController frontController;

  @MockBean
  private PaperLoaderDelegator paperLoaderDelegator;

  @Before
  public void setUp() throws Exception {
    assertThat(this.frontController).isNotNull();
    assertThat(this.paperLoaderDelegator).isNotNull();

    log.info("SETUP - frontController={}, basicPapermarkLoader={}", this.frontController, this.paperLoaderDelegator);
  }

  @Test
  public void test_handle_for_paper_theme() throws Exception {
    // GIVEN
    RequestContext requestContext = new TestRequestContext(Enums.random(Verb.class), Paths.get("/a", "b", "c"));

    final String theme = "aaa";
    DummyPaper paper = new DummyPaper(Paths.get("/a"), theme);
    when(this.paperLoaderDelegator.isSupported(any()))
        .thenReturn(true);
    when(this.paperLoaderDelegator.load(any()))
        .thenReturn(paper);

    log.info("GIVEN - requestContext={}", requestContext);
    log.info("GIVEN - paper={}", paper);

    // WHEN
    this.frontController.handle(requestContext);

    // THEN
    assertThat(requestContext.getViewname())
        .contains(theme);
  }
}