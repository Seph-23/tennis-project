package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.repository.MatchingRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulerService {

  private final MatchingRepository matchingRepository;

  @Async
  @Scheduled(cron = "0 0/10 * * * *")
  @Transactional
  public void matchScheduleCheck() {

    LocalDate currentDate = LocalDate.now();
    String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

    List<Matching> lists = matchingRepository.findAll();

    for (int i = 0; i < lists.size(); i++) {
      if (lists.get(i).getMatchingDate().equals(currentDate) && lists.get(i).getMatchingStartTime().equals(currentTime)) {
        Matching matching = lists.get(i);
        matchingRepository.matchingOngoingCheck(matching.getId());
      }
      if (lists.get(i).getMatchingDate().equals(currentDate) && lists.get(i).getMatchingEndTime().equals(currentTime)) {
        Matching matching = lists.get(i);
        matchingRepository.matchingAfterCheck(matching.getId());
      }
    }

    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " Schedule 실행됨");
  }
}
