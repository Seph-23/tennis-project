package myweb.secondboard.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.service.BoardService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardApiController {

  private final BoardService boardService;

  @PostMapping("/boardDelete/{boardId}")
  public JSONObject boardDelete(@PathVariable("boardId") Long boardId) {
    JSONObject result = new JSONObject();

    boardService.deleteById(boardId);

    result.put("result", "success");
    return result;
  }
}
