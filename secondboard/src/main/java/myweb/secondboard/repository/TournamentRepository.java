package myweb.secondboard.repository;

import myweb.secondboard.domain.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

}
