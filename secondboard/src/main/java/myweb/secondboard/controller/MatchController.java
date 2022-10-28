package myweb.secondboard.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.Player;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.dto.BoardUpdateForm;
import myweb.secondboard.dto.MatchSaveForm;
import myweb.secondboard.dto.MatchUpdateForm;
import myweb.secondboard.dto.PlayerAddForm;
import myweb.secondboard.service.MatchService;
import myweb.secondboard.service.PlayerService;
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
  private final PlayerService playerService;

  @GetMapping("/home")
  public String matchHome(
    @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
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
    model.addAttribute("form", new MatchSaveForm());
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
    model.addAttribute("form", matching);

    //TODO 매치 신청
    List<Player> players = playerService.findAllByMatchingId(matchId);
    List<Player> playersA = players.stream().filter(m -> m.getTeam().toString().equals("A"))
      .toList();
    List<Player> playersB = players.stream().filter(m -> m.getTeam().toString().equals("B"))
      .toList();

    model.addAttribute("playersA", playersA);
    model.addAttribute("playersB", playersB);
    model.addAttribute("playerAddForm", new PlayerAddForm());

    return "/match/matchInfo";
  }

  @GetMapping("/update/{matchId}")
  public String matchUpdateForm(@PathVariable("matchId") Long matchId, Model model) {

    Matching matching = matchService.findOne(matchId);

    MatchUpdateForm form = new MatchUpdateForm();
    form.setId(matching.getId());
    form.setMatchTitle(matching.getMatchTitle());
    form.setMatchDate(matching.getMatchDate());
    form.setMatchTime(matching.getMatchTime());
    form.setMatchType(matching.getMatchType());
    form.setCourtType(matching.getCourtType());
    form.setMatchPlace(matching.getMatchPlace());
    model.addAttribute("form", form);

    return "/match/matchUpdateForm";
  }

  @PostMapping("/update/{matchId}")
  public String matchUpdate(@Validated @ModelAttribute("form") MatchUpdateForm form,
    BindingResult bindingResult, HttpServletRequest request,
    @PathVariable("matchId") Long matchId) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/match/matchUpdateForm";
    }

    matchService.update(form, member);
    return "redirect:/match/info/" + matchId;
  }

  @GetMapping("/delete/{matchId}")
  public String matchDelete(@PathVariable("matchId") Long matchId) {

    matchService.deleteById(matchId);

    return "redirect:/match/home";

  }

  @PostMapping("/player/add")
  public String matchPlayerAdd(@ModelAttribute("playerAddForm") PlayerAddForm form) {

    System.out.println("form.getMatchId() = " + form.getMatchId());
    System.out.println("form.getMemberId() = " + form.getMemberId());
    System.out.println("form.getTeam() = " + form.getTeam());
    playerService.matchPlayerAdd(form);

    return "redirect:/match/home";
  }
}
