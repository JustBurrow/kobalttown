package kr.lul.kobalttown.document.web;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.document.borderline.NoteBorderline;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/08
 */
@Controller
class NoteControllerImpl implements NoteController {
  protected static final Logger log = getLogger(NoteControllerImpl.class);

  @Autowired
  private NoteBorderline borderline;
  @Autowired
  private TimeProvider timeProvider;
}
