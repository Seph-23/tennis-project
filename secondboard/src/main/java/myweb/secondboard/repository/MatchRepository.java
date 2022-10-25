package myweb.secondboard.repository;

import myweb.secondboard.domain.Local;
import myweb.secondboard.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
