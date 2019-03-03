package kr.lul.kobalttown.account.borderline.command;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.kobalttown.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public class CreateAccountCmd {
  private String nickname;
  private byte[] password;

  public CreateAccountCmd() {
  }

  public CreateAccountCmd(String nickname, String password) {
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
    return null == this.password
        ? null
        : Arrays.copyOf(this.password, this.password.length);
  }

  public void setPassword(String password) {
    if (null != password) {
      setPassword(password.getBytes(UTF_8));
    }
  }

  public void setPassword(byte[] password) {
    this.password = password;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringBuilder(CreateAccountCmd.class.getSimpleName())
        .append("nickname=").append(singleQuote(this.nickname))
        .append(", password=[ PROTECTED ]")
        .toString();
  }
}