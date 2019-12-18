package kr.lul.kobalttown.account.service;

import kr.lul.common.data.Context;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public interface MailService {
  @Async
  void send(Context context, String from, String to, String title, String template, Map<String, Object> params);
}
