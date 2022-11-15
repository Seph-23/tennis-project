package myweb.secondboard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.service.ImageService;
import myweb.secondboard.service.LessonService;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lesson")
public class LessonApiController {

  private final LessonService lessonService;
  private final ImageService imageService;

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
}
