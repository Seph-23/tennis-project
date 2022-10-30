package myweb.secondboard.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.dto.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("loginForm", new LoginForm()); //Modal창 용
    return "home";
  }
}
