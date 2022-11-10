package myweb.secondboard.repository;

import myweb.secondboard.domain.ClubMember;
import myweb.secondboard.domain.ResultTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResultTempRepository extends JpaRepository<ResultTemp, Long> {

  @Query("select rt from ResultTemp rt where rt.player.matching.id = :matchingId")
  List<ResultTemp> findResultTempMatching(@Param ("matchingId")Long matchingId);

}
