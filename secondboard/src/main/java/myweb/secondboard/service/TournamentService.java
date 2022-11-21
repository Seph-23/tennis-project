package myweb.secondboard.service;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Local;
import myweb.secondboard.domain.Tournament;
import myweb.secondboard.dto.TournamentSaveForm;
import myweb.secondboard.repository.LocalRepository;
import myweb.secondboard.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentService {

  private final TournamentRepository tournamentRepository;

  public List<Tournament> getTournamentList() {
    return tournamentRepository.findAll();
  }

  @Transactional
  public void addTournament(TournamentSaveForm form, MultipartFile file) throws IOException {
    Tournament tournament = Tournament.createTournament(form, file);
    tournament.setImgEn(file, tournament);
    tournamentRepository.save(tournament);
  }
}
