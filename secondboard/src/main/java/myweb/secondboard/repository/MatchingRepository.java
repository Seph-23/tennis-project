package myweb.secondboard.repository;

import java.time.LocalDate;
import java.util.List;
import myweb.secondboard.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchingRepository extends JpaRepository<Matching, Long>, MatchingRepositoryInterface {

  @Query("select m from Matching m where m.matchingDate = :date")
  List<Matching> findAllByDate(@Param("date") LocalDate date);
}
