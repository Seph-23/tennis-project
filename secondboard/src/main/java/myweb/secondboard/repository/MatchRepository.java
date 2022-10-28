package myweb.secondboard.repository;

import myweb.secondboard.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Matching, Long>, MatchRepositoryInterface {
}
