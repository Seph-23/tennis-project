package myweb.secondboard.repository;


import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Board;
import myweb.secondboard.domain.Matching;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class MatchRepositoryImpl {

  @PersistenceContext
  private final EntityManager em;

  public Matching findOne(Long matchId) {
    return em.find(Matching.class, matchId);
  }
}
