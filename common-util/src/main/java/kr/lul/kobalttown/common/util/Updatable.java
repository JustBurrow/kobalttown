package kr.lul.kobalttown.common.util;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public interface Updatable extends Creatable {
  Instant getUpdatedAt();
}