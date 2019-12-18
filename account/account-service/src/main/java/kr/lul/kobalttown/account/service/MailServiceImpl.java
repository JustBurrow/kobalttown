package kr.lul.kobalttown.account.service;

import kr.lul.common.data.Context;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/18
 */
@Service
class MailServiceImpl implements MailService {
  private static final Logger log = getLogger(MailServiceImpl.class);

  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private TemplateEngine templateEngine;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.mailSender, "mailSender is not autowired.");
    requireNonNull(this.templateEngine, "templateEngine is not autowired.");
  }

  private String buildMessage(final String template, final Map<String, Object> params) {
    final org.thymeleaf.context.Context ctx = new org.thymeleaf.context.Context();
    params.forEach((name, value) -> {
      ctx.setVariable(name, value);
    });
    final String message = this.templateEngine.process(template, ctx);

    if (log.isTraceEnabled())
      log.trace("#buildMessage return : {}", message);
    return message;
  }

  @Override
  public void send(final Context context, final String from, final String to, final String title,
      final String template, final Map<String, Object> params) {
    if (log.isTraceEnabled())
      log.trace("#send args : context={}, from={}, to={}, title={}, template={}, params={}",
          context, from, to, title, template, params);
    notNull(context, "context");
    notEmpty(from, "from");
    notEmpty(to, "to");
    notEmpty(title, "title");
    notEmpty(template, "template");
    notNull(params, "params");

    final MimeMessagePreparator preparator = mimeMessage -> {
      final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(title);
      helper.setText(buildMessage(template, params), true);
    };

    try {
      final Instant before = Instant.now();
      this.mailSender.send(preparator);
      final Duration mailResponseTime = Duration.between(before, Instant.now());
      if (log.isInfoEnabled())
        log.info("#send (context={}) mailResponseTime={}", context, mailResponseTime);
    } catch (final MailException e) {
      log.warn("#send (context=" + context + ") fail to send email.", e);
      throw e;
    }
  }
}
