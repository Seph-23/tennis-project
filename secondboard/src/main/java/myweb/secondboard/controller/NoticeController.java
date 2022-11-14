package myweb.secondboard.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.domain.comments.NoticeComment;
import myweb.secondboard.dto.NoticeSaveForm;
import myweb.secondboard.dto.NoticeUpdateForm;
import myweb.secondboard.service.NoticeCommentService;
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
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

  private final NoticeService noticeService;
  private final NoticeCommentService noticeCommentService;

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
    model.addAttribute("notice", notice);
    List<NoticeComment> comments = noticeCommentService.findComments(noticeId);

    if (comments != null) {
      model.addAttribute("comments", comments);
    }
  }



}
