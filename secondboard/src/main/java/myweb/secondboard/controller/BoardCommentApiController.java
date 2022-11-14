package myweb.secondboard.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.service.CommentService;
import myweb.secondboard.web.SessionConst;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class BoardCommentApiController {

  private final CommentService commentService;
  private final BoardService boardService;

  @PostMapping("/commentAdd/{boardId}")
  public JSONObject commentAdd(@PathVariable("boardId") Long boardId,
    @RequestParam Map<String, Object> param, HttpServletRequest request) {
    JSONObject result = new JSONObject();

    String content = param.get("content").toString();
    if (content.length() < 1 || content.length() > 100) {
      result.put("result", "validate");
      return result;
    }
    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);
    Board board = boardService.findOne(boardId);
    commentService.save(param, board, member);

    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentDelete/{commentId}")
  public JSONObject commentDelete(@PathVariable("commentId") Long commentId) {
    JSONObject result = new JSONObject();

    commentService.deleteById(commentId);

    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentUpdate/{commentId}")
  public JSONObject commentUpdate(@PathVariable("commentId") Long commentId,
    @RequestParam Map<String, Object> param) {
    JSONObject result = new JSONObject();
    String content = param.get("content").toString();

    if (content.length() < 1 || content.length() > 100) {
      result.put("result", "validate");
      return result;
    }

    commentService.updateComment(commentId, param);
    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentUpdateCancel/{commentId}")
  public JSONObject commentDeleteCancel(@PathVariable("commentId") Long commentId) {
    JSONObject result = new JSONObject();

    commentService.updateCommentCancel(commentId);

    result.put("result", "success");
    return result;
  }
}
