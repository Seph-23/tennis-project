package myweb.secondboard.repository;

import myweb.secondboard.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Matching, Long>, MatchRepositoryInterface {

}
