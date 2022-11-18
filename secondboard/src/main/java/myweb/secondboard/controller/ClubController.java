package myweb.secondboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.*;
import myweb.secondboard.dto.ClubSaveForm;
import myweb.secondboard.dto.ClubUpdateForm;
import myweb.secondboard.service.ClubService;
import myweb.secondboard.service.LocalService;
import myweb.secondboard.service.TournamentService;
import myweb.secondboard.service.VisitorService;
import myweb.secondboard.web.SessionConst;
import myweb.secondboard.web.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClubController {

  private final ClubService clubService;
  private final LocalService localService;
  private final VisitorService visitorService;


  @GetMapping("/club")
  public String clubList(@RequestParam(required = false, value = "keyword") String keyword,
                         @PageableDefault(page = 0, size = 3) Pageable pageable, Model model) {

    Page<Club> clubs;
    if (keyword != null) {
      clubs = clubService.searchClubs(keyword, pageable);
    } else {
      clubs = clubService.getClubList(pageable);
    }

    int nowPage = clubs.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, clubs.getTotalPages());

    model.addAttribute("clubs", clubs);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    // 클럽 생성 모달
    ClubSaveForm clubForm = new ClubSaveForm();
    model.addAttribute("form", clubForm);

    List<Local> locals = localService.getLocalList();
    model.addAttribute("locals", locals);

    return "/club/clubList"; // 동호회 리스트 페이지
  }

  @GetMapping("/club/detail/{clubId}")
  public String clubDetail(@PathVariable("clubId") Long clubId, Model model, HttpServletRequest request) {

    Club club = clubService.findOne(clubId);
    if(club.getImg()!=null){
      String src = new String(club.getImg(), StandardCharsets.UTF_8);
      model.addAttribute("src", src);
    }
    model.addAttribute("club", club);

    List<ClubMember> memberList = clubService.getClubMemberList(club.getId());
    model.addAttribute("memberList", memberList);

    HttpSession session = request.getSession(false);

    if (session != null) {
      Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
      ClubMember clubMemberCheck = clubService.clubMemberCheck(clubId, member.getId());
      model.addAttribute("clubMemberCheck", clubMemberCheck);
    }

    // 방명록 보여주기
    ClubDetailView(clubId, model, club);

    // 정보 수정 모달용
    ClubUpdateForm form = new ClubUpdateForm();
    form.setId(club.getId());
    form.setIntroduction(club.getIntroduction());
    form.setName(club.getName());
    form.setStatus(club.getStatus());
    form.setLocal(club.getLocal());
    model.addAttribute("form", form);

    Status[] statuses = Status.values();
    model.addAttribute("statuses", statuses);

    List<Local> locals = localService.getLocalList();
    model.addAttribute("locals", locals);

    return "/club/clubDetail";
  }

  @PostMapping("/club/save")
  public String clubSave(@Validated @ModelAttribute("form") ClubSaveForm form, BindingResult bindingResult,
                         HttpServletRequest request, MultipartFile file) throws IOException {

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/club/clubList";
    }

    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    form.setMember(member);

    System.out.println("현재 이미지 파일은?(MultipartFile ==>" + file.isEmpty());

    Long clubId = clubService.addClub(form, member, file).getId();
    return "redirect:/club/detail/" + clubId;
  }

  @ResponseBody
  @PostMapping("/club/join")
  public String joinClub(HttpServletRequest request, @RequestParam("clubId") Long id, HttpServletResponse response) throws IOException {
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    Club club = clubService.findOne(id);
    clubService.addClubMember(club, member);

    return "redirect:/club/detail/" + id;
  }

  @PostMapping("/club/update")
  public String clubUpdate(@Validated @ModelAttribute("form") ClubUpdateForm form,
                           BindingResult bindingResult, Model model, MultipartFile file) throws IOException {

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/club/clubDetail";
    }
    System.out.println("file = " + file);
    Long clubId = clubService.update(form, file);

    return "redirect:/club/detail/" + clubId;
  }

  @PostMapping("/club/memberDelete")
  public String clubMemberDelete(HttpServletRequest request, Long id) {
    Long clubId = clubService.findOne(id).getId();
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    Long memberId = member.getId();
    clubService.deleteClubMember(clubId, memberId);
    return "redirect:/club/detail/" + clubId;
  }

  @PostMapping("/club/delete")
  public String clubDelete(Long id) {
    clubService.deleteClub(clubService.findOne(id).getId());
    return "redirect:/club";
  }

  @PostMapping("/club/memberBan/{id}")
  public String clubMemberBan(@PathVariable("id") Long id, HttpServletRequest request) {
    ClubMember clubMember = clubService.get(id);
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);

    if (clubMember.getClub().getMember().getNickname().equals(member.getNickname())) {
      clubService.deleteClubMember(clubMember.getClub().getId(), clubMember.getMember().getId());
    }
    return "redirect:/club/detail/" + clubMember.getClub().getId();
  }

  private void ClubDetailView(Long clubId, Model model, Club club){
    model.addAttribute("club", club);
    List<Visitor> visitors = visitorService.findVisitors(clubId);

    if(visitors!=null){
      model.addAttribute("visitors", visitors);
    }

  }


}