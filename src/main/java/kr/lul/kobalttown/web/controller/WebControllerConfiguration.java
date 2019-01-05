package kr.lul.kobalttown.web.controller;

import kr.lul.kobalttown.loader.LoaderAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-01-05
 */
@Configuration
@ComponentScan(basePackageClasses = {LoaderAnchor.class})
public class WebControllerConfiguration {
}