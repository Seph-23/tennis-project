package myweb.secondboard.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.service.CommentService;
import myweb.secondboard.web.SessionConst;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentApiController {

  private final CommentService commentService;
  private final BoardService boardService;

  @PostMapping("/commentAdd/{boardId}")
  public int commentAdd(@PathVariable("boardId") Long boardId,
    @RequestParam Map<String, Object> param, HttpServletRequest request) {

    Comment comment = new Comment();
    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);
    Board board = boardService.findOne(boardId);

    commentService.save(param, board, member);
    return 1;
  }
}
