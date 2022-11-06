package myweb.secondboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

  @GetMapping("/home")
  public String home() {

    return "ranking/ranking";
  }
}
