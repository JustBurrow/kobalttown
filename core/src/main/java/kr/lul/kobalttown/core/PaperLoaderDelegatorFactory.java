package kr.lul.kobalttown.core;

import kr.lul.common.util.IllegalConfigurationException;
import kr.lul.kobalttown.domain.PaperLoader;
import kr.lul.kobalttown.domain.PaperLoaderFactory;
import kr.lul.kobalttown.loader.PaperLoaderDelegator;
import kr.lul.kobalttown.loader.PaperLoaderDelegatorImpl;
import org.reflections.Reflections;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-16
 */
public class PaperLoaderDelegatorFactory {
  private static final Logger log = getLogger(PaperLoaderDelegatorFactory.class);

  public static final String EXTENSION_ROOT_PACKAGE = "kr.lul.kobalttown.extension";

  private List<PaperLoader> generatePaperLoaders() {
    List<PaperLoader> list = new ArrayList<>();

    for (Class<? extends PaperLoaderFactory> clz
        : new Reflections(EXTENSION_ROOT_PACKAGE).getSubTypesOf(PaperLoaderFactory.class)
    ) {
      try {
        Constructor<? extends PaperLoaderFactory> constructor = clz.getDeclaredConstructor();
        list.add(constructor.newInstance().newLoader());
      } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
        String err = format("fail to get `PaperLoader` : generator=%s, err=%s", clz.getName(), e.getMessage());
        log.error(err, e);
        throw new IllegalConfigurationException(err, e);
      }
    }

    return list;
  }

  public PaperLoaderDelegator generate() {
    PaperLoaderDelegatorImpl delegator = new PaperLoaderDelegatorImpl();
    delegator.init(generatePaperLoaders());

    return delegator;
  }
}