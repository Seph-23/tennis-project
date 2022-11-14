package myweb.secondboard.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.service.LessonCommentService;
import myweb.secondboard.service.LessonService;
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
public class LessonCommentApiController {

  private final LessonService lessonService;
  private final LessonCommentService lessonCommentService;

  @PostMapping("/commentAddLesson/{lessonId}")
  public JSONObject commentAdd(@PathVariable("lessonId") Long lessonId,
      @RequestParam Map<String, Object> param, HttpServletRequest request) {
    JSONObject result = new JSONObject();

    String content = param.get("content").toString();
    if (content.length() < 1 || content.length() > 100) {
      result.put("result", "validate");
      return result;
    }
    Member member = (Member) request.getSession(false)
        .getAttribute(SessionConst.LOGIN_MEMBER);

    Lesson lesson = lessonService.findOne(lessonId);
    lessonCommentService.save(param, lesson, member);
    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentDeleteLesson/{commentId}")
  public JSONObject commentDelete(@PathVariable("commentId") Long commentId) {
    JSONObject result = new JSONObject();

    lessonCommentService.deleteById(commentId);

    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentUpdateLesson/{commentId}")
  public JSONObject commentUpdate(@PathVariable("commentId") Long commentId,
      @RequestParam Map<String, Object> param) {
    JSONObject result = new JSONObject();
    String content = param.get("content").toString();

    if (content.length() < 1 || content.length() > 100) {
      result.put("result", "validate");
      return result;
    }

    lessonCommentService.updateComment(commentId, param);
    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentUpdateCancelLesson/{commentId}")
  public JSONObject commentDeleteCancel(@PathVariable("commentId") Long commentId) {
    JSONObject result = new JSONObject();

    lessonCommentService.updateCommentCancel(commentId);

    result.put("result", "success");
    return result;
  }
}
