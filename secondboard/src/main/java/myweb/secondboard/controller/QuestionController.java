package myweb.secondboard.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Question;
import myweb.secondboard.domain.comments.QuestionComment;
import myweb.secondboard.dto.QuestionSaveForm;
import myweb.secondboard.dto.QuestionUpdateForm;
import myweb.secondboard.service.QuestionCommentService;
import myweb.secondboard.service.QuestionService;
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
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionService questionService;
  private final QuestionCommentService questionCommentService;


  @GetMapping("/home")
  public String home(@RequestParam(required = false, value = "keyword") String keyword,
                     @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC)
  Pageable pageable, Model model) {

    Page<Question> questionList;
    if (keyword != null) {
      questionList = questionService.searchQuestions(keyword, pageable);
    } else {
      questionList = questionService.getQuestionList(pageable);
    }
    int nowPage = questionList.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, questionList.getTotalPages());

    model.addAttribute("questionList", questionList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    return "/boards/question/questionHome";
  }


  @GetMapping("/questionAdd")
  public String noticeAddForm(Model model) {
    model.addAttribute("question", new QuestionSaveForm());
    return "/boards/question/questionAddForm";
  }

  @PostMapping("/new")
  public String noticeAdd(@Validated @ModelAttribute("question") QuestionSaveForm form,
      BindingResult bindingResult, HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
        .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/question/questionAddForm";
    }

    Long questionId = questionService.addQuestion(form, member);
    return "redirect:/question/detail/"+questionId;
  }

  @GetMapping("/detail/{questionId}")
  public String boardDetail(@PathVariable("questionId") Long questionId, Model model,
      HttpServletRequest request, HttpServletResponse response) {

    Question question = questionService.findOne(questionId);
    noticeDetailView(questionId, model, question);
    return "/boards/question/questionDetail";
  }

  @GetMapping("/update/{questionId}")
  public String noticeUpdateForm(@PathVariable("questionId") Long questionId, Model model) {
    Question question = questionService.findOne(questionId);

    QuestionUpdateForm form = new QuestionUpdateForm();
    form.setId(question.getId());
    form.setTitle(question.getTitle());
    form.setContent(question.getContent());
    model.addAttribute("form",form);

    return "/boards/question/questionUpdateForm";
  }

  @PostMapping("/update/{questionId}")
  public String noticeUpdate(@Validated @ModelAttribute("form")QuestionUpdateForm form,
      BindingResult bindingResult, HttpServletRequest request,
      @PathVariable("questionId") Long questionId) {

    Member member = (Member) request.getSession(false)
        .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/question/questionUpdateForm";
    }

    questionService.update(form, member);
    return "redirect:/question/detail/"+questionId;
  }


  private void noticeDetailView(Long questionId, Model model, Question question) {
    model.addAttribute("question", question);
    List<QuestionComment> comments = questionCommentService.findComments(questionId);

    if (comments != null) {
      model.addAttribute("comments", comments);
    }
  }

}
