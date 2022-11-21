package myweb.secondboard.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Local;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.BoardReport;
import myweb.secondboard.domain.comments.BoardCommentReport;
import myweb.secondboard.dto.TournamentSaveForm;
import myweb.secondboard.service.BoardCommentReportService;
import myweb.secondboard.service.BoardLikeService;
import myweb.secondboard.service.BoardReportService;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.service.CommentService;
import myweb.secondboard.service.LocalService;
import myweb.secondboard.web.SessionConst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

  private final BoardReportService boardReportService;
  private final BoardCommentReportService boardCommentReportService;
  private final BoardService boardService;
  private final BoardLikeService boardLikeService;
  private final CommentService commentService;
  private final LocalService localService;

  @GetMapping("/profile")
  public String managerHome(
    @PageableDefault(page = 0, size = 10, sort = "boardId", direction = Direction.ASC)
    Pageable pageable, Model model) {

    Page<BoardReport> boardReports = boardReportService.findAll(pageable);
    List<BoardReport> boardReportList = boardReports.stream().toList();

    int nowPage = boardReports.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, boardReports.getTotalPages());

    model.addAttribute("boardReportList", boardReportList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    return "/manager/managerHome";
  }

  @GetMapping("/comment/report")
  public String commentReport(
    @PageableDefault(page = 0, size = 10, sort = "commentId", direction = Direction.ASC)
    Pageable pageable, Model model) {

    Page<BoardCommentReport> commentReports = boardCommentReportService.findAll(pageable);
    List<BoardCommentReport> commentReportList = commentReports.stream().toList();

    int nowPage = commentReports.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, commentReports.getTotalPages());

    model.addAttribute("commentReportList", commentReportList);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    return "/manager/managerCommentReport";
  }

  @GetMapping("/boardDetail/{boardId}")
  public String boardDetail(@PathVariable("boardId") Long boardId, Model model,
    HttpServletRequest request, HttpServletResponse response) {

    Board board = boardService.findOne(boardId);
    viewLogic(boardId, request, response);
    boardDetailView(boardId, model, board);

    Member member = (Member) request.getSession(true).getAttribute(SessionConst.LOGIN_MEMBER);
    if (member != null) { //회원
      String checkLike = boardLikeService.checkLike(board.getId(), member.getId());
      model.addAttribute("checkLike", checkLike);
    }
    if (board.getMember().getNickname().contains("탈퇴된")) {
      board.setAuthor(board.getMember().getNickname());
    }


    List<Long> hotBoardIds = boardService.getHotBoards(LocalDate.now());
    List<Board> hotBoards = new ArrayList<>();
    for (Long hotBoardId : hotBoardIds) {
      hotBoards.add(boardService.findOne(hotBoardId));
      System.out.println("hotBoardId = " + hotBoardId);
    }

    model.addAttribute("hotBoardList", hotBoards);

    //비회원
    System.out.println("member = " + member);
    Long likeCount = boardLikeService.getLikeCount(board.getId());
    model.addAttribute("likeCount", likeCount);

    Long reportCount = boardReportService.getReportCount(board.getId());
    model.addAttribute("reportCount", reportCount);
    return "/manager/boardDetailAdmin";
  }

  //TODO
  @GetMapping("/tournament")
  public String managerTournament(Model model) {
    List<Local> locals = localService.getLocalList();
    model.addAttribute("locals", locals);

    TournamentSaveForm tournamentSaveForm = new TournamentSaveForm();
    model.addAttribute("form", tournamentSaveForm);

    return "/manager/managerTournament";
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
