package kr.lul.kobalttown.web.controller;

import kr.lul.common.util.Enums;
import kr.lul.kobalttown.domain.BasicPaper;
import kr.lul.kobalttown.domain.GenericPaperLoader;
import kr.lul.kobalttown.web.WebTestConfiguration;
import kr.lul.kobalttown.web.context.RequestContext;
import kr.lul.kobalttown.web.context.Verb;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
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
  private GenericPaperLoader genericPaperLoader;

  @Before
  public void setUp() throws Exception {
    assertThat(this.frontController).isNotNull();
    assertThat(this.genericPaperLoader).isNotNull();

    log.info("SETUP - frontController={}, genericPaperLoader={}", this.frontController, this.genericPaperLoader);
  }

  @Test
  public void test_handle_for_paper_theme() throws Exception {
    // GIVEN
    RequestContext requestContext = new TestRequestContext(Enums.random(Verb.class), Paths.get("a", "b", "c"));

    BasicPaper paper = new BasicPaper();
    final String theme = "aaa";
    paper.setTheme(theme);
    Mockito.when(this.genericPaperLoader.load(Mockito.any())).thenReturn(paper);

    log.info("GIVEN - requestContext={}", requestContext);
    log.info("GIVEN - paper={}", paper);

    // WHEN
    this.frontController.handle(requestContext);

    // THEN
    assertThat(requestContext.getViewname())
        .contains(theme);
  }
}