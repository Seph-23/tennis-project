package myweb.secondboard.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.dto.NoticeSaveForm;
import myweb.secondboard.dto.NoticeUpdateForm;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.service.NoticeLikeService;
import myweb.secondboard.service.NoticeService;
import myweb.secondboard.web.SessionConst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

  private final NoticeService noticeService;
  private final NoticeLikeService noticeLikeService;
  private final BoardService boardService;

  @GetMapping("/home")
  public String home(@RequestParam(required = false, value = "keyword") String keyword,
                     @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC)
  Pageable pageable, Model model) {

    Page<Notice> noticeList;
    if (keyword != null) {
      noticeList = noticeService.searchNotices(keyword, pageable);
    } else {
      noticeList = noticeService.getNoticeList(pageable);
    }
    int nowPage = noticeList.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, noticeList.getTotalPages());

    List<Long> hotBoardIds = boardService.getHotBoards(LocalDate.now());
    List<Board> hotBoards = new ArrayList<>();
    for (Long hotBoardId : hotBoardIds) {
      hotBoards.add(boardService.findOne(hotBoardId));
      System.out.println("hotBoardId = " + hotBoardId);
    }

    model.addAttribute("hotBoardList", hotBoards);

    model.addAttribute("noticeList", noticeList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    return "/boards/notice/noticeHome";
  }

  @GetMapping("/noticeAdd")
  public String noticeAddForm(Model model) {
    model.addAttribute("notice", new NoticeSaveForm());
    return "/boards/notice/noticeAddForm";
  }

  @PostMapping("/new")
  public String noticeAdd(@Validated @ModelAttribute("notice") NoticeSaveForm form,
      BindingResult bindingResult, HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
        .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/notice/noticeAddForm";
    }

    Long noticeId = noticeService.addNotice(form, member);
    return "redirect:/notice/detail/"+noticeId;
  }

  @GetMapping("/detail/{noticeId}")
  public String boardDetail(@PathVariable("noticeId") Long noticeId, Model model,
      HttpServletRequest request, HttpServletResponse response) {

    Notice notice = noticeService.findOne(noticeId);
    noticeDetailView(noticeId, model, notice);
    Member member = (Member) request.getSession(true).getAttribute(SessionConst.LOGIN_MEMBER);
    if (member != null) {
      String checkLike = noticeLikeService.checkLike(notice.getId(), member.getId());
      model.addAttribute("checkLike", checkLike);
    }

    Long likeCount = noticeLikeService.getLikeCount(notice.getId());
    model.addAttribute("likeCount", likeCount);
    System.out.println("notice.getLikeCount() = " + notice.getLikeCount());

    return "/boards/notice/noticeDetail";
  }

  @GetMapping("/update/{noticeId}")
  public String noticeUpdateForm(@PathVariable("noticeId") Long noticeId, Model model) {
    Notice notice = noticeService.findOne(noticeId);

    NoticeUpdateForm form = new NoticeUpdateForm();
    form.setId(notice.getId());
    form.setTitle(notice.getTitle());
    form.setContent(notice.getContent());
    model.addAttribute("form",form);

    return "/boards/notice/noticeUpdateForm";
  }

  @PostMapping("/update/{noticeId}")
  public String noticeUpdate(@Validated @ModelAttribute("form")NoticeUpdateForm form,
      BindingResult bindingResult, HttpServletRequest request,
      @PathVariable("noticeId") Long noticeId) {

    Member member = (Member) request.getSession(false)
        .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/notice/noticeUpdateForm";
    }

    noticeService.update(form, member);
    return "redirect:/notice/detail/"+noticeId;
  }

  private void noticeDetailView(Long noticeId, Model model, Notice notice) {

    List<Long> hotBoardIds = boardService.getHotBoards(LocalDate.now());
    List<Board> hotBoards = new ArrayList<>();
    for (Long hotBoardId : hotBoardIds) {
      hotBoards.add(boardService.findOne(hotBoardId));
      System.out.println("hotBoardId = " + hotBoardId);
    }

    model.addAttribute("hotBoardList", hotBoards);
    model.addAttribute("notice", notice);
  }

  @PostMapping("/like")
  @ResponseBody
  public Map<String, Object> like(Long noticeId, HttpServletRequest request) {
    Notice notice = noticeService.findOne(noticeId);
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    Integer result = noticeLikeService.clickLike(notice, member);
    Map<String, Object> map = new HashMap<>();
    map.put("result", result);
    Long count = noticeLikeService.getLikeCount(notice.getId());
    map.put("count", count);
    return map;
  }

}
