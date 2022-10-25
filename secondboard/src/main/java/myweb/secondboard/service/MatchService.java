package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.MatchSaveForm;
import myweb.secondboard.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;

  @Transactional
  public Long addMatch(MatchSaveForm form, Member member) {
    Matching matching = Matching.createMatch(form, member);
    matchRepository.save(matching);
    return matching.getId();
  }
}
