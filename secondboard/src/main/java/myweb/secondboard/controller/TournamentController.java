package myweb.secondboard.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Local;
import myweb.secondboard.domain.Tournament;
import myweb.secondboard.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TournamentController {

  private final TournamentService tournamentService;

  @GetMapping("/tournament")
  public String tournament(Model model) {
    List<Tournament> list = tournamentService.getTournamentList();
    List<Local> locals = tournamentService.getLocalList();

    model.addAttribute("list", list);
    model.addAttribute("locals", locals);
    return "/tournament/tournament";
  }


}
