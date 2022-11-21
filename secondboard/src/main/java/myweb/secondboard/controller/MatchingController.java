package myweb.secondboard.controller;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.Player;
import myweb.secondboard.dto.MatchingSaveForm;
import myweb.secondboard.dto.MatchingSearchCondition;
import myweb.secondboard.dto.MatchingUpdateForm;
import myweb.secondboard.dto.PlayerAddForm;
import myweb.secondboard.dto.ResultAddForm;
import myweb.secondboard.service.MatchingService;
import myweb.secondboard.service.PlayerService;
import myweb.secondboard.web.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/matching")
@RequiredArgsConstructor
public class MatchingController {

  private final MatchingService matchingService;
  private final PlayerService playerService;

  //ModelAttribute 컨트롤러 레벨에 적용
  @ModelAttribute
  public void addAttributes(Model model) {
    ArrayList<LocalDate> carousel = new ArrayList<>(); //날짜
    ArrayList<String> dayKR = new ArrayList<>(); //요일
    Map<LocalDate, String> carouselDay = new LinkedHashMap<>();
    for (int i = 0; i <10 ; i++) {

      carousel.add(LocalDate.now().plusDays(i));
      dayKR.add(LocalDate.now().plusDays(i).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN));
      carouselDay.put(carousel.get(i), dayKR.get(i));
    }
    model.addAttribute("carouselDay", carouselDay);
  }

  @GetMapping("/home")
  public String home(Model model) {

//    List<Matching> matchingList = matchingService.findAllByDate(LocalDate.now());
    MatchingSearchCondition condition = new MatchingSearchCondition();
    condition.setDate(LocalDate.now().toString());
    List<Matching> matchingList = matchingService.searchMatchingByBuilder(condition);

    for (Matching matching : matchingList) {
      if (matching.getMember().getNickname().contains("탈퇴된")) {
        matching.setAuthor(matching.getMember().getNickname());
        System.out.println("matching.getMember().getNickname() = " + matching.getMember().getNickname());
        matching.setMatchingCondition(MatchingCondition.FAIL);
      }
    }

    model.addAttribute("matchingList", matchingList);

    MatchingSaveForm matchingForm = new MatchingSaveForm();
    model.addAttribute("matching", matchingForm);

    MatchingType[] matchTypes = MatchingType.values();
    model.addAttribute("matchTypes", matchTypes);

    CourtType[] courtTypes = CourtType.values();
    model.addAttribute("courtTypes", courtTypes);

    List<String> matchingPlaces = matchingService.getMatchingPlaces(LocalDate.now().toString());
    System.out.println("matchingPlaces = " + matchingPlaces);



    model.addAttribute("lat", null);

    model.addAttribute("searchCondition", condition);
    model.addAttribute("currDate", LocalDate.now().toString());

    return "/matching/matchingHome";
  }

  @PostMapping("/new")
  public String matchingAdd(@Validated @ModelAttribute("matching") MatchingSaveForm form,
    BindingResult bindingResult, HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "redirect:/matching/home";
    }

    Long matchingId = matchingService.addMatching(form, member);
    return "redirect:/matching/detail/" + matchingId;
  }

  @GetMapping("/detail/{matchingId}")
  public String matchingDetail(@PathVariable("matchingId") Long matchingId, Model model,
    HttpServletRequest request) {

    Matching matching = matchingService.findOne(matchingId);
    model.addAttribute("matching", matching);

    List<Player> players = playerService.findAllByMatchingId(matchingId);
    List<Player> playersA = players.stream().filter(m -> m.getTeam().toString().equals("A"))
      .toList();
    List<Player> playersB = players.stream().filter(m -> m.getTeam().toString().equals("B"))
      .toList();

    List<List<Player>> playerListA = new ArrayList<>();
    List<List<Player>> playerListB = new ArrayList<>();

    for (int i = 0; i < playersA.size(); i++) {
      playerListA.add(playerService.findByMemberId(playersA.get(i).getMember().getId()));
    }
    for (int i = 0; i < playersB.size(); i++) {
      playerListB.add(playerService.findByMemberId(playersB.get(i).getMember().getId()));
    }

    model.addAttribute("playerListA", playerListA);
    model.addAttribute("playerListB", playerListB);

    model.addAttribute("playersA", playersA);
    model.addAttribute("playersB", playersB);
    model.addAttribute("playerAddForm", new PlayerAddForm());
    model.addAttribute("resultForm", new ResultAddForm());
    GameResult[] results = GameResult.values();
    model.addAttribute("results", results);

    HttpSession session = request.getSession(false);


    if (session != null) {
      Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

      Player playerMemberCheck = matchingService.playerMemberCheck(matchingId, member.getId());
      model.addAttribute("playerMemberCheck", playerMemberCheck);
    }

    MatchingType[] matchTypes = MatchingType.values();
    model.addAttribute("matchTypes", matchTypes);

    CourtType[] courtTypes = CourtType.values();
    model.addAttribute("courtTypes", courtTypes);

    MatchingUpdateForm matchingForm = new MatchingUpdateForm();
    matchingForm.setId(matching.getId());
    matchingForm.setTitle(matching.getTitle());
    matchingForm.setPlace(matching.getPlace());
    matchingForm.setCourtType(matching.getCourtType());
    matchingForm.setMatchingDate(matching.getMatchingDate());
    matchingForm.setMatchingStartTime(matching.getMatchingStartTime());
    matchingForm.setMatchingEndTime(matching.getMatchingEndTime());
    matchingForm.setLat(matching.getLat());
    matchingForm.setLng(matching.getLng());
    matchingForm.setContent(matching.getContent());
    model.addAttribute("matchingForm", matchingForm);
    System.out.println("matchingForm.getContent() = " + matchingForm.getContent());
    String[] split = matchingForm.getContent().split("\\n");
    List<String> matchContentList = new ArrayList<>(Arrays.asList(split));
    model.addAttribute("matchContentList", matchContentList);

    if (session == null) {
      return "/matching/matchingDetailNotLoginMember";
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

  @PostMapping("/update/{matchingId}")
  public String matchingUpdate(@Validated @ModelAttribute("matchingForm") MatchingUpdateForm form,
    BindingResult bindingResult, HttpServletRequest request,
    @PathVariable("matchingId") Long matchingId) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "redirect:/matching/detail/" + matchingId;
    }

    matchingService.update(form, member);
    return "redirect:/matching/detail/" + matchingId;
  }

  @PostMapping("/player/add")
  public String matchingPlayerAdd(@Validated @ModelAttribute("playerAddForm") PlayerAddForm form,
    BindingResult bindingResult, Long matchingId) {

    if (bindingResult.hasErrors()) {
//      log.info("errors = {}", bindingResult);
      return "redirect:/matching/detail/" + matchingId;
    }

    playerService.matchingPlayerAdd(form);
    matchingService.increasePlayerNumber(Long.valueOf(form.getMatchingId()));
    matchingService.matchingCondtionCheck(Long.valueOf(form.getMatchingId()));

    return "redirect:/matching/detail/" + matchingId;
  }

  @PostMapping("/result")
  public String matchingResult(@ModelAttribute("result") ResultAddForm result,
    HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    matchingService.resultTempAdd(result, member);

    return "redirect:/matching/home";
  }

  //  @PostMapping("/new")
  public String matchingFormAddLocation(
    @Validated @ModelAttribute("matching") MatchingSaveForm form,
    BindingResult bindingResult, HttpServletRequest request, Model model) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "redirect:/matching/home";
    }

    model.addAttribute("form", form);
    return "/matching/matchingLocationAdd";
  }

  @PostMapping("/matchingListUpdate/{date}")
  public String matchingListUpdate(@PathVariable("date") String date, Model model) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ArrayList<LocalDate> carousel = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      carousel.add(LocalDate.now().plusDays(i));
    }
    model.addAttribute("carouselDays", carousel);

