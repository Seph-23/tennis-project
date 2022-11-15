package myweb.secondboard.service;


import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.Player;
import myweb.secondboard.dto.PlayerAddForm;
import myweb.secondboard.repository.MatchingRepository;
import myweb.secondboard.repository.MemberRepository;
import myweb.secondboard.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerService {

  private final MemberRepository memberRepository;

  private final MatchingRepository matchingRepository;

  private final PlayerRepository playerRepository;

  @Transactional
  public void matchingPlayerAdd(PlayerAddForm form) {
    Member member = memberRepository.findById(Long.valueOf(form.getMemberId())).get();
    Matching matching = matchingRepository.findById(Long.valueOf(form.getMatchingId())).get();
    Player player = Player.createPlayerFromForm(form, member, matching);
    playerRepository.save(player);
  }

  public List<Player> findAllByMatchingId(Long matchingId) {
    return playerRepository.findAllByMatchingId(matchingId);
  }

  public List<Player> findByMemberId(Long memberId) {
    return playerRepository.findByMemberId(memberId).stream().filter(list -> list.getMatching().getGameResult() != null).toList();
  }
}
