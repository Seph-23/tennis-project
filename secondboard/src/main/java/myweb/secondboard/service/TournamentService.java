package myweb.secondboard.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myweb.secondboard.domain.Local;
import myweb.secondboard.domain.Tournament;
import myweb.secondboard.repository.LocalRepository;
import myweb.secondboard.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentService {

  private final TournamentRepository tournamentRepository;
  private final LocalRepository localRepository;

  public List<Tournament> getTournamentList() {
    return tournamentRepository.findAll();
  }

  public List<Local> getLocalList() {
    return  localRepository.findAll();
  }
}
