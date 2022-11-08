package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MatchingStatus implements MatchingConditionMapper{

  BEFORE("경기전"),
  ONGOING("신청마감"),
  AFTER("경기후");

  @Getter
  private final String title;

}
