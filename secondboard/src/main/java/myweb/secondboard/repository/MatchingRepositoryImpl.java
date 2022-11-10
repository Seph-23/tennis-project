package myweb.secondboard.repository;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Matching;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchingRepositoryImpl implements MatchingRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;

  public List<Matching> findAll() {
    return em.createQuery("select m from Matching m", Matching.class).getResultList();
  }

  public Matching findOne(Long matchingId) {
    return em.find(Matching.class, matchingId);
  }

  @Override
  public void increasePlayerNumber(Long matchingId) {
    Matching matching = findOne(matchingId);
    matching.increasePlayerNumber(matching);
  }

  @Override
  public void matchingCondtionCheck(Long matchingId) {
    Matching matching = findOne(matchingId);
    matching.matchingConditionCheck(matching);
  }

  public void matchingOngoingCheck(Long matchingId) {
    Matching matching = findOne(matchingId);
    matching.matchingOngoingCheck(matching);
  }

  public void matchingAfterCheck(Long matchingId) {
    Matching matching = findOne(matchingId);
    matching.matchingAfterCheck(matching);
  }

  public void matchingBeforeHourCheck(Long matchingId) {
    Matching matching = findOne(matchingId);
    matching.matchingBeforeHourCheck(matching);
  }

  public void matchingAfterWeek(Long matchingId) {
    Matching matching = findOne(matchingId);
    matching.matchingAfterWeek(matching);
  }
}
