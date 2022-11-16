package myweb.secondboard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.repository.LessonReportRepository;
import myweb.secondboard.service.ImageService;
import myweb.secondboard.service.LessonLikeService;
import myweb.secondboard.service.LessonReportService;
import myweb.secondboard.service.LessonService;
import myweb.secondboard.web.SessionConst;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lesson")
public class LessonApiController {

  private final LessonService lessonService;
  private final ImageService imageService;

  private final LessonLikeService lessonLikeService;
  private final LessonReportService lessonReportService;

  @PostMapping("/lessonDelete/{lessonId}")
  public JSONObject boardDelete(@PathVariable("lessonId") Long lessonId) {
    JSONObject result = new JSONObject();

    Lesson lesson = lessonService.findOne(lessonId);
    String content = lesson.getContent();

    System.out.println("content = " + content);
    //content =  <p><img src="/image/2" style="width: 25%;"><img src="/image/3" style="width: 25%;"><br></p>
    List<String> ImageNumbers = new ArrayList<>();

    for (int i = 0; i < content.length(); i++) {
      String number = StringUtils.substringBetween(content, "/image/", "\"");
      if (number == null) {
        break;
      }

      if (imageService.findById(Long.valueOf(number)).isPresent()) {
        String fileName = imageService.findById(Long.valueOf(number)).get().getFilePath();
        System.out.println("fileName = " + fileName);
        content = StringUtils.substringAfter(content, "/image/");
        imageService.deleteById(Long.valueOf(number));
        File file = new File(fileName);
        file.delete();
      }
      else break;

    }

    for (String imageNumber : ImageNumbers) {
      System.out.println("imageNumber = " + imageNumber);
    }

    lessonService.deleteById(lessonId);


    result.put("result", "success");
    return result;
  }

  @PostMapping("/like")
  public Map<String, Object> like(Long lessonId, HttpServletRequest request) {
    Lesson lesson = lessonService.findOne(lessonId);
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    Integer result = lessonLikeService.clickLike(lesson, member);
    Map<String, Object> map = new HashMap<>();
    map.put("result", result);
    Long count = lessonLikeService.getLikeCount(lesson.getId());
    map.put("count", count);
    return map;
  }

  @PostMapping("/report")
  public Integer report(@RequestParam("lessonId")Long lessonId, @RequestParam("content") String content, HttpServletRequest request) {
    Lesson lesson = lessonService.findOne(lessonId);
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    if (lessonReportService.checkReport(lesson.getId(), member.getId()) == "is") {
      return 1;
    } else {
      lessonReportService.addReport(lesson, member, content);
      if (lessonReportService.getReportCount(lesson.getId()) >= 2) {
        return 2;
      }
      return 0;
    }
  }
}
