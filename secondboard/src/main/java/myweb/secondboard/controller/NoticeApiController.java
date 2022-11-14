package myweb.secondboard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.boards.Notice;
import myweb.secondboard.service.ImageService;
import myweb.secondboard.service.NoticeService;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공지사항 삭제
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notice")
public class NoticeApiController {

  private final NoticeService noticeService;
  private final ImageService imageService;

  @PostMapping("/noticeDelete/{noticeId}")
  public JSONObject boardDelete(@PathVariable("noticeId") Long noticeId) {
    JSONObject result = new JSONObject();

    Notice notice = noticeService.findOne(noticeId);
    String content = notice.getContent();

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

    noticeService.deleteById(noticeId);

    result.put("result", "success");
    return result;
  }

}
