package myweb.secondboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.Player;
import myweb.secondboard.dto.MatchingSaveForm;
import myweb.secondboard.dto.MatchingUpdateForm;
import myweb.secondboard.dto.PlayerAddForm;
import myweb.secondboard.service.MatchingService;
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
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/matching")
@RequiredArgsConstructor
public class MatchingController {

  private final MatchingService matchingService;

  private final PlayerService playerService;

  @GetMapping("/home")
  public String home(@PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC)
                     Pageable pageable, Model model) {
    Page<Matching> matchingList = matchingService.getMatchingList(pageable);
    int nowPage = matchingList.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, matchingList.getTotalPages());

    model.addAttribute("matchingList", matchingList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    return "/matching/matchingHome";
  }

  @GetMapping("/matchingAdd")
  public String matchingAddForm(Model model) {
    model.addAttribute("matching", new MatchingSaveForm());
    return "/matching/matchingAddForm";
  }

  @PostMapping("/new")
  public String matchingAdd(@Validated @ModelAttribute("matching") MatchingSaveForm form,
                            BindingResult bindingResult, HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/matching/matchingAddForm";
    }

    Long matchingId = matchingService.addMatching(form, member);
    return "redirect:/matching/detail/" + matchingId;
  }

  @GetMapping("/detail/{matchingId}")
  public String matchingDetail(@PathVariable("matchingId") Long matchingId, Model model, HttpServletRequest request) {

    Matching matching = matchingService.findOne(matchingId);
    model.addAttribute("matching", matching);

    List<Player> players = playerService.findAllByMatchingId(matchingId);
    List<Player> playersA = players.stream().filter(m -> m.getTeam().toString().equals("A")).toList();
    List<Player> playersB = players.stream().filter(m -> m.getTeam().toString().equals("B")).toList();

    model.addAttribute("playersA", playersA);
    model.addAttribute("playersB", playersB);
    model.addAttribute("playerAddForm", new PlayerAddForm());

    HttpSession session = request.getSession(false);

    if (session != null) {
      Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

      Player playerMemberCheck = matchingService.playerMemberCheck(matchingId, member.getId());
      model.addAttribute("playerMemberCheck", playerMemberCheck);
    }

    return "/matching/matchingDetail";
  }

  @PostMapping("/delete/{matchingId}")
  public String matchingDelete(@PathVariable("matchingId") Long matchingId) {
    List<Player> players = playerService.findAllByMatchingId(matchingId);
    matchingService.deleteById(matchingId, players);

    return "redirect:/matching/home";
  }

  @PostMapping("/delete/memberDelete/{matchingId}")
  public String matchingMemberDelete(@PathVariable("matchingId") Long matchingId, Long memberId) {
    matchingService.deleteMatchingMember(matchingId, memberId);
    return "redirect:/matching/detail/" + matchingId;
  }

  @GetMapping("/update/{matchingId}")
  public String matchingUpdateForm(@PathVariable("matchingId") Long matchingId, Model model) {
    Matching matching = matchingService.findOne(matchingId);

    MatchingUpdateForm form = new MatchingUpdateForm();
    form.setId(matching.getId());
    form.setTitle(matching.getTitle());
    form.setPlace(matching.getPlace());
    form.setCourtType(matching.getCourtType());
    form.setMatchingDate(matching.getMatchingDate());
    form.setMatchingTime(matching.getMatchingTime());
    form.setMatchingType(matching.getMatchingType());
    model.addAttribute("form", form);

    return "/matching/matchingUpdateForm";
  }

  @PostMapping("/update/{matchingId}")
  public String matchingUpdate(@Validated @ModelAttribute("form") MatchingUpdateForm form,
                               BindingResult bindingResult, HttpServletRequest request,
                               @PathVariable("matchingId") Long matchingId) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/matching/matchingUpdateForm";
    }

    matchingService.update(form, member);
    return "redirect:/matching/detail/" + matchingId;
  }


  @PostMapping("/player/add")
  public String matchingPlayerAdd(@ModelAttribute("playerAddForm")PlayerAddForm form, Long matchingId) {
    playerService.matchingPlayerAdd(form);
    matchingService.increasePlayerNumber(Long.valueOf(form.getMatchingId()));
    matchingService.matchingCondtionCheck(Long.valueOf(form.getMatchingId()));
    return "redirect:/matching/detail/" + matchingId;
  }
}
