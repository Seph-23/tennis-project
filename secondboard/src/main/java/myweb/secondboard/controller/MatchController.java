package myweb.secondboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.dto.MatchSaveForm;
import myweb.secondboard.service.MatchService;
import myweb.secondboard.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

  private final MatchService matchService;

  @GetMapping("/home")
  public String matchHome() {
    return "match/matchHome";
  }

  @GetMapping("/matchAdd")
  public String matchAddForm(Model model) {
    model.addAttribute("form", new MatchSaveForm());
    return "match/matchAddForm";
  }

  @PostMapping("/new")
  public String matchAdd(@Validated @ModelAttribute("form") MatchSaveForm form,
                         BindingResult bindingResult, HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/match/matchAddForm";
    }

    Long matchId = matchService.addMatch(form, member);
    return "redirect:/match/home";
  }
}
