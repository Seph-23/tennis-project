package myweb.secondboard.repository;

import myweb.secondboard.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRepository extends JpaRepository<Matching, Long>, MatchingRepositoryInterface {
}
