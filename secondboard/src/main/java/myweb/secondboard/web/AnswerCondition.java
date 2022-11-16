package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AnswerCondition {
  ACCEPT("접수"), COMPLETE("완료");

  @Getter
  private final String title;
}
