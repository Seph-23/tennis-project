package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GameResult implements GameResultMapper{
  WIN("승리"), LOSE("패배"), NORECORD("결과미등록"), PENALTY("무효");

  @Getter
  private final String title;

}
