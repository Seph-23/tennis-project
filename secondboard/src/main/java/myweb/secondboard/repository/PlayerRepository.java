package myweb.secondboard.repository;

import myweb.secondboard.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
  @Query("select p from Player p where p.matching.id = :matchingId")
  List<Player> findAllByMatchingId(@Param("matchingId") Long matchingId);

  @Query("select p from Player p where p.matching.id = :matchingId and p.member.id = :memberId")
  Optional<Player> exist(@Param("matchingId") Long matchingId, @Param("memberId") Long memberId);

 List<Player> findByMemberId(Long memberId);
}
