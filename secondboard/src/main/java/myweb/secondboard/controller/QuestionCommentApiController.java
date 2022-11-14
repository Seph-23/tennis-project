package myweb.secondboard.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Question;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.service.CommentService;
import myweb.secondboard.service.QuestionCommentService;
import myweb.secondboard.service.QuestionService;
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
public class QuestionCommentApiController {

  private final QuestionCommentService questionCommentService;
  private final QuestionService questionService;

  @PostMapping("/commentAddQuestion/{questionId}")
  public JSONObject commentAdd(@PathVariable("questionId") Long questionId,
    @RequestParam Map<String, Object> param, HttpServletRequest request) {
    JSONObject result = new JSONObject();

    String content = param.get("content").toString();
    if (content.length() < 1 || content.length() > 100) {
      result.put("result", "validate");
      return result;
    }
    Member member = (Member) request.getSession(false)
      .getAttribute(SessionConst.LOGIN_MEMBER);
    Question question = questionService.findOne(questionId);
    questionCommentService.save(param, question, member);

    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentDeleteQuestion/{commentId}")
  public JSONObject commentDelete(@PathVariable("commentId") Long commentId) {
    JSONObject result = new JSONObject();

    questionCommentService.deleteById(commentId);

    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentUpdateQuestion/{commentId}")
  public JSONObject commentUpdate(@PathVariable("commentId") Long commentId,
    @RequestParam Map<String, Object> param) {
    JSONObject result = new JSONObject();
    String content = param.get("content").toString();

    if (content.length() < 1 || content.length() > 100) {
      result.put("result", "validate");
      return result;
    }

    questionCommentService.updateComment(commentId, param);
    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentUpdateCancelQuestion/{commentId}")
  public JSONObject commentDeleteCancel(@PathVariable("commentId") Long commentId) {
    JSONObject result = new JSONObject();

    questionCommentService.updateCommentCancel(commentId);

    result.put("result", "success");
    return result;
  }
}
