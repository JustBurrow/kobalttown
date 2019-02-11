package kr.lul.kobalttown.configuration;

import kr.lul.kobalttown.domain.DummyPaper;
import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.loader.InMemoryVerbPathPaperLoader;
import kr.lul.kobalttown.loader.PaperLoaderDelegator;
import kr.lul.kobalttown.loader.PaperLoaderDelegatorImpl;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.format;
import static java.nio.file.Paths.get;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-10
 */
@Configuration
public class BeanConfiguration {
  private static final Logger log = getLogger(BeanConfiguration.class);

  private void generate(InMemoryVerbPathPaperLoader memory, int seed) {
    Paper lv1 = new DummyPaper(get("/sample", "" + (char) ('a' + seed)), "sample2",
        format("seed %d sample page.", seed));
    memory.addPaper(lv1);

    for (int i = ThreadLocalRandom.current().nextInt(0, 5); i >= 0; i--) {
      Path path = lv1.getPath();
      do {
        path = path.resolve(randomAlphanumeric(1, 5));
      } while (ThreadLocalRandom.current().nextBoolean());

      memory.addPaper(new DummyPaper(path, "sample3", format("seed=%d, path=\"%s\" sample page.", seed, path)));
    }
  }

  @Bean
  @Deprecated(forRemoval = true)
  public InMemoryVerbPathPaperLoader inMemoryVerbPathPaperLoader() {
    InMemoryVerbPathPaperLoader memory = new InMemoryVerbPathPaperLoader();

    DummyPaper paper;

    paper = new DummyPaper(get("/"), "basic");
    paper.setDescription("root page");
    memory.addPaper(paper);

    paper = new DummyPaper(get("/a"), "basic");
    paper.setDescription("/a paper.");
    memory.addPaper(paper);

    memory.addPaper(new DummyPaper(get("/a/aa"), "sample", "sample layout page."));
    memory.addPaper(new DummyPaper(get("/b/bb/bbb"), "sample", "without Lv.1 page."));

    for (int i = 0; i < 10; i++) {
      generate(memory, i);
    }

    if (log.isTraceEnabled()) {
      log.trace("return : {}", memory);
    }
    return memory;
  }

  @Bean
  @Deprecated(forRemoval = true)
  public PaperLoaderDelegator paperLoaderDelegator() {
    PaperLoaderDelegatorImpl delegator = new PaperLoaderDelegatorImpl();
    delegator.init(inMemoryVerbPathPaperLoader());

    if (log.isTraceEnabled()) {
      log.trace("return : {}", delegator);
    }
    return delegator;
  }
}