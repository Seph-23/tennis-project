package myweb.secondboard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.boards.Question;
import myweb.secondboard.service.ImageService;
import myweb.secondboard.service.QuestionService;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/question")
public class QuestionApiController {

  private final QuestionService questionService;
  private final ImageService imageService;

  @PostMapping("/questionDelete/{questionId}")
  public JSONObject boardDelete(@PathVariable("questionId") Long questionId) {
    JSONObject result = new JSONObject();

    Question question = questionService.findOne(questionId);
    String content = question.getContent();

    System.out.println("content = " + content);
    //content =  <p><img src="/image/2" style="width: 25%;"><img src="/image/3" style="width: 25%;"><br></p>
    List<String> ImageNumbers = new ArrayList<>();

    for (int i = 0; i < content.length(); i++) {
      String number = StringUtils.substringBetween(content, "/image/", "\"");
      if (number == null) {
        break;
      }

      String fileName = imageService.findById(Long.valueOf(number)).getFilePath();
      System.out.println("fileName = " + fileName);
      content = StringUtils.substringAfter(content, "/image/");
      imageService.deleteById(Long.valueOf(number));
      File file = new File(fileName);
      file.delete();
    }

    for (String imageNumber : ImageNumbers) {
      System.out.println("imageNumber = " + imageNumber);
    }

    questionService.deleteById(questionId);


    result.put("result", "success");
    return result;
  }

}
