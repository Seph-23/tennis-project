package myweb.secondboard.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.Player;
import myweb.secondboard.dto.PlayerAddForm;
import myweb.secondboard.repository.MatchRepository;
import myweb.secondboard.repository.MemberRepository;
import myweb.secondboard.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerService {

  private final PlayerRepository playerRepository;
  private final MemberRepository memberRepository;
  private final MatchRepository matchRepository;

  public List<Player> findAllByMatchingId(Long matchId) {
    return playerRepository.findAllByMatchingId(matchId);
  }

  public void matchPlayerAdd(PlayerAddForm form) {
    Member member = memberRepository.findById(Long.valueOf(form.getMemberId())).get();
    Matching matching = matchRepository.findById(Long.valueOf(form.getMatchId())).get();
    Player player = Player.createPlayerFromForm(form, member, matching);
    playerRepository.save(player);
  }
}
