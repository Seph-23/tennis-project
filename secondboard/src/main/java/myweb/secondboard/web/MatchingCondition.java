package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MatchingCondition implements MatchingConditionMapper{

  AVAILABLE("신청가능"),
  DONE("신청마감"),

  FAIL("매칭실패");

  @Getter
  private final String title;

}
