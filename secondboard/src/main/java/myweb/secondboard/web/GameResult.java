package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GameResult implements GameResultMapper{
  WIN("승리"), LOSE("패배");

  @Getter
  private final String title;

}
