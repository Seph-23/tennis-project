package myweb.secondboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/abcd")
  public String abcd() {
    return "login/test";
  }
}
