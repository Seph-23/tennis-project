package myweb.secondboard.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.comments.LessonComment;
import myweb.secondboard.dto.LessonSaveForm;
import myweb.secondboard.dto.LessonUpdateForm;
import myweb.secondboard.service.LessonCommentService;
import myweb.secondboard.service.LessonService;
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
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

  private final LessonService lessonService;
  private final LessonCommentService lessonCommentService;

  @GetMapping("/home")
  public String home(@RequestParam(required = false, value = "keyword") String keyword,
                     @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC)
  Pageable pageable, Model model) {

    Page<Lesson> lessonList;
    if (keyword != null) {
      lessonList = lessonService.searchLessons(keyword, pageable);
    } else {
      lessonList = lessonService.getLessonList(pageable);
    }
    int nowPage = lessonList.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, lessonList.getTotalPages());

    model.addAttribute("lessonList", lessonList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    return "/boards/lesson/lessonHome";
  }

  @GetMapping("/lessonAdd")
  public String noticeAddForm(Model model) {
    model.addAttribute("lesson", new LessonSaveForm());
    return "/boards/lesson/lessonAddForm";
  }

  @PostMapping("/new")
  public String noticeAdd(@Validated @ModelAttribute("lesson") LessonSaveForm form,
      BindingResult bindingResult, HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
        .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/lesson/lessonAddForm";
    }

    Long lessonId = lessonService.addLesson(form, member);
    return "redirect:/lesson/detail/"+lessonId;
  }

  @GetMapping("/detail/{lessonId}")
  public String boardDetail(@PathVariable("lessonId") Long lessonId, Model model,
      HttpServletRequest request, HttpServletResponse response) {

    Lesson lesson = lessonService.findOne(lessonId);
    noticeDetailView(lessonId, model, lesson);
    return "/boards/lesson/lessonDetail";
  }

  @GetMapping("/update/{lessonId}")
  public String noticeUpdateForm(@PathVariable("lessonId") Long lessonId, Model model) {
    Lesson lesson = lessonService.findOne(lessonId);

    LessonUpdateForm form = new LessonUpdateForm();
    form.setId(lesson.getId());
    form.setTitle(lesson.getTitle());
    form.setContent(lesson.getContent());
    model.addAttribute("form",form);

    return  "/boards/lesson/lessonUpdateForm";
  }
  @PostMapping("/update/{lessonId}")
  public String noticeUpdate(@Validated @ModelAttribute("form")LessonUpdateForm form,
      BindingResult bindingResult, HttpServletRequest request,
      @PathVariable("lessonId") Long lessonId) {

    Member member = (Member) request.getSession(false)
        .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/lesson/lessonUpdateForm";
    }

    lessonService.update(form, member);
    return "redirect:/lesson/detail/"+lessonId;
  }

  private void noticeDetailView(Long lessonId, Model model, Lesson lesson) {
    model.addAttribute("lesson", lesson);
    List<LessonComment> comments = lessonCommentService.findComments(lessonId);

    if (comments != null) {
      model.addAttribute("comments", comments);
    }
  }

}
