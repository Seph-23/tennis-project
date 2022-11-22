package myweb.secondboard.repository;

import myweb.secondboard.domain.Visitor;
import myweb.secondboard.domain.boards.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    @Query("select v from Visitor v where v.club.id= :clubId")
    List<Visitor> findVisitors(@Param("clubId") Long clubId);

    void deleteAllByClubId(Long clubId);
}
