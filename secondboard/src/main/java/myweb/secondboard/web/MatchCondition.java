package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MatchCondition implements MatchConditionMapper{
  AVAILABLE("신청가능"),
  DONE("신청마감");

  @Getter
  public final String title;

}
