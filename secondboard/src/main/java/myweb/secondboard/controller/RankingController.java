package myweb.secondboard.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Member;
import myweb.secondboard.service.MemberService;
import myweb.secondboard.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
  private final RecordService recordService;
  @GetMapping("/home")
  public String home(Model model) {

    List<Member> rankers = recordService.findRankTopThree();
    if (rankers.size() > 2) {
      Member topRank = rankers.get(0);
      Member secondRank = rankers.get(1);
      Member thirdRank = rankers.get(2);

      if(topRank.getImgEn()!=null){
        String src1 = new String(topRank.getImgEn(), StandardCharsets.UTF_8);
        model.addAttribute("src1",src1);
      }
      if(secondRank.getImgEn()!=null){
        String src2 = new String(secondRank.getImgEn(), StandardCharsets.UTF_8);
        model.addAttribute("src2",src2);
      }
      if(thirdRank.getImgEn()!=null){
        String src3 = new String(thirdRank.getImgEn(), StandardCharsets.UTF_8);
        model.addAttribute("src3",src3);
      }

//    model.addAttribute("rankers", rankers);
      model.addAttribute("topRank", topRank);
      model.addAttribute("secondRank", secondRank);
      model.addAttribute("thirdRank", thirdRank);

    }

    List<Member> list = recordService.findRankList();
    List<Member> newList = new ArrayList<>();
    for (int i = 3; i < 100; i++) {
      newList.add(list.get(i));
    }
    model.addAttribute("rank", newList);

    return "ranking/ranking";
  }
}
