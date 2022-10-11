package myweb.secondboard.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.service.BoardService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards/api")
public class BoardApiController {

  private final BoardService boardService;

  @PostMapping("/boardDelete/{boardId}")
  public int boardDelete(@PathVariable("boardId") Long boardId) {

    boardService.deleteById(boardId);
    return 1;
  }
}
