package myweb.secondboard.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class TestController {

  @GetMapping("/test")
  public String ohora() {
    return "ohora";
  }
}
