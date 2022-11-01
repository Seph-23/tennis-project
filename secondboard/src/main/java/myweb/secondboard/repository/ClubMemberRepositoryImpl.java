package myweb.secondboard.repository;

import lombok.RequiredArgsConstructor;
import myweb.secondboard.domain.ClubMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class ClubMemberRepositoryImpl implements ClubMemberRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;

  public ClubMember findOne(Long clubMemberId) {
    return em.find(ClubMember.class, clubMemberId);
  }
}
