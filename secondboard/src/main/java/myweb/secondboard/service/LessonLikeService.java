package myweb.secondboard.service;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Member;
import myweb.secondboard.domain.boards.BoardLike;
import myweb.secondboard.domain.boards.Lesson;
import myweb.secondboard.domain.boards.LessonLike;
import myweb.secondboard.repository.BoardLikeRepository;
import myweb.secondboard.repository.LessonLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonLikeService {

  private final LessonLikeRepository lessonLikeRepository;

  public Long getLikeCount(Long lessonId) {
    return lessonLikeRepository.countByLessonId(lessonId);
  }

  public String checkLike(Long lessonId, Long memberId) {
    Optional<LessonLike> likeCheck = lessonLikeRepository.find(lessonId, memberId);

    if (likeCheck.isEmpty()) {
      return "not";
    } else {
      return "is";
    }
  }

  @Transactional
  public int clickLike(Lesson lesson, Member member) {
    LessonLike likeCheck = lessonLikeRepository.find(lesson.getId(), member.getId()).orElse(null);
    if (likeCheck == null) {
      LessonLike like = LessonLike.createLike(lesson, member);
      lessonLikeRepository.save(like);
      lesson.setLikeCount(lesson.getLikeCount() + 1);
      return 1;
    } else {
      lessonLikeRepository.delete(likeCheck);
      lesson.setLikeCount(lesson.getLikeCount() - 1);
      return 0;
    }
  }

  public void deleteById(Long boardId) {
    lessonLikeRepository.deleteById(boardId);
  }
}
