package myweb.secondboard.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("ranking")
@RequiredArgsConstructor
public class RankingController {

  private final RecordService recordService;

  @GetMapping("/home")
  public String home(Model model) {

    List<Member> rankers = recordService.findRankTopThree();
    if (rankers.size() > 2) {
      Member topRank = rankers.get(0); // 1등
      Member secondRank = rankers.get(1);
      Member thirdRank = rankers.get(2);

//    model.addAttribute("rankers", rankers);
      model.addAttribute("topRank", topRank);
      model.addAttribute("secondRank", secondRank);
      model.addAttribute("thirdRank", thirdRank);

    }

    List<Member> list = recordService.findRankList();
    List<Member> newList = new ArrayList<>();

    for (int i = 3; i < list.size(); i++) {
      if (i > 100) {
        break;
      }
      newList.add(list.get(i));
    }
    model.addAttribute("rank", newList);

    return "ranking/ranking";
  }
}