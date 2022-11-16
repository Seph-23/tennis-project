package myweb.secondboard.dto;

import lombok.Data;

@Data
public class MatchingSearchCondition {

  private String date;
  private String matchType;
  private String courtType;
  private String matchingPlace;

}
