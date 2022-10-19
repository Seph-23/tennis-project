package myweb.secondboard.controller;

import java.util.HashMap;
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
@RequestMapping("/api/comments")
public class CommentApiController {

  private final CommentService commentService;
  private final BoardService boardService;

  @PostMapping("/commentAdd/{boardId}")
  public String commentAdd(@PathVariable("boardId") Long boardId,
    @RequestParam Map<String, Object> param, HttpServletRequest request) {
    String content = param.get("content").toString();
    if (content.length() < 1 || content.length() > 100) {
      return "validate";
    }
    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);
    Board board = boardService.findOne(boardId);
    commentService.save(param, board, member);
    return "success";
  }

  @PostMapping("/commentDelete/{commentId}")
  public String commentDelete(@PathVariable("commentId") Long commentId) {
    commentService.deleteById(commentId);
    return "success";
  }

  @PostMapping("/commentUpdate/{commentId}")
  public String commentUpdate(@PathVariable("commentId") Long commentId,
    @RequestParam Map<String, Object> param) {
    String content = param.get("content").toString();
    if (content.length() < 1 || content.length() > 100) {
      return "validate";
    }
    commentService.updateComment(commentId, param);
    return "success";
  }

  @PostMapping("/commentUpdateCancel/{commentId}")
  public String commentDeleteCancel(@PathVariable("commentId") Long commentId) {
    commentService.updateCommentCancel(commentId);
    return "success";
  }
}
