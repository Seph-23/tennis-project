package myweb.secondboard.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

  @GetMapping("/")
  public String home() {
    return "home";
  }
}
