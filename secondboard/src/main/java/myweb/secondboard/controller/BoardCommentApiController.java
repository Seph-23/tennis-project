package myweb.secondboard.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Comment;
import myweb.secondboard.domain.Member;
import myweb.secondboard.service.BoardCommentReportService;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.service.CommentService;
import myweb.secondboard.web.SessionConst;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class BoardCommentApiController {

  private final CommentService commentService;
  private final BoardService boardService;
  private final BoardCommentReportService boardCommentReportService;

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

  @PostMapping("/report")
  public Integer report(@RequestParam("commentId")Long commentId, @RequestParam("content") String content, HttpServletRequest request) {
    Comment comment = commentService.findOne(commentId);
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    if (boardCommentReportService.checkReport(comment.getId(), member.getId()) == "is") {
      return 1;
    } else {
      boardCommentReportService.addReport(comment, member, content);
      if (boardCommentReportService.getReportCount(comment.getId()) >= 2) {
        return 2;
      }
      return 0;
    }
  }
}
