package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.*;
import myweb.secondboard.domain.Record;
import myweb.secondboard.dto.MatchingSaveForm;
import myweb.secondboard.dto.MatchingUpdateForm;
import myweb.secondboard.dto.ResultAddForm;
import myweb.secondboard.repository.MatchingRepository;
import myweb.secondboard.repository.PlayerRepository;
import myweb.secondboard.repository.ResultTempRepository;
import myweb.secondboard.web.CourtType;
import myweb.secondboard.web.GameResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchingService {

  private final MatchingRepository matchingRepository;

  private final PlayerRepository playerRepository;

  private final ResultTempRepository resultTempRepository;

  public Matching findOne(Long matchingId) {
    return matchingRepository.findOne(matchingId);
  }

  @Transactional
  public Page<Matching> getMatchingList(Pageable pageable) {
    return matchingRepository.findAll(pageable);
  }

  @Transactional
  public List<Player> getPlayerList(Long matchingId) {
    return playerRepository.findAllByMatchingId(matchingId);
  }

  // 매칭 등록
  @Transactional
  public Long addMatching(MatchingSaveForm form, Member member) {
    Matching matching = Matching.createMatching(form, member);
    Player player = Player.createPlayer(matching, member);
    matchingRepository.save(matching);
    playerRepository.save(player);
    return matching.getId();
  }

  @Transactional
  public void deleteById(Long matchingId, List<Player> players) {
    playerRepository.deleteAllInBatch(players);
    matchingRepository.deleteById(matchingId);
  }

  @Transactional
  public void update(MatchingUpdateForm form, Member member) {
    Matching matching = matchingRepository.findOne(form.getId());
    matching.updateMatching(matching, form, member);
  }

  public void increasePlayerNumber(Long matchingId) {
    matchingRepository.increasePlayerNumber(matchingId);
  }

  public void matchingCondtionCheck(Long matchingId) {
    matchingRepository.matchingCondtionCheck(matchingId);
  }

  public Player playerMemberCheck(Long matchingId, Long memberId) {
    Optional<Player> result = playerRepository.exist(matchingId, memberId);
    if (result.isEmpty()) {
      return null;
    }
    return result.get();
  }

  public void deleteMatchingMember(Long matchingId, Long memberId) {
    Player player = playerMemberCheck(matchingId, memberId);
    playerRepository.delete(player);
    Matching matching = matchingRepository.findOne(matchingId);
    matching.setPlayerNumber(matching.getPlayerNumber() - 1);
  }

//  public void updateGameResult(Matching matching) {
//    List<Player> players = playerRepository.findAllByMatchingId(matching.getId());
//
//    for (Player player : players) {
//      if (player.getTeam().toString().equals("A")) {
//        if (matching.getGameResult().getTitle().equals("승리")) {
//          player.getMember().getRecord().setWin(player.getMember().getRecord().getWin() + 1);
//          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() + 10);
//        } else {
//          player.getMember().getRecord().setLose(player.getMember().getRecord().getLose() + 1);
//          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() - 7);
//        }
//
//      } else {
//        if (matching.getGameResult().getTitle().equals("승리")) {
//          player.getMember().getRecord().setLose(player.getMember().getRecord().getLose() + 1);
//          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() - 7);
//        } else {
//          player.getMember().getRecord().setWin(player.getMember().getRecord().getWin() + 1);
//          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() + 10);
//        }
//      }
//      player.getMember().getRecord().setRate((double) (player.getMember().getRecord().getWin()) / (double)(player.getMember().getRecord().getWin() + player.getMember().getRecord().getLose()) * 100);
//    }
//  }

  @Transactional
  public void resultTempAdd(ResultAddForm result, Member member) {
    Optional<Player> player = playerRepository.exist(result.getId(), member.getId());
    resultTempRepository.save(ResultTemp.createResultTemp(result, player.get()));
  }

//  @Transactional
//  public void validateResult(Player player) {
//    List<ResultTemp> list = resultTempRepository.findResultTempMatching(player.getMatching().getId());
//    List<ResultTemp> teamA = list.stream().filter(a -> a.getPlayer().getTeam().toString().equals("A")).toList();
//    List<ResultTemp> teamB = list.stream().filter(a -> a.getPlayer().getTeam().toString().equals("B")).toList();
//    Integer count = list.size();
//    if (count == 4) {
//      countFour(player, teamA, teamB);
//    } else if (count == 3) {
//      countThree(player, teamA, teamB);
//    } else if (count == 2) {
//      countTwo(player, list, teamA);
//    } else if (count == 1) {
//      player.getMatching().setGameResult(list.get(0).getGameResult());
//    } else if (count == 0) {
//      player.getMatching().setGameResult(GameResult.NORECORD);
//    }
//    updateGameResult(player.getMatching());
//  }
//
//  private static void countThree(Player player, List<ResultTemp> teamA, List<ResultTemp> teamB) {
//    if (teamA.size() == 2) {
//      if (teamA.get(0).getGameResult() == teamA.get(1).getGameResult() && teamA.get(0).getGameResult() != teamB.get(0).getGameResult()) {
//        player.getMatching().setGameResult(teamA.get(0).getGameResult());
//      } else {
//        player.getMatching().setGameResult(GameResult.PENALTY);
//      }
//    } else {
//      if (teamB.get(0).getGameResult() == teamB.get(1).getGameResult() && teamA.get(0).getGameResult() != teamB.get(0).getGameResult()) {
//        player.getMatching().setGameResult(teamA.get(0).getGameResult());
//      } else {
//        player.getMatching().setGameResult(GameResult.PENALTY);
//      }
//    }
//  }
//
//  private static void countTwo(Player player, List<ResultTemp> list, List<ResultTemp> teamA) {
//    if (list.get(0).getPlayer().getTeam() == list.get(1).getPlayer().getTeam()) {
//      if (list.get(0).getGameResult() == list.get(1).getGameResult()) {
//        player.getMatching().setGameResult(list.get(0).getGameResult());
//      } else {
//        player.getMatching().setGameResult(GameResult.PENALTY);
//      }
//    } else {
//      if (list.get(0).getGameResult() != list.get(1).getGameResult()) {
//        player.getMatching().setGameResult(teamA.get(0).getGameResult());
//      } else {
//        player.getMatching().setGameResult(GameResult.PENALTY);
//      }
//    }
//  }
//
//  private static void countFour(Player player, List<ResultTemp> teamA, List<ResultTemp> teamB) {
//    if (teamA.get(0).getGameResult().toString().equals(teamA.get(1).getGameResult().toString())
//      && teamB.get(0).getGameResult().toString().equals(teamB.get(1).getGameResult().toString())
//      && !teamA.get(0).getGameResult().toString().equals(teamB.get(0).getGameResult().toString())) {
//      player.getMatching().setGameResult(teamA.get(0).getGameResult());
//    } else {
//      player.getMatching().setGameResult(GameResult.PENALTY);
//    }
//  }
}


