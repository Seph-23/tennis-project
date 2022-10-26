package myweb.secondboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.dto.MatchSaveForm;
import myweb.secondboard.service.MatchService;
import myweb.secondboard.web.SessionConst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

  private final MatchService matchService;

  @GetMapping("/home")
  public String matchHome(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable, Model model) {

    Page<Matching> matchList = matchService.getMatchList(pageable);
    int nowPage = matchList.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, matchList.getTotalPages());

    model.addAttribute("matchList", matchList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    return "/match/matchHome";
  }

  @GetMapping("/matchAdd")
  public String matchAddForm(Model model) {
    model.addAttribute("match", new MatchSaveForm());
    return "/match/matchAddForm";
  }

  @PostMapping("/new")
  public String matchAdd(@Validated @ModelAttribute("match") MatchSaveForm form,
                         BindingResult bindingResult, HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/match/matchAddForm";
    }

    Long matchId = matchService.addMatch(form, member);
    return "redirect:/match/home/";
  }

  @GetMapping("/info/{matchId}")
  public String matchDetail(@PathVariable("matchId") Long matchId, Model model) {

    Matching matching = matchService.findOne(matchId);
    model.addAttribute("match", matching);
    return "/match/matchInfo";
  }


}
