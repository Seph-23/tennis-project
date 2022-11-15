package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.Player;
import myweb.secondboard.domain.ResultTemp;
import myweb.secondboard.repository.MatchingRepository;
import myweb.secondboard.repository.PlayerRepository;
import myweb.secondboard.repository.ResultTempRepository;
import myweb.secondboard.web.GameResult;
import myweb.secondboard.web.Tier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulerService {

  private final MatchingRepository matchingRepository;
  private final ResultTempRepository resultTempRepository;
  private final PlayerRepository playerRepository;

  @Async
//  @Scheduled(cron = "0 0/10 * * * *")
//  @Scheduled(cron = "0/10 * * * * *")
  @Transactional
  public void matchScheduleCheck() {

    LocalDate currentDate = LocalDate.now();
    String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

    List<Matching> lists = matchingRepository.findAll();

    // 매칭 상태 업데이트 로직
    for (int i = 0; i < lists.size(); i++) {
      Matching matching = lists.get(i);

      if (lists.get(i).getMatchingDate().equals(currentDate) && lists.get(i).getBeforeTwoHour().equals(currentTime)) {
        matchingRepository.matchingBeforeTwoHourCheck(matching.getId());
      }
      else if (lists.get(i).getMatchingDate().equals(currentDate) && lists.get(i).getBeforeHour().equals(currentTime)) {
        matchingRepository.matchingBeforeHourCheck(matching.getId());
      }
      else if (lists.get(i).getMatchingDate().equals(currentDate) && lists.get(i).getMatchingStartTime().equals(currentTime)) {
        matchingRepository.matchingOngoingCheck(matching.getId());
      }
      else if (lists.get(i).getMatchingDate().equals(currentDate) && lists.get(i).getMatchingEndTime().equals(currentTime)) {
        matchingRepository.matchingAfterCheck(matching.getId());
      }
      else if (lists.get(i).getMatchingDate().plusDays(7).equals(currentDate)) {
        matchingRepository.matchingAfterWeek(matching.getId());
        List<ResultTemp> list = resultTempRepository.findResultTempMatching(matching.getId());
        List<ResultTemp> teamA = list.stream().filter(a -> a.getPlayer().getTeam().toString().equals("A")).toList();
        List<ResultTemp> teamB = list.stream().filter(a -> a.getPlayer().getTeam().toString().equals("B")).toList();
        Integer count = list.size();
        if (matching.getGameResult() == null) {
          if (count == 4) {
            countFour(matching, teamA, teamB);
          } else if (count == 3) {
            countThree(matching, teamA, teamB);
          } else if (count == 2) {
            countTwo(matching, list, teamA);
          } else if (count == 1) {
            matching.setGameResult(list.get(0).getGameResult());
          } else if (count == 0) {
            matching.setGameResult(GameResult.NORECORD);
          }
          updateGameResult(matching);
        }
      }
    }
    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " Schedule 실행됨");
  }

  private static void countThree(Matching matching, List<ResultTemp> teamA, List<ResultTemp> teamB) {
    if (teamA.size() == 2) {
      if (teamA.get(0).getGameResult() == teamA.get(1).getGameResult() && teamA.get(0).getGameResult() != teamB.get(0).getGameResult()) {
        matching.setGameResult(teamA.get(0).getGameResult());
      } else {
        matching.setGameResult(GameResult.PENALTY);
      }
    } else {
      if (teamB.get(0).getGameResult() == teamB.get(1).getGameResult() && teamA.get(0).getGameResult() != teamB.get(0).getGameResult()) {
        matching.setGameResult(teamA.get(0).getGameResult());
      } else {
        matching.setGameResult(GameResult.PENALTY);
      }
    }
  }

  private static void countTwo(Matching matching, List<ResultTemp> list, List<ResultTemp> teamA) {
    if (list.get(0).getPlayer().getTeam() == list.get(1).getPlayer().getTeam()) {
      if (list.get(0).getGameResult() == list.get(1).getGameResult()) {
        matching.setGameResult(list.get(0).getGameResult());
      } else {
        matching.setGameResult(GameResult.PENALTY);
      }
    } else {
      if (list.get(0).getGameResult() != list.get(1).getGameResult()) {
        matching.setGameResult(teamA.get(0).getGameResult());
      } else {
        matching.setGameResult(GameResult.PENALTY);
      }
    }
  }

  private static void countFour(Matching matching, List<ResultTemp> teamA, List<ResultTemp> teamB) {
    if (teamA.get(0).getGameResult().toString().equals(teamA.get(1).getGameResult().toString())
      && teamB.get(0).getGameResult().toString().equals(teamB.get(1).getGameResult().toString())
      && !teamA.get(0).getGameResult().toString().equals(teamB.get(0).getGameResult().toString())) {
      matching.setGameResult(teamA.get(0).getGameResult());
    } else {
      matching.setGameResult(GameResult.PENALTY);
    }
  }

  public void updateGameResult(Matching matching) {
    List<Player> players = playerRepository.findAllByMatchingId(matching.getId());

    for (Player player : players) {
      if (player.getTeam().toString().equals("A")) {
        if (matching.getGameResult().getTitle().equals("승리")) {
          player.getMember().getRecord().setWin(player.getMember().getRecord().getWin() + 1);
          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() + 10);
        } else if (matching.getGameResult().getTitle().equals("패배")){
          player.getMember().getRecord().setLose(player.getMember().getRecord().getLose() + 1);
          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() - 7);
        } else if (matching.getGameResult().getTitle().equals("무효")){
          player.getMember().getRecord().setPenalty(player.getMember().getRecord().getPenalty() + 1);
          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() - 10);
        }

      } else {
        if (matching.getGameResult().getTitle().equals("승리")) {
          player.getMember().getRecord().setLose(player.getMember().getRecord().getLose() + 1);
          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() - 7);
        } else if (matching.getGameResult().getTitle().equals("패배")){
          player.getMember().getRecord().setWin(player.getMember().getRecord().getWin() + 1);
          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() + 10);
        } else if (matching.getGameResult().getTitle().equals("무효")){
          player.getMember().getRecord().setPenalty(player.getMember().getRecord().getPenalty() + 1);
          player.getMember().getRecord().setPoints(player.getMember().getRecord().getPoints() - 10);
        }
      }
      player.getMember().getRecord().setRate((double) (player.getMember().getRecord().getWin()) / (double)(player.getMember().getRecord().getWin() + player.getMember().getRecord().getLose()) * 100);

      if (player.getMember().getRecord().getPoints() >= 150) {
        player.getMember().setTier(Tier.BRONZE);
      } else if (player.getMember().getRecord().getPoints() >= 200) {
        player.getMember().setTier(Tier.SILVER);
      } else if (player.getMember().getRecord().getPoints() >= 250) {
        player.getMember().setTier(Tier.GOLD);
      } else if (player.getMember().getRecord().getPoints() >= 300) {
        player.getMember().setTier(Tier.PLATINUM);
      } else if (player.getMember().getRecord().getPoints() >= 350) {
        player.getMember().setTier(Tier.DIAMOND);
      } else if (player.getMember().getRecord().getPoints() >= 400) {
        player.getMember().setTier(Tier.MASTER);
      } else if (player.getMember().getRecord().getPoints() >= 500) {
        player.getMember().setTier(Tier.GRANDMASTER);
      } else if (player.getMember().getRecord().getPoints() >= 600) {
        player.getMember().setTier(Tier.CHALLENGER);
      }
    }
  }
}
