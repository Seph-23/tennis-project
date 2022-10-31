package myweb.secondboard.repository;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.Club;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class ClubRepositoryImpl implements ClubRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;

  public Club findOne(Long clubId) {
    return em.find(Club.class, clubId);
  }
}
