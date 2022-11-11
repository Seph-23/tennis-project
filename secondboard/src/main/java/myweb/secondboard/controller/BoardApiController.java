package myweb.secondboard.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.boards.BoardUploadFile;
import myweb.secondboard.service.BoardService;
import myweb.secondboard.service.ImageService;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.PatternUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardApiController {

  private final BoardService boardService;
  private final ImageService imageService;

  @PostMapping("/boardDelete/{boardId}")
  public JSONObject boardDelete(@PathVariable("boardId") Long boardId) {
    JSONObject result = new JSONObject();

    //TODO: 이미지 삭제
    Board board = boardService.findOne(boardId);
    String content = board.getContent();

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





    boardService.deleteById(boardId);


    result.put("result", "success");
    return result;
  }
}
