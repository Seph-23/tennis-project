package myweb.secondboard.web;

import lombok.Getter;

@Getter
public enum Gender {
  MALE("male"), FEMALE("female");

  private final String gender;

  Gender(String gender) {
    this.gender = gender;
  }

  @Override
  public String toString() {
    return this.gender;
  }
}
