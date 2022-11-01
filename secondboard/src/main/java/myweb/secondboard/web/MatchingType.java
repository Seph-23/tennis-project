package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MatchingType implements MatchingMapperType{
  SINGLE("단식", "2"),
  DOUBLE("복식", "4");

  @Getter
  private final String title;

  @Getter
  private final String code;

}
