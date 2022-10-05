package myweb.secondboard.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/home")
  public String home() {
    return "/boards/boardHome";
  }

  @GetMapping("/boardAdd")
  public String addForm(Model model) {
    model.addAttribute("board", new BoardSaveForm());
    return "/boards/boardAddForm";
  }

  @PostMapping("/new")
  public String addBoard(@Validated @ModelAttribute("board") BoardSaveForm form,
    BindingResult bindingResult, HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/boardAddForm";
    }

    boardService.addBoard(form, member);

    return "/boards/boardHome";
  }
}
