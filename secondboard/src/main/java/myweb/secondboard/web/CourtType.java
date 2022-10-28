package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CourtType implements CourtMapperType{
  INDOOR("실내"),
  OUTDOOR("실외");

  @Getter
  private final String title;


}
