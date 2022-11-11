package myweb.secondboard.repository;

import myweb.secondboard.domain.boards.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
