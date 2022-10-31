package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status implements StatusMapperType{
  RECRUITING("모집 중"),
  END("모집 마감");

  @Getter
  private final String title;
}