package myweb.secondboard.repository;

import static myweb.secondboard.domain.QMatching.*;
import static org.springframework.util.StringUtils.*;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Matching;
import myweb.secondboard.domain.QMatching;
import myweb.secondboard.dto.MatchingSearchCondition;
import myweb.secondboard.web.CourtType;
import myweb.secondboard.web.MatchingType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class MatchingRepositoryImpl implements MatchingRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;

  private final JPAQueryFactory queryFactory;

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

  public void matchingBeforeTwoHourCheck(Long matchingId) {
    Matching matching = findOne(matchingId);
    matching.matchingBeforeTwoHourCheck(matching);
  }
  public void matchingAfterWeek(Long matchingId) {
    Matching matching = findOne(matchingId);
    matching.matchingAfterWeek(matching);
  }

  public List<Matching> searchMatchingByBuilder(MatchingSearchCondition condition) {
    BooleanBuilder builder = new BooleanBuilder();
    if (hasText(condition.getDate()) && !condition.getDate().equals("none")) {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate date = LocalDate.parse(condition.getDate(), dtf);
      builder.and(matching.matchingDate.eq(date));
    }
    if (hasText(condition.getMatchType()) && !condition.getMatchType().equals("none")) {
      MatchingType matchType = MatchingType.valueOf(condition.getMatchType());
      builder.and(matching.matchingType.eq(matchType));
    }
    if (hasText(condition.getCourtType()) && !condition.getCourtType().equals("none")) {
      CourtType courtType = CourtType.valueOf(condition.getCourtType());
      builder.and(matching.courtType.eq(courtType));
    }

    return queryFactory
      .selectFrom(matching)
      .where(builder)
      .fetch();
  }
}
