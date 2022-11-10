package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MatchingStatus implements MatchingConditionMapper{

  BEFORE("경기전"),

  HOURBEFORE("한시간전"),
  ONGOING("신청마감"),
  AFTER("경기후"),

  WEEKAFTER("일주일후");

  @Getter
  private final String title;

}
