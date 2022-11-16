package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.LessonReport;
import myweb.secondboard.repository.LessonReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonReportService {

  private final LessonReportRepository lessonReportRepository;

  @Transactional
  public void addReport(Lesson lesson, Member member, String content) {
    LessonReport report = LessonReport.createReport(lesson, member, content);
    lessonReportRepository.save(report);
  }

  public String checkReport(Long lessonId, Long memberId) {
    Optional<LessonReport> reportCheck = lessonReportRepository.find(lessonId, memberId);
    if (reportCheck.isEmpty()) {
      return "not";
    } else {
      return "is";
    }
  }

  public Long getReportCount(Long lessonId) {
  return lessonReportRepository.countByLessonId(lessonId);
  }
}
