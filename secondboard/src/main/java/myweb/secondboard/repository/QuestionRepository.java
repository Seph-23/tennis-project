package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryInterface {
  Page<Question> findByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable);

}
