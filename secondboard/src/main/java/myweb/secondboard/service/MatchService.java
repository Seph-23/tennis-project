package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.Member;
import myweb.secondboard.dto.BoardUpdateForm;
import myweb.secondboard.dto.MatchSaveForm;
import myweb.secondboard.dto.MatchUpdateForm;
import myweb.secondboard.repository.MatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchService {

  private final MatchRepository matchRepository;

  public Matching findOne(Long matchId) {
    return matchRepository.findOne(matchId);
  }

  @Transactional
  public Long addMatch(MatchSaveForm form, Member member) {
    Matching matching = Matching.createMatch(form, member);
    matchRepository.save(matching);
    return matching.getId();
  }

  @Transactional
  public Page<Matching> getMatchList(Pageable pageable) {
    return matchRepository.findAll(pageable);
  }

  @Transactional
  public void update(MatchUpdateForm form, Member member) {
    Matching matching = matchRepository.findOne(form.getId()); //조회한 board 엔티티는 영속 상태
    matching.updateMatch(matching, form, member);

  }

  public void deleteById(Long matchId) {
    matchRepository.deleteById(matchId);

  }
}
