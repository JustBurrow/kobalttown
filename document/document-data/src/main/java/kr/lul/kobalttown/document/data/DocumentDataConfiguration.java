package kr.lul.kobalttown.document.data;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author justburrow
 * @since 2020/02/11
 */
@Component
@ComponentScan(basePackageClasses = {AccountDataAnchor.class})
public class DocumentDataConfiguration {
}