//    List<Matching> matchingList = matchingService.findAllByDate(LocalDate.parse(date, dtf));
    MatchingSearchCondition condition = new MatchingSearchCondition();
    condition.setDate(date);
    List<Matching> matchingList = matchingService.searchMatchingByBuilder(condition);

    for (Matching matching : matchingList) {
      if (matching.getMember().getNickname().contains("탈퇴된")) {
        matching.setAuthor(matching.getMember().getNickname());
        System.out.println("matching.getMember().getNickname() = " + matching.getMember().getNickname());
      }
    }

    model.addAttribute("matchingList", matchingList);

    MatchingSaveForm matchingForm = new MatchingSaveForm();
    model.addAttribute("matching", matchingForm);

    MatchingType[] matchTypes = MatchingType.values();
    model.addAttribute("matchTypes", matchTypes);

    CourtType[] courtTypes = CourtType.values();
    model.addAttribute("courtTypes", courtTypes);

    List<String> matchingPlaces = matchingService.getMatchingPlaces(date);
    model.addAttribute("matchingPlaces", matchingPlaces);

    model.addAttribute("lat", null);

    model.addAttribute("searchCondition", condition);
    model.addAttribute("currDate", date);

    return "/matching/matchingHome";
  }

  @PostMapping("/searchCondition")
  public String matchingSearchCondition(
    @ModelAttribute("searchCondition") MatchingSearchCondition condition, Model model) {

    ArrayList<LocalDate> carousel = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      carousel.add(LocalDate.now().plusDays(i));
    }
    model.addAttribute("carouselDays", carousel);

    List<Matching> matchingList = matchingService.searchMatchingByBuilder(condition);

    for (Matching matching : matchingList) {
      if (matching.getMember().getNickname().contains("탈퇴된")) {
        matching.setAuthor(matching.getMember().getNickname());
        System.out.println("matching.getMember().getNickname() = " + matching.getMember().getNickname());
      }
    }

    model.addAttribute("matchingList", matchingList);

//    model.addAttribute("matchingList", matchingList);

    MatchingSaveForm matchingForm = new MatchingSaveForm();
    model.addAttribute("matching", matchingForm);

    MatchingType[] matchTypes = MatchingType.values();
    model.addAttribute("matchTypes", matchTypes);

    CourtType[] courtTypes = CourtType.values();
    model.addAttribute("courtTypes", courtTypes);

    List<String> matchingPlaces = matchingService.getMatchingPlaces(condition.getDate());
    model.addAttribute("matchingPlaces", matchingPlaces);

    model.addAttribute("lat", null);

    model.addAttribute("searchCondition", condition);
    model.addAttribute("currDate", condition.getDate());

    return "/matching/matchingHome";
  }
}

