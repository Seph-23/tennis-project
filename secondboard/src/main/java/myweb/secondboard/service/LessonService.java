package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.dto.LessonSaveForm;
import myweb.secondboard.dto.LessonUpdateForm;
import myweb.secondboard.repository.LessonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonService {
  
  private final LessonRepository lessonRepository;

  public Lesson findOne(Long LessonId) {
    return lessonRepository.findOne(LessonId);
  }
  @Transactional
  public Long addLesson(LessonSaveForm form, Member member) {
    Lesson lesson = Lesson.createLesson(form, member);
    lessonRepository.save(lesson);
    return lesson.getId();
  }

  @Transactional
  public Page<Lesson> getLessonList(Pageable pageable) {
    return lessonRepository.findAll(pageable);
  }

  @Transactional
  public void increaseView(Long boardId) {
    lessonRepository.updateView(boardId);
  }

  @Transactional
  public void deleteById(Long boardId) {
    lessonRepository.deleteById(boardId);
  }

  @Transactional
  public void update(LessonUpdateForm form, Member member) {
    Lesson lesson = lessonRepository.findOne(form.getId()); //조회한 board 엔티티는 영속 상태
    lesson.updateLesson(lesson, form, member);
    // boardRepository.save(board); 트랜잭션 커밋 시점에 변경 감지(Dirty checking)
  }

  public Page<Lesson> searchLessons(String keyword, Pageable pageable) {
    String title = keyword;
    String author = keyword;
    return lessonRepository.findByTitleContainingOrAuthorContaining(title, author, pageable);
  }
}
