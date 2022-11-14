package myweb.secondboard.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.service.NoticeCommentService;
import myweb.secondboard.service.NoticeService;
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
public class NoticeCommentApiController {

  private final NoticeCommentService noticeCommentService;
  private final NoticeService noticeService;

  @PostMapping("/commentAddNotice/{noticeId}")
  public JSONObject commentAdd(@PathVariable("noticeId") Long noticeId,
      @RequestParam Map<String, Object> param, HttpServletRequest request) {
    JSONObject result = new JSONObject();

    String content = param.get("content").toString();
    if (content.length() < 1 || content.length() > 100) {
      result.put("result", "validate");
      return result;
    }
    Member member = (Member) request.getSession(false)
        .getAttribute(SessionConst.LOGIN_MEMBER);
    Notice notice = noticeService.findOne(noticeId);
    noticeCommentService.save(param, notice, member);

    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentDeleteNotice/{commentId}")
  public JSONObject commentDelete(@PathVariable("commentId") Long commentId) {
    JSONObject result = new JSONObject();

    noticeCommentService.deleteById(commentId);

    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentUpdateNotice/{commentId}")
  public JSONObject commentUpdate(@PathVariable("commentId") Long commentId,
      @RequestParam Map<String, Object> param) {
    JSONObject result = new JSONObject();
    String content = param.get("content").toString();

    if (content.length() < 1 || content.length() > 100) {
      result.put("result", "validate");
      return result;
    }

    noticeCommentService.updateComment(commentId, param);
    result.put("result", "success");
    return result;
  }

  @PostMapping("/commentUpdateCancelNotice/{commentId}")
  public JSONObject commentDeleteCancel(@PathVariable("commentId") Long commentId) {
    JSONObject result = new JSONObject();

    noticeCommentService.updateCommentCancel(commentId);

    result.put("result", "success");
    return result;
  }




}
