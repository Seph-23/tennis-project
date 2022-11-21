package myweb.secondboard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Local;
import myweb.secondboard.domain.Tournament;
import myweb.secondboard.dto.TournamentSaveForm;
import myweb.secondboard.service.LocalService;
import myweb.secondboard.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TournamentController {

  private final TournamentService tournamentService;
  private final LocalService localService;

  @GetMapping("/tournament")
  public String tournament(Model model) {
    List<Tournament> list = tournamentService.getTournamentList();
    List<Local> locals = localService.getLocalList();

    List<TournamentSaveForm> listDto = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      listDto.add(new TournamentSaveForm(list.get(i)));
    }

    model.addAttribute("list", listDto);
    model.addAttribute("locals", locals);
    return "/tournament/tournament";
  }

  @PostMapping("/tournament/new")
  public String tournament(@ModelAttribute("form") TournamentSaveForm form,
    MultipartFile file) throws IOException {

    tournamentService.addTournament(form, file);

    return "redirect:/manager/tournament";
  }


}
