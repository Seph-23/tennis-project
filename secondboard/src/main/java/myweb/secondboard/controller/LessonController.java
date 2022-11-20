package myweb.secondboard.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.comments.LessonComment;
import myweb.secondboard.dto.LessonSaveForm;
import myweb.secondboard.dto.LessonUpdateForm;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.service.LessonCommentService;
import myweb.secondboard.service.LessonLikeService;
import myweb.secondboard.service.LessonReportService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

  private final LessonService lessonService;
  private final LessonCommentService lessonCommentService;
  private final LessonReportService lessonReportService;
  private final LessonLikeService lessonLikeService;
  private final BoardService boardService;

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

    List<Lesson> lessons = lessonList.stream().toList();
    for (Lesson lesson : lessons) {
      if (lesson.getMember().getNickname().contains("탈퇴된")) {
        lesson.setAuthor(lesson.getMember().getNickname());
      }
    }

    List<Long> hotBoardIds = boardService.getHotBoards(LocalDate.now());
    List<Board> hotBoards = new ArrayList<>();
    for (Long hotBoardId : hotBoardIds) {
      hotBoards.add(boardService.findOne(hotBoardId));
      System.out.println("hotBoardId = " + hotBoardId);
    }

    model.addAttribute("hotBoardList", hotBoards);

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

    Member member = (Member) request.getSession(true).getAttribute(SessionConst.LOGIN_MEMBER);
    if (member != null) { //회원
      String checkLike = lessonLikeService.checkLike(lesson.getId(), member.getId());
      model.addAttribute("checkLike", checkLike);
    }
    if (lesson.getMember().getNickname().contains("탈퇴된")) {
      lesson.setAuthor(lesson.getMember().getNickname());
    }


    List<Long> hotBoardIds = boardService.getHotBoards(LocalDate.now());
    List<Board> hotBoards = new ArrayList<>();
    for (Long hotBoardId : hotBoardIds) {
      hotBoards.add(boardService.findOne(hotBoardId));
      System.out.println("hotBoardId = " + hotBoardId);
    }

    model.addAttribute("hotBoardList", hotBoards);

    //비회원
    Long likeCount = lessonLikeService.getLikeCount(lesson.getId());
    model.addAttribute("likeCount", likeCount);

    Long reportCount = lessonReportService.getReportCount(lesson.getId());
    model.addAttribute("reportCount", reportCount);
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

    for (LessonComment lessonComment : comments) {
      if (lessonComment.getMember().getNickname().contains("탈퇴된")) {
        lessonComment.setAuthor(lessonComment.getMember().getNickname());
      }
    }

    if (comments != null) {
      model.addAttribute("comments", comments);
    }
  }

}
