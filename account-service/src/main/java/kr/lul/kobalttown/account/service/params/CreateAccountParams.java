package kr.lul.kobalttown.account.service.params;

import kr.lul.kobalttown.common.util.AbstractTrackingContext;

import java.time.Instant;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public class CreateAccountParams extends AbstractTrackingContext<UUID> {
  private String nickname;
  private byte[] password;

  public CreateAccountParams(UUID trackingId, Instant timestamp) {
    super(trackingId, timestamp);
  }

  public CreateAccountParams(UUID trackingId, Instant timestamp, String nickname, byte[] password) {
    super(trackingId, timestamp);

    setNickname(nickname);
    setPassword(password);
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public byte[] getPassword() {
    return this.password;
  }

  public void setPassword(byte[] password) {
    this.password = password;
  }

  public void setPassword(String password) {
    this.password = password.getBytes(UTF_8);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringBuilder(CreateAccountParams.class.getSimpleName())
        .append("nickname='").append(this.nickname).append('\'')
        .append(", password=[ PROTECTED ], ").append(super.toString())
        .append('}').toString();
  }
}