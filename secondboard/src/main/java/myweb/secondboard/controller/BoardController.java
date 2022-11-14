package myweb.secondboard.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.BoardSaveForm;
import myweb.secondboard.dto.BoardUpdateForm;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.service.CommentService;
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

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  private final CommentService commentService;

  @GetMapping("/home")
  public String home(@RequestParam(required = false, value = "keyword") String keyword,
                     @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC)
  Pageable pageable, Model model) {

    Page<Board> boardList;
    if (keyword != null) {
      boardList = boardService.searchBoards(keyword, pageable);
    } else {
      boardList = boardService.getBoardList(pageable);
    }
    int nowPage = boardList.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, boardList.getTotalPages());

    model.addAttribute("boardList", boardList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    return "/boards/board/boardHome";
  }

  @GetMapping("/boardAdd")
  public String boardAddForm(Model model) {
    model.addAttribute("board", new BoardSaveForm());
    return "/boards/board/boardAddForm";
  }

  @PostMapping("/new")
  public String boardAdd(@Validated @ModelAttribute("board") BoardSaveForm form,
    BindingResult bindingResult, HttpServletRequest request) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/board/boardAddForm";
    }

    Long boardId = boardService.addBoard(form, member);
    return "redirect:/boards/detail/"+boardId;
  }

  @GetMapping("/detail/{boardId}")
  public String boardDetail(@PathVariable("boardId") Long boardId, Model model,
    HttpServletRequest request, HttpServletResponse response) {

    Board board = boardService.findOne(boardId);
    viewLogic(boardId, request, response);
    boardDetailView(boardId, model, board);
    return "/boards/board/boardDetail";
  }

  @GetMapping("/update/{boardId}")
  public String boardUpdateForm(@PathVariable("boardId") Long boardId, Model model) {
    Board board = boardService.findOne(boardId);

    BoardUpdateForm form = new BoardUpdateForm();
    form.setId(board.getId());
    form.setTitle(board.getTitle());
    form.setContent(board.getContent());
    model.addAttribute("form",form);

    return "/boards/board/boardUpdateForm";
  }

  @PostMapping("/update/{boardId}")
  public String boardUpdate(@Validated @ModelAttribute("form")BoardUpdateForm form,
    BindingResult bindingResult, HttpServletRequest request,
    @PathVariable("boardId") Long boardId) {

    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/board/boardUpdateForm";
    }

    boardService.update(form, member);
    return "redirect:/boards/detail/"+boardId;
  }

  private void boardDetailView(Long boardId, Model model, Board board) {
    model.addAttribute("board", board);
    List<Comment> comments = commentService.findComments(boardId);
    if (comments != null) {
      model.addAttribute("comments", comments);
    }
  }

  /* 조회수 로직 */
  private void viewLogic(Long boardId, HttpServletRequest request, HttpServletResponse response) {
    Cookie oldCookie = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("postView")) {
          oldCookie = cookie;
        }
      }
    }
    if (oldCookie != null) {
      if (!oldCookie.getValue().contains("["+ boardId.toString() +"]")) {
        this.boardService.increaseView(boardId);
        oldCookie.setValue(oldCookie.getValue() + "_[" + boardId + "]");
        oldCookie.setPath("/");
        oldCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(oldCookie);
      }
    } else {
      this.boardService.increaseView(boardId);
      Cookie newCookie = new Cookie("postView", "[" + boardId + "]");
      newCookie.setPath("/");
      newCookie.setMaxAge(60 * 60 * 24);
      response.addCookie(newCookie);
    }
  }
}