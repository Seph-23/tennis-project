package myweb.secondboard.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Tier implements TierMapper {

  IRON("아이언"),
  BRONZE("브론즈"),
  SILVER("실버"),
  GOLD("골드"),
  PLATINUM("플래티넘"),
  DIAMOND("다이아몬드"),
  MASTER("마스터"),
  GRANDMASTER("그랜드마스터"),
  CHALLENGER("챌린저");


  @Getter
  private final String title;
}
