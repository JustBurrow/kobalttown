package kr.lul.kobalttown.web;

import kr.lul.kobalttown.configuration.ConfigurationAnchor;
import kr.lul.kobalttown.runner.KobalttownRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author justburrow
 * @since 2019-01-05
 */
@SpringBootApplication(scanBasePackageClasses = {KobalttownRunner.class, ConfigurationAnchor.class})
public class WebTestConfiguration {
}