package myweb.secondboard.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.web.SessionConst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/home")
  public String home(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC)
  Pageable pageable, Model model) {

    Page<Board> boardList = boardService.getBoardList(pageable);
    //페이지블럭 처리
    //1을 더해주는 이유는 pageable은 0부터라 1을 처리하려면 1을 더해서 시작해주어야 한다.
    int nowPage = boardList.getPageable().getPageNumber() + 1;
    //-1값이 들어가는 것을 막기 위해서 max값으로 두 개의 값을 넣고 더 큰 값을 넣어주게 된다.
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, boardList.getTotalPages());

    model.addAttribute("boardList", boardList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

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

    return "home";
  }

  @GetMapping("/detail/{boardId}")
  public String boardDetail(@PathVariable("boardId") Long boardId, Model model) {
    Board board = boardService.findOne(boardId);
    boardService.increaseView(boardId);
    model.addAttribute("board", board);
    return "/boards/boardDetail";
  }
}